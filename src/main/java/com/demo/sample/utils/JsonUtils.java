package com.demo.sample.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class JsonUtils {

	private ObjectMapper objectMapper;

	@PostConstruct
	public void initialize() {
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
	}

	public String stringify(Object obj) {
		String json = null;

		if (obj != null) {
			try {
				json = objectMapper.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
				json = null;
			}
		}

		return json;
	}

}
