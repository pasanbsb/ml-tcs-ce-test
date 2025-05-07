package com.ml.cetest.inventory.controller;

import com.ml.cetest.common.exceptions.ProductNotFoundException;
import com.ml.cetest.inventory.model.Product;
import com.ml.cetest.inventory.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing product inventory.
 */
@RestController
@RequestMapping("/api/inventory")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * Retrieves all products stored in the inventory.
     *
     * @return a ResponseEntity containing a list of all products.
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info("Fetching all stored products from database");
        return ResponseEntity.ok(productService.getProducts());
    }

    /**
     * Adds a new product to the inventory.
     *
     * @param product the product to be added, provided in the request body.
     * @return a confirmation message with the saved product details.
     */
    @PostMapping("/products")
    public String saveProduct(@RequestBody Product product) {
        logger.info("Saving product: {}", product);
        return productService.saveProduct(product);
    }

    /**
     * Retrieves a product by its ticker symbol.
     *
     * @param ticker the ticker symbol of the product to be retrieved.
     * @return a ResponseEntity containing the product if found, or a 404 status if not found.
     */
    @GetMapping("/products/{ticker}")
    public ResponseEntity<Product> getProductByTicker(@PathVariable("ticker") String ticker) {
        logger.info("Fetching product with ticker: {}", ticker);
        Product product = productService.getProductByTicker(ticker);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            throw new ProductNotFoundException("Product not found: " + ticker);
        }
    }

    /**
     * Updates the stock level of a product.
     *
     * @param ticker the ticker symbol of the product to be updated.
     * @param stock the new stock level to be set.
     * @return a confirmation message if the product is updated, or an error message if not found.
     */
    @PutMapping("/products/{ticker}/{stock}")
    public String updateProductStock(@PathVariable("ticker") String ticker, @PathVariable("stock") Double stock) {
        logger.info("Updating product stock for ticker: {} to {}", ticker, stock);
        return productService.updateProductStock(ticker, stock);
    }
}
