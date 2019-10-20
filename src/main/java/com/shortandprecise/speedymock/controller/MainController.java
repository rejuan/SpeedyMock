package com.shortandprecise.speedymock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortandprecise.speedymock.model.Project;
import com.shortandprecise.speedymock.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

@Controller
public class MainController {

	@Autowired
	ObjectMapper objectMapper;

	@RequestMapping(value = "**")
	public Mono<ResponseEntity> index(ServerHttpRequest request) throws JsonProcessingException {
		Project project = Constant.PROJECT_MAP.get(request.getURI().getPath().substring(1));
		ResponseEntity responseEntity;
		long delay = 0;
		if (Objects.nonNull(project)) {
			delay = project.getDelay();
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			if (!CollectionUtils.isEmpty(project.getHeader())) {
				project.getHeader().forEach(httpHeaders::add);
			}

			String responseString = objectMapper.writeValueAsString(project.getResponse());
			responseEntity = ResponseEntity.ok().headers(httpHeaders).body(responseString);
		} else {
			responseEntity = ResponseEntity.notFound().build();
		}

		return Mono.just(responseEntity).delaySubscription(Duration.ofMillis(delay));
	}
}
