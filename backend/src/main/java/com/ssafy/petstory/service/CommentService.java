package com.ssafy.petstory.service;

import com.ssafy.petstory.domain.Board;
import com.ssafy.petstory.domain.Comment;
import com.ssafy.petstory.dto.CreateBoardRequest;
import com.ssafy.petstory.dto.CreateCommentRequest;
import com.ssafy.petstory.repository.BoardRepository;
import com.ssafy.petstory.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 생성
     */
    @Transactional
    public Long create(CreateCommentRequest request) throws IOException {

        Board board = boardRepository.findBoard(request.getBoardId());
        Comment comment = Comment.createComment(request, board);

        commentRepository.save(comment);

        return comment.getId();
    }

    /**
     * 댓글 수정
     */


    /**
     * 댓글 삭제
     */
    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findComment(commentId);
        commentRepository.delete(comment);
    }
}
