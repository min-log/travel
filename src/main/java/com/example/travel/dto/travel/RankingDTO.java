package com.example.travel.dto.travel;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingDTO {
    private Long rankNo;
    private String title;
    private int searchNum;
}
