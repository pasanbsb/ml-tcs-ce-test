package com.ml.cetest.inventory;

import com.ml.cetest.inventory.model.Product;
import com.ml.cetest.inventory.repository.ProductRepository;
import com.ml.cetest.inventory.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProducts() {
        Product product1 = new Product("AAPL", LocalDate.of(2023, 10, 1), 175.50, 100.0);
        Product product2 = new Product("GOOGL", LocalDate.of(2023, 10, 1), 2800.00, 50.0);
        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<Product> products = productService.getProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }


    @Test
    void testSaveProduct_UpdateExistingProduct() {
        Product existingProduct = new Product("AAPL", LocalDate.of(2023, 10, 1), 170.00, 90.0);
        Product updatedProduct = new Product("AAPL", LocalDate.of(2023, 10, 1), 175.50, 100.0);
        when(productRepository.findByTicker(updatedProduct.getTicker())).thenReturn(existingProduct);
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        String result = productService.saveProduct(updatedProduct);

        assertTrue(result.contains("Product updated to"));
        assertEquals(175.50, existingProduct.getPrice());
        assertEquals(100.0, existingProduct.getStock());
        verify(productRepository, times(1)).findByTicker(updatedProduct.getTicker());
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testGetProductByTicker_Found() {
        Product product = new Product("AAPL", LocalDate.of(2023, 10, 1), 175.50, 100.0);
        when(productRepository.findByTicker("AAPL")).thenReturn(product);

        Product result = productService.getProductByTicker("AAPL");

        assertNotNull(result);
        assertEquals("AAPL", result.getTicker());
        verify(productRepository, times(1)).findByTicker("AAPL");
    }

    @Test
    void testGetProductByTicker_NotFound() {
        when(productRepository.findByTicker("AAPL")).thenReturn(null);

        Product result = productService.getProductByTicker("AAPL");

        assertNull(result);
        verify(productRepository, times(1)).findByTicker("AAPL");
    }

    @Test
    void testUpdateProductStock_Success() {
        Product product = new Product("AAPL", LocalDate.of(2023, 10, 1), 175.50, 100.0);
        when(productRepository.findByTicker("AAPL")).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        String result = productService.updateProductStock("AAPL", 120.0);

        assertEquals("Product stock updated to 120.0", result);
        assertEquals(120.0, product.getStock());
        verify(productRepository, times(1)).findByTicker("AAPL");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProductStock_NotFound() {
        when(productRepository.findByTicker("AAPL")).thenReturn(null);

        String result = productService.updateProductStock("AAPL", 120.0);

        assertEquals("Product not found", result);
        verify(productRepository, times(1)).findByTicker("AAPL");
        verify(productRepository, never()).save(any());
    }
}
