package com.example.travel.service.travel;

import com.example.travel.domain.Ranking;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.RankingDTO;
import com.example.travel.repository.travel.RankingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class RankingServiceImpl implements RankingService {
    final RankingRepository rankingRepository;

    @Override
    public void rankingUp(String keyword) {
        log.info("ranking up");
        String key = '%' + keyword + '%';
        log.info(key);
        Optional<Ranking> rankingByRankTitle = rankingRepository.findRankingByTitleLike(keyword);
        if (rankingByRankTitle.isEmpty()){
            log.info("존재하지 않는 키워드 -> 키워드 생성");
            Ranking ranking = Ranking.builder().title(keyword).searchNum(0).build();
            Ranking save = rankingRepository.save(ranking);
        }else {
            log.info("존재하는 키워드 -> 번호 + 1");
            Ranking rankingSave = rankingByRankTitle.get();
            RankingDTO rankingDTO = rankingEntityToDto(rankingSave);
            rankingDTO.setSearchNum(rankingDTO.getSearchNum() + 1);
            Ranking ranking = rankingDtoToEntity(rankingDTO);
            Ranking save = rankingRepository.save(ranking);
        }
    }

    @Override
    public Page<RankingDTO> rankingList() {
        log.info("키워드 랭킹 리스트");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("searchNum").descending());
        Page<Ranking> rankingList = rankingRepository.findAllSearchNum(pageRequest,1);
        List<RankingDTO> rankingDTOList = rankingList.getContent()
                .stream()
                .map(i->rankingEntityToDto(i)) // Assuming CategoryDTO constructor takes a Category object
                .collect(Collectors.toList());
        PageImpl<RankingDTO> rankingDTOS = new PageImpl<>(rankingDTOList, PageRequest.of(rankingList.getNumber(), rankingList.getSize()), rankingList.getTotalElements());

        return rankingDTOS;
        //return null;
    }
}
