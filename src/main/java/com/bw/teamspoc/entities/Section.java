package com.bw.teamspoc.entities;

import com.slack.api.model.Im;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
public class Section {
    private final HeroImage heroImage;
    private final List<Image> images;

}
