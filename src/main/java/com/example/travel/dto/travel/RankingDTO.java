package com.example.travel.dto.travel;

import lombok.*;


@ToString
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RankingDTO {
    private Long rankNo;
    private String rankTitle;
    private int rankSearchNum;

    public void rankAdd(int num){
        this.rankSearchNum = this.rankSearchNum + 1;
    }
}
