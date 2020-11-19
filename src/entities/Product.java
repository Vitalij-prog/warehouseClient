package entities;

import java.io.Serializable;

public class Product implements Serializable {
    private Integer id;
    private String name;
    private Double price;
    private Integer amount;

    public Product(int id, String name, double price, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
    public Product(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
