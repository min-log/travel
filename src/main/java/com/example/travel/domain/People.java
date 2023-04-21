package com.example.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "categoryId,userNo")
@Getter
@Entity
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long peopleId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    private User userNo;

}
