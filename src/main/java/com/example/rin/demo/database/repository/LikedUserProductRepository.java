package com.example.rin.demo.database.repository;

import com.example.rin.demo.database.dao.join_table.LikedUserProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface LikedUserProductRepository extends JpaRepository<LikedUserProduct, Integer> {
    List<LikedUserProduct> findAllByUser_Password(String password);
    List<LikedUserProduct> findAllByUser_PasswordAndProduct_Active(String password,int active);
    Boolean existsByUser_PasswordAndProduct_Id(String password, int id);
    void removeByUser_PasswordAndProduct_Id(String password, int id);
    void deleteAllByProduct_Id(Integer product_id);
}
