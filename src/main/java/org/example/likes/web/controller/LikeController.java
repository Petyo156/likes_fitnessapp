package org.example.likes.web.controller;

import org.example.likes.service.LikeService;
import org.example.likes.web.dto.LikeProgramRequest;
import org.example.likes.web.dto.LikeProgramResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService)  {
        this.likeService = likeService;
    }

    @PostMapping("/program")
    public ResponseEntity<LikeProgramResponse> likeProgram(@RequestBody LikeProgramRequest likeProgramRequest) {
        LikeProgramResponse response = likeService.likeProgram(likeProgramRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/programs/{userId}")
    public ResponseEntity<List<String>> getAllLikedPrograms(@PathVariable String userId) {
        List<String> programIds = likeService.getAllLikedProgramIdsForUser(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(programIds);
    }
}
