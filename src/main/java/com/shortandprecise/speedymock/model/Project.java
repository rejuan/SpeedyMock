package com.shortandprecise.speedymock.model;

import lombok.Data;

@Data
public class Project {
	private String path;
	private String response;
	private String header;
	private int delay;
}
