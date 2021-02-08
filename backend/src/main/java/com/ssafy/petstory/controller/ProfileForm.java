package com.ssafy.petstory.controller;

import com.ssafy.petstory.domain.ProfileState;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileForm {  //테이블 명하고 같게

    private ProfileState profile_state;
    //private int relation_id;
    private Long member_id;
    private Long profile_id;
    private String nickname;
    private String rank;
    private int follower_num;
    private int followee_num;

    private String image_full_path;

    public ProfileForm(){}
    public ProfileForm(ProfileState profile_state, Long member_id, Long profile_id, String nickname, String rank, int follower_num, int followee_num) {
        this.profile_state = profile_state;
        this.member_id = member_id;
        this.profile_id = profile_id;
        this.nickname = nickname;
        this.rank = rank;
        this.follower_num = follower_num;
        this.followee_num = followee_num;
    }

    /**
     * 생성 스팩(request)
     */
    public ProfileForm(ProfileState profile_state, Long member_id, String nickname) {
        this.profile_state = profile_state;
        this.member_id = member_id;
        this.nickname = nickname;
    }

    /**
     * 생성 스팩(response)
     */
    public ProfileForm(ProfileState profile_state, Long member_id, String nickname, String image_full_path) {
        this.profile_state = profile_state;
        this.member_id = member_id;
        this.nickname = nickname;
        this.image_full_path = image_full_path;
    }

    /**
     * update 스팩
     */
    public ProfileForm(ProfileState profile_state, String nickname, String image_full_path) {
        this.profile_state = profile_state;
        this.nickname = nickname;
        this.image_full_path = image_full_path;
    }

    public ProfileForm(ProfileState profile_state, Long profile_id, String nickname, String rank, int follower_num, int followee_num) {
        this.profile_state = profile_state;
        this.profile_id = profile_id;
        this.nickname = nickname;
        this.rank = rank;
        this.follower_num = follower_num;
        this.followee_num = followee_num;
    }

    public ProfileForm(ProfileState profile_state, Long member_id, Long profile_id, String nickname, String rank, int follower_num, int followee_num, String image_full_path) {
        this.profile_state = profile_state;
        this.member_id = member_id;
        this.profile_id = profile_id;
        this.nickname = nickname;
        this.rank = rank;
        this.follower_num = follower_num;
        this.followee_num = followee_num;
        this.image_full_path = image_full_path;
    }
}
