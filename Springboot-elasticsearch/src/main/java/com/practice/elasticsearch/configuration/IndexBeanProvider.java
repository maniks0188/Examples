package com.practice.elasticsearch.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IndexBeanProvider {

	@Value("${elasticsearch.index.name}")
	private String indexName;

	public String getIndexName() {
		return indexName;
	}
	
	
	
}
