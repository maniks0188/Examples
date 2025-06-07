package com.practice.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.elasticsearch.model.Product;
import com.practice.elasticsearch.service.ProductService;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@PostMapping("/product")
	public void createProduct(@RequestBody Product product) {
		service.createProduct(product);
	}
	
	@GetMapping("/products")
	public Iterable<Product> findAllProducts() {
		return service.findAll();
	}
	
	@GetMapping("/products/{name}")
	public Iterable<Product> findAllProducts(@PathVariable String name) {
		
		return service.getProductsUsingText(name);
	}
	
	@PostMapping("/products/bulk/{count}")
	public ResponseEntity<String> createBulkData(@PathVariable Integer count){
		
		service.createDummyData(count);
		
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
}
