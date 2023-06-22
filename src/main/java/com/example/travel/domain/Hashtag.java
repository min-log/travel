package com.example.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "categoryId,userId")
@Getter
@Entity
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashId;
    @OneToOne(fetch = FetchType.LAZY)
    private Category categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

}
