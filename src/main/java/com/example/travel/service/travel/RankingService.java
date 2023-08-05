package com.example.travel.service.travel;

import com.example.travel.domain.Ranking;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.RankingDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RankingService {
    public void rankingUp(String keyword);
    public Page<RankingDTO> rankingList();

    default RankingDTO rankingEntityToDto(Ranking ranking){
        RankingDTO result = RankingDTO.builder()
                .rankNo(ranking.getRankNo())
                .title(ranking.getTitle())
                .searchNum(ranking.getSearchNum())
                .build();
        return result;
    }

    default Ranking rankingDtoToEntity(RankingDTO ranking){
        Ranking result = Ranking.builder()
                .rankNo(ranking.getRankNo())
                .title(ranking.getTitle())
                .searchNum(ranking.getSearchNum())
                .build();
        return result;
    }


}
