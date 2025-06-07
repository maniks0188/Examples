package com.practice.elasticsearch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.practice.elasticsearch.configuration.DataGenerator;
import com.practice.elasticsearch.model.Product;
import com.practice.elasticsearch.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;


/**
 * Service class - This class provides methods for managing products data in elastic search.
 * @author Manik Sharma
 *
 */
@Service
@Slf4j
public class ProductService {

	private static Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private DataGenerator dataGenerator;
	
	/**
	 * This method creates a product in elastic search. 
	 * @param product
	 */
	public void createProduct(Product product) {
		logger.info("Create a product in Elastic search");
		repository.save(product);
	}
	
	
	/**
	 * This method gets all the products from elastic search.
	 * @return
	 */
	public Iterable<Product> findAll(){
		logger.info("Get all products from Elastic search");
		return repository.findAll();
	}
	
	/**
	 * This method searches for the products based on input passes. It searches for products that matches
	 * the name with the input. 
	 * @param text
	 * @return List of {@link Product}
	 */
	public List<Product> getProductsUsingName(String text){
		
		logger.info("Get all products with name as {}", text);
		List<Product> list = new ArrayList<>();
		Query query = new NativeQueryBuilder().withQuery(q -> q.match(m -> m.field("productName").query(text))).build();
		
		SearchHits<Product> hits = elasticsearchTemplate.search(query,Product.class);
		hits.stream().forEach(h -> list.add(h.getContent()));
		
		return list;
		
	}
	
	/**
	 * This method searches for the products based on input passes. It searches for products that matches
	 * the name and tags with the input.
	 * 
	 * @param text
	 * @return
	 */
	public List<Product> getProductsUsingText(String text){
		
		logger.info("Get all products with name and tag as {}", text);
		List<Product> list = new ArrayList<>();
		Query query = new NativeQueryBuilder().withQuery
				(q -> q.multiMatch(m -> m.fields(Arrays.asList("productName","tags"))
						.query(text)))
				.withSort(s -> s.field(f -> f.field("price")))
				.build();
		
		SearchHits<Product> hits = elasticsearchTemplate.search(query,Product.class);
		hits.stream().forEach(h -> list.add(h.getContent()));
		
		return list;
		
	}
	
	public void createDummyData(int count) {
		List<Product> products = dataGenerator.createProducts(count);
		repository.saveAll(products);
	}
}
