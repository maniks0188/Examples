package com.practice.elasticsearch.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "#{@indexBeanProvider.getIndexName()}",createIndex = true)
public class Product {

	@Id
	private String productId;
	private String productName;
	private String category;
	private String sku;
	private String brand;
	private String color;
	private Double price;
	private List<String> tags = new ArrayList<String>();
}
