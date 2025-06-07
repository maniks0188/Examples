package com.practice.elasticsearch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

import com.practice.elasticsearch.model.Product;
import com.practice.elasticsearch.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@InjectMocks
	ProductService productService;
	
	@Mock
	ProductRepository repository;
	
	@Mock
	ElasticsearchTemplate elasticsearchTemplate;
	
	Product product;
	
	@BeforeEach
	void setup() {
		product = new Product();
		product.setBrand("Apple");
		product.setCategory("Mobile&Accessories");
		product.setColor("Blue");
		product.setPrice(Double.valueOf("1653.45"));
		product.setProductName("iPhone");
		product.setSku("XSXS-1");
		product.setTags(new ArrayList<>(Arrays.asList("electronics","tag2")));
	}
	
	 @Test
	    void testCreateProduct() {
	        when(repository.save(product)).thenReturn(product);

	        productService.createProduct(product);

	        verify(repository, times(1)).save(product);
	    }

	    @Test
	    void testFindAll() {
	        List<Product> mockProducts = List.of(product);
	        when(repository.findAll()).thenReturn(mockProducts);

	        Iterable<Product> result = productService.findAll();

	        assertNotNull(result);
	        assertEquals(product, result.iterator().next());
	    }

	    @SuppressWarnings("unchecked")
		@Test
	    void testGetProductsUsingName() {
	        SearchHit<Product> searchHit = mock(SearchHit.class);
	        when(searchHit.getContent()).thenReturn(product);

	        SearchHits<Product> mockHits = mock(SearchHits.class);
	        when(mockHits.stream()).thenReturn(Stream.of(searchHit));

	        when(elasticsearchTemplate.search(any(Query.class), eq(Product.class))).thenReturn(mockHits);

	        List<Product> result = productService.getProductsUsingName("iPhone");

	        assertNotNull(result);
	        assertEquals(1, result.size());
	        assertEquals("iPhone", result.get(0).getProductName());
	    }

	    @SuppressWarnings("unchecked")
		@Test
	    void testGetProductsUsingText() {
	        SearchHit<Product> searchHit = mock(SearchHit.class);
	        when(searchHit.getContent()).thenReturn(product);

	        SearchHits<Product> mockHits = mock(SearchHits.class);
	        when(mockHits.stream()).thenReturn(Stream.of(searchHit));

	        when(elasticsearchTemplate.search(any(Query.class), eq(Product.class))).thenReturn(mockHits);

	        List<Product> result = productService.getProductsUsingText("electronics");

	        assertNotNull(result);
	        assertEquals(1, result.size());
	        assertTrue(result.get(0).getTags().contains("electronics"));
	    }
	}
