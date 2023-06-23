package com.example.travel.repository.member;

import com.example.travel.domain.UserImage;
import com.example.travel.domain.UserTravel;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTravel,Long> {
    UserTravel getUserTravelByUserNo(Long no);
    
    UserTravel getUserTravelByUserId(String id); // 회원 아이디 유무 확인
    Optional<UserTravel> getUserTravelByUserEmail(String email); //회원 이메일 유무 확인

    //전체 회원 아이디 리스트
    @Query(value = "select m.user_id from user_travel m", nativeQuery = true)
    List<String> getUserTravelList();


    @Modifying
    @Query(value = "delete from user_travel_role_set m where m.user_travel_user_no = :no", nativeQuery = true)
    int deleteByUserRole(@Param(value = "no") Long no);

    @Modifying
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "delete from UserTravel m where m.userId = :id", nativeQuery = false)
    int deleteByUserId(@Param(value = "id") String id); //회원탈퇴


    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "select m from UserTravel m where m.userSocial = :social and m.userId = :id",
    nativeQuery = false)
    Optional<UserTravel> getUserByUserIdAndUserSocial(@Param(value = "id") String id, @Param(value = "social") boolean social); //로그인 시 필요.


    //회원아이디 찾기
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "select m from UserTravel m where m.name = :name and m.userEmail = :email" ,nativeQuery = false)
    Optional<UserTravel> getUserByNameAndUserEmail(@Param(value = "name") String name,@Param(value = "email")String email);
    //회원비밀번호 찾기
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "select m from UserTravel m where m.userId = :id and m.name = :name and m.userEmail = :email" )
    Optional<UserTravel> getUserByPasswordAndUserEmail(@Param(value = "id") String id,@Param(value = "name") String name,@Param(value = "email")String email);

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "select m from UserTravel m right outer join UserImage e on m.userImg = e where m.userId = :id" )
    public Optional<UserTravel> getUserPullByUserId(@Param(value = "id") String id);
}