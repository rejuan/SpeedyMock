package com.shortandprecise.speedymock.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Project {
	private String path;
	private Object response;
	private Map<String, String> header = new HashMap<>();
	private long delay;
}
