# Product Service – Elasticsearch Integration

This project provides a Spring Boot-based service to manage `Product` entities stored in an **Elasticsearch** index. The `ProductService` handles basic CRUD operations as well as powerful full-text and multi-field search capabilities.

---

## 📦 Features

- ✅ Create and save products to Elasticsearch
- ✅ Fetch all products
- ✅ Full-text search by product name
- ✅ Multi-field search by name and tags
- ✅ Sorting search results by price

---

## 🔧 Technology Stack

- Java 17+
- Spring Boot
- Spring Data Elasticsearch (using `ElasticsearchTemplate`)
- Elasticsearch 8.x
- Lombok

---

## 📁 Project Structure


---

## 🔍 Service Overview

### `ProductService.java`

This class provides methods to:

| Method                      | Description                                                                 |
|----------------------------|-----------------------------------------------------------------------------|
| `createProduct(Product)`   | Indexes a new product                                                       |
| `findAll()`                | Retrieves all indexed products                                              |
| `getProductsUsingName()`   | Performs full-text search on the `productName` field                        |
| `getProductsUsingText()`   | Multi-match search on `productName` and `tags` fields with price sorting    |

---

## 📦 Example Product Document

```json
{
    "productName":"Galaxy-S3",
    "category":"Mobile&Accessories",
    "sku":"SKU-5425232",
    "brand":"Samsung",
    "color":"blue",
    "price":44500.00,
    "tags":["samsung","mobile","premium","galaxy","blue"]
}