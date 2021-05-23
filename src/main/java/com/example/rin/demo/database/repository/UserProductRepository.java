package com.example.rin.demo.database.repository;

import com.example.rin.demo.database.dao.Product;
import com.example.rin.demo.database.dao.User;
import com.example.rin.demo.database.dao.join_table.LikedUserProduct;
import com.example.rin.demo.database.dao.join_table.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;


public interface UserProductRepository extends JpaRepository<UserProduct, Integer> {
    List<UserProduct> getAllByUser_Id(int id);

    Boolean existsByUser_PasswordAndProduct_Id(String password, int id);
    Boolean deleteByUser_PasswordAndProduct_Id(String password, int id);
    UserProduct getByProductId(int id);

    UserProduct getByProductIdAndUser_Password(int id, String password);

    List<UserProduct> findAllByUser_PasswordAndProduct_Active(String code, int i);
}
