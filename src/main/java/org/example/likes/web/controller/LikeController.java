package org.example.likes.web.controller;

import org.example.likes.model.Like;
import org.example.likes.model.Notification;
import org.example.likes.service.LikeService;
import org.example.likes.web.dto.LikeProgramRequest;
import org.example.likes.web.dto.LikeProgramResponse;
import org.example.likes.web.dto.NotifyUserRequest;
import org.example.likes.web.dto.NotifyUserResponse;
import org.example.likes.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        Like like = likeService.likeProgram(likeProgramRequest);
        LikeProgramResponse response = DtoMapper.fromLike(like);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
