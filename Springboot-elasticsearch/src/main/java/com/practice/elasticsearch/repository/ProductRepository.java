package com.practice.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.practice.elasticsearch.model.Product;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, Integer>{

}
