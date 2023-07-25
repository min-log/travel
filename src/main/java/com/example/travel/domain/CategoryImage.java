package com.example.travel.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class CategoryImage extends Image {
    private Long boardNo;
    private boolean thumbnail;
}
