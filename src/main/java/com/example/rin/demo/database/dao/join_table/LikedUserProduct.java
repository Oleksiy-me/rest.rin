package com.example.rin.demo.database.dao.join_table;


import com.example.rin.demo.database.dao.Product;
import com.example.rin.demo.database.dao.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "liked_products")
public class LikedUserProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public LikedUserProduct() {
    }

    public LikedUserProduct(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
