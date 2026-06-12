package com.lms.dto.response;

public record ELibraryBookResponse(
        String id,
        String title,
        String authors,
        String coverUrl,
        String readOnlineUrl,
        String downloadUrl,
        Integer firstPublishYear
) {
}
