package com.example.travel.dto.travel;

import lombok.*;



@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO {
    private Long commNo;
    private Long commCategory;
    private String commUser;
    private Long commUserNo;
    private String commCont;

}
