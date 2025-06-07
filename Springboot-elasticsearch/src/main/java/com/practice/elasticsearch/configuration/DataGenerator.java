package com.practice.elasticsearch.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.practice.elasticsearch.model.Product;

@Component
public class DataGenerator {
	
	private static List<String> brands = Arrays.asList(new String[]{"Samsung","Apple","OnePlus","Motorola","Lenovo"});
	private static List<String> categories = Arrays.asList(new String[]{"Mobile","Accessories","Tablet","Laptop"});
	private static List<String> colors = Arrays.asList(new String[]{"Red","Blue","Silver","Black","Grey"});
	
	private static String tag_prefix = "Tag-";
	private static String prodname_prefix = "prdnm-";
	
	public List<Product> createProducts(int count){
		
		ArrayList<Product> list = new ArrayList<Product>();
		for(int i=1; i<=count; i++) {
			Product product  = new Product();
			product.setBrand(getBrand());
			product.setCategory(getCategory());
			product.setColor(getColor());
			product.setPrice(generatePrice());
			product.setProductName(prodname_prefix+i);
			product.setSku(generateSku());
			for(int j=0;j<tagsCount();j++) {
				product.getTags().add(tag_prefix+j);	
			}
			list.add(product);
		}
		
		return list;
	}
	
	private String getBrand() {
		Random random = new Random();
		return brands.get(random.nextInt(1, 5));
	}
	
	private String getCategory() {
		Random random = new Random();
		return categories.get(random.nextInt(1, 4));
	}
	
	private String getColor() {
		Random random = new Random();
		return colors.get(random.nextInt(1, 5));
	}
	
	private int tagsCount() {
		Random random = new Random();
		return random.nextInt(1, 5);
	}
	
	private String generateSku() {
		return UUID.randomUUID().toString();
	}
	private Double generatePrice() {
		Random random = new Random();
		return Math.ceil(500 + (1600 - 500) * random.nextDouble())/100;
	}
	
	

}
