package com.ssafy.petstory.repository;

import com.ssafy.petstory.domain.Board;
import com.ssafy.petstory.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    /**
     * 댓글 생성
     */
    public void save(Comment comment) {
        em.persist(comment);
    }
}
