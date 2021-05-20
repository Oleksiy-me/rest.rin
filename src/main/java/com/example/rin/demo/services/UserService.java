package com.example.rin.demo.services;

import com.example.rin.demo.database.dao.Product;
import com.example.rin.demo.database.dao.User;
import com.example.rin.demo.database.dao.join_table.LikedUserProduct;
import com.example.rin.demo.database.repository.LikedUserProductRepository;
import com.example.rin.demo.database.repository.ProductRepository;
import com.example.rin.demo.database.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikedUserProductRepository likedUserProductRepository;

    public ArrayList<User> getAll() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            user.setId(0);
            user.setName("user_registred");
            user.setPassword("1");
            user.setEmail("1");
            user.setPhone("1");
            return user;
        }
        userRepository.save(user);
        user = userRepository.findByEmail(user.getEmail());
        return user;
    }

    public User existsByEmailandPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) return user;
        if (BCrypt.checkpw(password, user.getPassword())) return user;
        else {
            user = new User();
            return user;
        }
    }

    public List<Product> getLikedProductsByUserCode(String code) {
        List<Product> liked_products = new ArrayList<>();
        for (LikedUserProduct likedUserProduct : likedUserProductRepository.findAllByUser_Password(code)) {
            System.out.println(likedUserProduct.getProduct());
            liked_products.add(likedUserProduct.getProduct());
        }
        return liked_products;
    }

    @Transactional
    public boolean likeProduct(String code, int id) {
        if (likedUserProductRepository.existsByUser_PasswordAndProduct_Id(code, id)) {
            likedUserProductRepository.removeByUser_PasswordAndProduct_Id(code, id);
            return false;
        }
        LikedUserProduct likedUserProduct = new LikedUserProduct(productRepository.findById(id), userRepository.findByPassword(code));
        likedUserProductRepository.save(likedUserProduct);
        return true;
    }


}
