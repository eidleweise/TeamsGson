package com.bw.teamspoc.entities;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
public class MessageCard {
    private final String type;
    private final String context;
    private final String themeColor;
    private final String title;
    private final String text;
    private final String moreText;
    private final List<Section> sections;
}
