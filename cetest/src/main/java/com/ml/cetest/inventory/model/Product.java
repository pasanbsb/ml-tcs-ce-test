package com.ml.cetest.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;
    private LocalDate priceDate;
    private Double price;
    private Double stock;

    public Product(String ticker, LocalDate priceDate, Double price, Double stock) {
        this.ticker = ticker;
        this.priceDate = priceDate;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getStock() {
        return stock;
    }
    public void setStock(Double stock) {
        this.stock = stock;
    }
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", priceDate=" + priceDate +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}