package com.ssafy.petstory.service;

import com.ssafy.petstory.domain.BoardHashtag;
import com.ssafy.petstory.domain.Hashtag;
import com.ssafy.petstory.dto.BoardQueryDto;
import com.ssafy.petstory.repository.BoardHashtagRepository;
import com.ssafy.petstory.repository.BoardRepository;
import com.ssafy.petstory.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 영속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
@RequiredArgsConstructor // final, nonnull인 field를 가지고 생성자를 만들어줌
public class HashtagService {

    private final HashtagRepository hashtagRepository;
    private final BoardHashtagRepository boardHashtagRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Hashtag findOrCreateHashtag(String hashtagName) {

        Hashtag hashtag = hashtagRepository.findByName(hashtagName)
                .orElse(Hashtag.createHashtag(hashtagName));
        return hashtagRepository.save(hashtag);
    }


    /**
     * 해시태그로 게시물 검색
     */
    public List<BoardQueryDto> findBoardsByHashtag(String hashtagName) {

        // 해당 이름을 가진 해시태그 엔티티 조회
        Hashtag hashtag = hashtagRepository.findByHashtagName(hashtagName);

        // 해당 해시태그를 매핑한 게시글 번호들 조회
        List<BoardHashtag> boardHashtags = boardHashtagRepository.findBoardHashtag(hashtag.getId());

        // 게시글 번호들로 게시글들 조회
        List<BoardQueryDto> result = boardRepository.findByHashtag(boardHashtags);

        return result;

    }

    /**
     * 해시태그 자동완성
     *  (게시물 생성시)
     */
    public List<String> findHashtagName(String hashtagName){
        return hashtagRepository.findByHashtagNameStartsWith(hashtagName);
    }

    /**
     * 인기 해시태그 조회
     */
//    public List<PopulatHashtagDto> findPopular

}
