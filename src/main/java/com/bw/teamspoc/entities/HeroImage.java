package com.bw.teamspoc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
public class HeroImage {
    private final String image;
    private final String url;
    private final String summary;
    private final String text;
}
