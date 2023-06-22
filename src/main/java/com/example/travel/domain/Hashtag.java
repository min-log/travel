package com.example.travel.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "categoryId,userId")
@Getter
@Builder
@Entity
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashId;
    @OneToOne(fetch = FetchType.LAZY)
    private Category categoryId;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Tag> tag;

}
