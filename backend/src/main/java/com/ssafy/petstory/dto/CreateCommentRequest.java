package com.ssafy.petstory.dto;

import lombok.Data;

@Data
public class CreateCommentRequest {

    private Long profileId;
    private Long boardId;
    private String content;

    private Long commentId; // 수정 명세

    public CreateCommentRequest() {
    }

    public CreateCommentRequest(Long profileId, Long boardId, String content) {
        this.profileId = profileId;
        this.boardId = boardId;
        this.content = content;
    }

    public CreateCommentRequest(Long profileId, Long boardId, String content, Long commentId) {
        this.profileId = profileId;
        this.boardId = boardId;
        this.content = content;
        this.commentId = commentId;
    }
}
