package com.example.travel.repository.member;

import com.example.travel.domain.UserTravel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTravel,Long> {
    UserTravel getUserTravelByUserNo(Long no);
    
    UserTravel getUserTravelByUserId(String id); // 회원 아이디 유무 확인
    Optional<UserTravel> getUserTravelByUserEmail(String email); //회원 이메일 유무 확인

    //전체 회원 아이디 리스트
    @Query(value = "select m.user_id from user_travel m join user_travel_role_set utrs on m.user_no = utrs.user_travel_user_no where utrs.role_set = 0", nativeQuery = true)
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
    @Query(value = "select m from UserTravel m where m.userId = :id")
    public Optional<UserTravel> getUserPullByUserId(@Param(value = "id") String id);



    // 관리자 회원 리스트
    @Query(value = "select u.* from (select m.*,r.role_set from user_travel m " +
            "        left outer join user_travel_role_set r " +
            "        on m.user_no = r.user_travel_user_no ) u" +
            "            where u.role_set = :role and (u.name like :key or u.user_id like :key or u.user_email like :key)",nativeQuery = true)
    Page<UserTravel> findAllByRoleSet(@Param("key") String keyword , Pageable pageable,@Param("role") Integer role);


    //user 사용자 비율
    @Query(value = "select u.user_gender as graphTitle, count(u.user_gender) as graphVal " +
            "from (select m.* from user_travel m " +
            "join user_travel_role_set utrs on m.user_no = utrs.user_travel_user_no " +
            "where utrs.role_set = 0) u group by u.user_gender " +
            "HAVING u.user_gender is not null ", nativeQuery = true)
    public List<String[]> findGenderGraph();


    //회원 연령대
    @Query(value = "SELECT CASE " +
            "            WHEN c.user_age = 0 THEN '기타' " +
            "            WHEN c.user_age BETWEEN 10 AND 19 THEN '10대' " +
            "            WHEN c.user_age BETWEEN 20 AND 29 THEN '20대' " +
            "            WHEN c.user_age BETWEEN 30 AND 39 THEN '30대' " +
            "            WHEN c.user_age BETWEEN 40 AND 49 THEN '40대' " +
            "            WHEN c.user_age >= 50 THEN '50대 이상' " +
            "    END AS age_group " +
            "     , COUNT(*) total_cnt " +
            "FROM(select m.* from user_travel m " +
            "                         join user_travel_role_set utrs on m.user_no = utrs.user_travel_user_no " +
            "     where utrs.role_set = 0) AS c " +
            "GROUP BY age_group " +
            "ORDER BY age_group" , nativeQuery = true)
    public List<String[]> findAgeGraph();



    // 회원
    @Query(value = "select COUNT(*) " +
            "from (select m.* from user_travel m " +
            "      join user_travel_role_set utrs on m.user_no = utrs.user_travel_user_no " +
            "      where utrs.role_set = 0) u" , nativeQuery = true)
    public int findAllUserNumber();


}
