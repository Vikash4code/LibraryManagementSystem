package com.lms.controller;

import com.lms.service.ELibraryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/elibrary")
@RequiredArgsConstructor
@Tag(name = "E-Library")
public class ELibraryController {

    private final ELibraryService eLibraryService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String topic,
            @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(eLibraryService.search(q, topic, page));
    }
}
