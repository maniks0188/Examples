package com.practice.elasticsearch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.practice.elasticsearch.model.Product;
import com.practice.elasticsearch.repository.ProductRepository;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceIntegrationTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	private Product product = createProduct();

	@BeforeEach
	void setUp() {
		productRepository.deleteAll(); // CAUTION: deletes all test index data
		productService.createProduct(product);
	}

	@Test
	@Order(1)
	void testFindAll() {
		Iterable<Product> allProducts = productService.findAll();
		assertNotNull(allProducts);
		assertTrue(allProducts.iterator().hasNext());
	}

	@Test
	@Order(2)
	void testSearchByName() {
		List<Product> result = productService.getProductsUsingName("TestProduct");
		assertFalse(result.isEmpty());
		assertEquals("TestProduct", result.get(0).getProductName());
	}

	@Test
	@Order(3)
	void testSearchByText() {
		List<Product> result = productService.getProductsUsingText("test");
		assertFalse(result.isEmpty());
		assertTrue(result.get(0).getTags().contains("test"));
	}

	Product createProduct() {
		product = new Product();
		product.setBrand("Apple");
		product.setCategory("Mobile&Accessories");
		product.setColor("Blue");
		product.setPrice(Double.valueOf("1653.45"));
		product.setProductName("iPhone");
		product.setSku("XSXS-1");
		product.setTags(new ArrayList<>(Arrays.asList("electronics", "tag2")));

		return product;
	}
}
