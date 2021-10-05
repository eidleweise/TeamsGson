package com.bw.teamspoc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
public class Button {
	private final String type;
	private final String title;
	private final String value;
	private final String summary;
	private final String text;
}
