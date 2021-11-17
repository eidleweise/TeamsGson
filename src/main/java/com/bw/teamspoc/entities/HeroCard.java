package com.bw.teamspoc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
public class HeroCard {
	private final String contentType;
	private final HeroCardContent content;
	private final String summary;
	private final String text;
	private final String text2;
}
