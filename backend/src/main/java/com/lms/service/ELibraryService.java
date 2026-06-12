package com.lms.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.lms.dto.response.ELibraryBookResponse;
import com.lms.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Thin proxy over the free Open Library search API (https://openlibrary.org).
 * We only ask for books with free full text (ebook_access:public), so every
 * result can be read online or downloaded from the Internet Archive.
 * Going through our backend avoids CORS issues in the browser.
 */
@Service
public class ELibraryService {

    private static final int PAGE_SIZE = 24;

    private final RestClient restClient;

    public ELibraryService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://openlibrary.org")
                .defaultHeader("User-Agent", "library-management-system (student project)")
                .build();
    }

    public Map<String, Object> search(String query, String topic, int page) {
        // Open Library supports Lucene-style filters inside the q parameter
        StringJoiner q = new StringJoiner(" ");
        if (query != null && !query.isBlank()) {
            q.add(query.trim());
        }
        if (topic != null && !topic.isBlank()) {
            q.add("subject:" + topic.trim());
        }
        q.add("ebook_access:public"); // free-to-read books only

        int safePage = Math.max(page, 1);

        JsonNode response;
        try {
            response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search.json")
                            .queryParam("q", q.toString())
                            .queryParam("page", safePage)
                            .queryParam("limit", PAGE_SIZE)
                            .queryParam("fields", "key,title,author_name,cover_i,ia,first_publish_year")
                            .build())
                    .retrieve()
                    .body(JsonNode.class);
        } catch (Exception e) {
            throw new ApiException("E-Library service is not reachable right now, please try again later");
        }

        List<ELibraryBookResponse> books = new ArrayList<>();
        if (response != null && response.has("docs")) {
            response.get("docs").forEach(doc -> books.add(toBook(doc)));
        }

        long totalCount = response != null ? response.path("numFound").asLong(0) : 0;

        Map<String, Object> result = new HashMap<>();
        result.put("books", books);
        result.put("totalCount", totalCount);
        result.put("hasNext", (long) safePage * PAGE_SIZE < totalCount);
        result.put("page", safePage);
        return result;
    }

    private ELibraryBookResponse toBook(JsonNode doc) {
        StringJoiner authors = new StringJoiner(", ");
        doc.path("author_name").forEach(a -> authors.add(a.asText()));

        String coverUrl = doc.has("cover_i")
                ? "https://covers.openlibrary.org/b/id/" + doc.get("cover_i").asInt() + "-M.jpg"
                : null;

        // "ia" is the Internet Archive identifier used for reading and downloading
        String iaId = doc.path("ia").isArray() && doc.path("ia").size() > 0
                ? doc.path("ia").get(0).asText()
                : null;
        String readUrl = iaId != null ? "https://archive.org/details/" + iaId : null;
        String downloadUrl = iaId != null
                ? "https://archive.org/download/" + iaId + "/" + iaId + ".pdf"
                : null;

        return new ELibraryBookResponse(
                doc.path("key").asText(),
                doc.path("title").asText(),
                authors.toString(),
                coverUrl,
                readUrl,
                downloadUrl,
                doc.has("first_publish_year") ? doc.get("first_publish_year").asInt() : null);
    }
}
