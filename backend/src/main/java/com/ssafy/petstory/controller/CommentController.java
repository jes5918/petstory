package com.ssafy.petstory.controller;

import com.ssafy.petstory.dto.CreateCommentRequest;
import com.ssafy.petstory.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }


    /**
     * 댓글 생성
     */
    @PostMapping("/api/comment/create")
    public CreateCommentResponse createComment(CreateCommentRequest request) throws IOException {

        Long id = commentService.create(request);

        return new CreateCommentResponse(id);
    }
    


    @Data
    static class CreateCommentResponse {
        private Long id;

        public CreateCommentResponse(Long id) {
            this.id = id;
        }
    }
}
