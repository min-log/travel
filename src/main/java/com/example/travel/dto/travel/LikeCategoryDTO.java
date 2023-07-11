package com.example.travel.dto.travel;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class LikeCategoryDTO {
    private Long id;
    private Long categoryId; // 카테고리 아이디
    private Long userId; // TravelUser 아이디
}
