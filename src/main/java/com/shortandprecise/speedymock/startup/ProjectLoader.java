package com.shortandprecise.speedymock.startup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortandprecise.speedymock.model.Project;
import com.shortandprecise.speedymock.util.Constant;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class ProjectLoader implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Stream<Path> paths = Files.walk(Paths.get(Constant.PROJECT_FOLDER));
		Object[] objects = paths.filter(Files::isRegularFile).toArray();
		for (Object object : objects) {
			Path path = (Path) object;
			ObjectMapper objectMapper = new ObjectMapper();
			Project[] projects = objectMapper.readValue(path.toFile(), Project[].class);
			String projectName = projectNameFromPath(path);
			for (Project project : projects) {
				Constant.PROJECT_MAP.put(projectName + "/" + project.getPath(), project);
			}
		}
	}

	private String projectNameFromPath(Path path) {
		String filename = path.toFile().getName();
		return filename.substring(0, filename.lastIndexOf("."));
	}
}
