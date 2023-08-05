package com.example.travel.service.travel;

import com.example.travel.domain.Ranking;
import com.example.travel.dto.travel.RankingDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RankingServiceImplTest {
    @Autowired
    RankingService rankingService;

    @Test
    @DisplayName("키워드 랭킹 셋팅")
    public void rankingSet(){
        List<String[]> rankSet = new ArrayList<>();

        String[] sity = new String[]{"강원", "경기","경남","경북","광주","대구","대전","부산","서울","울산","인천","전남","전북","제주","충남","충북"};
        String[] gangwon = new String[]{"강릉","동해","삼척","속초","원주","춘천","태백","고성","양구","양양","영월","인제","정선","철원","평창","홍천","화천","횡성"};
        String[] gyeonggi = new String[]{"고양","과천","광명","구리","군포","김포","남양주","동두천","부천","성남","수원","시흥","안산","안성","안양","양주","오산","용인","의왕","의정부","이천","파주","평택","포천","하남","화성","가평","양평","여주","연천"};
        String[] gyeongsangnam = new String[]{"거제", "김해", "마산", "밀양", "사천", "양산", "진주", "진해", "창원", "통영", "거창", "남해", "산청", "의령", "창녕", "하동", "함안", "함양", "합천"};
        String[] gyeongsangbuk =  new String[]{"경산","경주","구미","김천","문경","상주","안동","영주","영천","포항","고령","군위","봉화","성주","영덕","영양","예천","울릉","울진","의성","청도","청송","칠곡"};
        String[] gwangju = {"광산구", "남구", "동구", "북구"};
        String[] daegu = {"달서구", "수성구", "중구", "달성군"};
        String[] daejeon = {"대덕", "유성",};
        String[] busan = {"강서구","금정구", "동래", "부산진구", "사상", "사하","수영", "연제", "영도", "해운대", "기장"};
        String[] seoul = {"강남", "강동", "강북", "강서", "관악", "광진", "구로", "금천", "노원", "도봉", "동대문", "동작", "마포", "서대문", "서초", "성동", "성북", "송파", "양천", "영등포", "용산", "은평", "종로", "중랑"};
        String[] ulsan = {"울주"};
        String[] incheon = {"계양", "남동", "부평", "연수구", "강화", "옹진"};
        String[] jeonnam = {"광양", "나주", "목포", "순천", "여수", "강진", "고흥", "곡성군", "구례", "담양", "무안", "보성", "신안", "영광", "영암", "완도", "장성", "장흥", "진도", "함평", "해남", "화순"};
        String[] jeonbuk = {"군산", "김제", "남원", "익산", "전주", "정읍", "고창", "무주", "부안", "순창", "완주", "임실", "장수", "진안"};
        String[] jeju = {"서귀포","제주시","남제주","북제주"};
        String[] chungbuk = {"제천", "청주", "충주", "괴산", "단양", "보은", "영동", "옥천", "음성", "증평", "진천", "청원"};

        rankSet.add(sity);
        rankSet.add(gangwon);
        rankSet.add(gyeonggi);
        rankSet.add(gyeongsangnam);
        rankSet.add(gyeongsangbuk);
        rankSet.add(gwangju);
        rankSet.add(daegu);
        rankSet.add(daejeon);
        rankSet.add(busan);
        rankSet.add(seoul);
        rankSet.add(incheon);
        rankSet.add(ulsan);
        rankSet.add(jeonnam);
        rankSet.add(jeonbuk);
        rankSet.add(jeju);
        rankSet.add(chungbuk);

        rankSet.stream().forEach(i->{
            for (int e=0;e < i.length;e++){
                rankingService.rankingUp(i[e]);
            }
        });

    }


    @Test
    @DisplayName("키워드 랭킹 검색시")
    public void rankingSave(){
        rankingService.rankingUp("강원");
    }
    @Test
    @DisplayName("키워드 랭킹 리스트")
    public void rankingList(){
        Page<RankingDTO> rankingDTOS = rankingService.rankingList();
        List<RankingDTO> content = rankingDTOS.getContent();
        for (RankingDTO r : content){
            System.out.println(r);
        }

    }
}