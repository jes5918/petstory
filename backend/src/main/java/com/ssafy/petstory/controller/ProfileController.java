package com.ssafy.petstory.controller;


import com.ssafy.petstory.domain.Like;
import com.ssafy.petstory.domain.Member;
import com.ssafy.petstory.domain.Profile;
import com.ssafy.petstory.domain.Relation;
import com.ssafy.petstory.dto.LikeDto;
import com.ssafy.petstory.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping("/map")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 프로필 생성
     * */
    @PostMapping("/profiles/new")  // post - 맴버로 로그인 후 프로필 생성 클릭 시 -> 프론트에서 맴버 id(세션에 저장된), 받아와 Member타입은 null로
    public ResponseEntity<String> create(@Valid @RequestBody ProfileForm proform, BindingResult result){


        if (result.hasErrors()) {
            return new ResponseEntity<>("error입니다. 파라미터명, 형식 확인", HttpStatus.FORBIDDEN);
        }


        System.out.println("=================================================== 받은 닉네임 확인");
        System.out.println(proform.getNickname());

        //service -> 1. 맴버 id를 이용해 member 찾고   -> 2. entity 메서드 profile 엔티티에 연관관계 지어주고 서비스에서 db에 바로 넣어준다
        //이때 relation 테이블도 함께 생성된다.
        profileService.createprofile(proform);


//        profile.set(form.getMember_name());
//        profile.setEmail(form.getEmail());
//        profile.setPassword(form.getPassword());

        return new ResponseEntity<>("success", HttpStatus.OK); //이건 컨트롤러에서 해당 뷰를 보여주는 것이 아니라 redirect 오른쪽 주소로 url 요청 다시하는거(새로고침)
        //ResponseEntity로 성공 메세지 전달 가능
    }


    /**
     * 프로필 조회1(세부조회)
     * */

    @GetMapping("/detail/profile/{profile_id}")   // 프로필 아이디 받아서 findone 조회 후 폼에 담아서 객체하나 리턴
    public ResponseEntity<ProfileForm> detail(@PathVariable("profile_id") Long profile_id, ProfileForm form) {

        Profile profile = new Profile();
        profile.setId(profile_id);
        //엔티티로 db접근

        Profile profileentity = profileService.detail(profile.getId());  //id 받은걸로 엔티티 검색

        if (profileentity == null) {//해당아이디로 검색된 정보가 없음
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        form.setProfile_id(profileentity.getId());
        form.setMember_id(profileentity.getMember().getId());
        form.setFollowee_num(profileentity.getFollowee_num());
        form.setFollower_num(profileentity.getFollower_num());
        form.setNickname(profileentity.getNickname());
        form.setProfile_state(profileentity.getState());
        form.setRank(profileentity.getRank());

        return new ResponseEntity<>(form, HttpStatus.OK);
    }//맴버정보보기를 눌러서 확인

    /**
     * 프로필 조회2(맴버의 다중프로필 조회 - 로그인 시 사용)
     * */
    @GetMapping("/show/{member_id}")
    public ResponseEntity<List<Profile>> show(@PathVariable("member_id") Long member_id) {

        Member member = new Member();
        member.setId(member_id);

        System.out.print("아이디 가져온거 출력: ");
        System.out.println(member.getId());

        List<Profile> profileentity = profileService.showprofile(member.getId());  //id 받은걸로 엔티티 검색
        //해당 memid 로 검색된 프로필 리스트 -> profileentity
        System.out.println(profileentity.size());

//        for(int i=0 ; i<profileentity.size() ; i++){
//
//        }sS

        if (profileentity.size() == 0) {//해당 맴버아이디로 검색된 프로필이 없음
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(profileentity, HttpStatus.OK);
    }//맴버정보보기를 눌러서 확인

    /**
     * 프로필 정보 수정
     * */
    @PutMapping("profile/update/{profile_id}") // v2 mem id로 받아서 검색 후 수정, 받아오는 형식 memformdto
    public ResponseEntity<String> updateProfile(@PathVariable("profile_id") Long profile_id , @Valid @RequestBody ProfileForm form) {

        profileService.update(profile_id, form);
        //Member findMember = memberService.findOne(id); 수정정보 리턴할 때

        return new ResponseEntity<>("프로필 정보가 수정되었습니다.", HttpStatus.OK);
    }//맴버정보보기를 눌러서 확인

    /**
     * 프로필 정보 삭제
     * */
    @DeleteMapping("profile/delete/{profile_id}")  //프로필 아이디를 통해 삭제한다.
    public ResponseEntity<String> deleteMember(@PathVariable("profile_id") Long profile_id ) {

//        Profile profile = new Profile();
//        profile.setId(profile_id);
        profileService.delete(profile_id);
        return new ResponseEntity<>("프로필 정보가 삭제되었습니다.", HttpStatus.OK);
    }//맴버정보보기를 눌러서 확인


    /**
     * 좋아요 눌렀을 경우 게시물의 좋아요 up + like테이블에 정보 저장
     * */

    @PostMapping("/profiles/like")  // board_id랑 좋아요 누른 사람의 프로필_id 받아와
    public ResponseEntity<String> like(@RequestBody LikeDto likeform){

        System.out.println("=================================================== 받은 프로필 아이디 확인");
        System.out.println(likeform.getProfile_id());

        Like like = new Like();

        //엔티티로 변경해주자
        like.setProfile_id(likeform.getProfile_id());    //좋아요 누른사람의 프로필 아이디
        like.setBoard_id(likeform.getBoard_id());       //좋아요 누른 게시물

        //일단 테이블에서 프로필아이디와 게시글아이디로 같은것이 있는지 찾아보자 참거짓으로 리턴 받자
        boolean is_like_emtpy= profileService.findlike(like);

        if(is_like_emtpy ==true){  //없으니까 생성하자 + 게시물의 like count 올리고-> 프로필 아이디로 게시물 검색해서 라이크 올리고

            profileService.likeup(like);
            profileService.addlike(like);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        else{ // 이미 있으니까 없애자 + 이미 라이크 눌렀으니까 like count 내리고-> 프로필 아이디로 게시물 검색해서 라이크 내리고
            profileService.likedown(like);
            profileService.deletelike(like);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }
    /**
     * 팔로우 신청
     * */

    @PostMapping("/profiles/follow")  // post - 맴버로 로그인 후 프로필 생성 클릭 시 -> 프론트에서 맴버 id(세션에 저장된), 받아와 Member타입은 null로
    public ResponseEntity<String> create(@RequestBody RelationForm follower_ee){

        System.out.println("확인1");
        //두 개 받고 나서 relation 테이블에 넣어주기 전 엔티티로 변경
        Relation relation = new Relation();
        System.out.println("확인2");
        relation.setFollowee_id(follower_ee.getFollowee_id());
        relation.setFollower_id(follower_ee.getFollower_id());

        //System.out.println("1: "+follower_ee.getFollowee_id()+"2: "+follower_ee.getFollower_id()+"3: "+follower_ee.getRelation_id());

        profileService.createrelation(relation);

        return new ResponseEntity<>("success", HttpStatus.OK); //이건 컨트롤러에서 해당 뷰를 보여주는 것이 아니라 redirect 오른쪽 주소로 url 요청 다시하는거(새로고침)
        //ResponseEntity로 성공 메세지 전달 가능
    }
}
