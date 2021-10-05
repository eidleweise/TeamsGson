package com.bw.teamspoc.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
public class HeroCardContent {
	private final String title;
	private final String subtitle;
	private final String text;
	private final List<HeroImage> images;
	private final List<Button> buttons;

}
