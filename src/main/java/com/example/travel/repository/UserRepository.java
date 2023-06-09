package com.example.travel.repository;

import com.example.travel.domain.UserTravel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTravel,Long> {
    UserTravel getUserTravelByUserNo(Long no);
    
    UserTravel getUserTravelByUserId(String id); // 회원 아이디 유무 확인
    UserTravel getUserTravelByUserEmail(String email); //회원 이메일 유무 확인

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
}
