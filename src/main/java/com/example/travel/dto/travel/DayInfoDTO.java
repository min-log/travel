package com.example.travel.dto.travel;

import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@Builder
public class DayInfoDTO {
    int day;
    String[] dayInfo;

    public DayInfoDTO(int day, String[] dayInfo) {
        this.day = day;
        this.dayInfo = dayInfo;
    }
}
