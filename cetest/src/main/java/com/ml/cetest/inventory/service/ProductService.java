package com.ml.cetest.inventory.service;

import com.ml.cetest.inventory.controller.ProductController;
import com.ml.cetest.inventory.model.Product;
import com.ml.cetest.inventory.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing product-related operations.
 */
@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves all products from the database.
     *
     * @return a list of all stored products.
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * Adds or updates a new product to the database.
     *
     * @param product the product to be added.
     * @return a confirmation message with the saved product details.
     */
    public String saveProduct(Product product) {
        Product existingProduct = productRepository.findByTicker(product.getTicker());
        if (existingProduct != null) {
            logger.info("Product {} already exists, updating it", product.getTicker());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setPriceDate(product.getPriceDate());
            existingProduct.setStock(product.getStock());
            productRepository.save(existingProduct);
            return "Product updated to " + existingProduct;
        } else {
            logger.info("Product {} does not exist, saving it", product.getTicker());
            productRepository.save(product);
            return "Product saved to database" + this.getProductByTicker(product.getTicker());
        }

    }

    /**
     * Retrieves a product by its ticker symbol.
     *
     * @param ticker the ticker symbol of the product.
     * @return the product with the specified ticker, or null if not found.
     */
    public Product getProductByTicker(String ticker) {
        return productRepository.findByTicker(ticker);
    }

    /**
     * Updates the stock level of a product.
     *
     * @param ticker the ticker symbol of the product.
     * @param stock the new stock level to be set.
     * @return a confirmation message if the product is updated, or an error message if not found.
     */
    public String updateProductStock(String ticker, Double stock) {
        Product product = productRepository.findByTicker(ticker);
        if (product != null) {
            product.setStock(stock);
            productRepository.save(product);
            return "Product stock updated to " + stock;
        } else {
            return "Product not found";
        }
    }
}