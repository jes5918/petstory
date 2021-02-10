package com.ssafy.petstory.dto;

import lombok.Data;

@Data
public class CreateCommentRequest {

    private Long profileId;
    private Long boardId;
    private String content;

    public CreateCommentRequest(Long profileId, Long boardId, String content) {
        this.profileId = profileId;
        this.boardId = boardId;
        this.content = content;
    }
}
