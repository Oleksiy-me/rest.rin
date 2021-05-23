package com.example.rin.demo.controllers;

import com.example.rin.demo.database.dao.Product;
import com.example.rin.demo.database.dao.User;
import com.example.rin.demo.database.dao.join_table.LikedUserProduct;
import com.example.rin.demo.database.dao.join_table.UserProduct;
import com.example.rin.demo.database.repository.LikedUserProductRepository;
import com.example.rin.demo.database.repository.ProductRepository;
import com.example.rin.demo.database.repository.UserProductRepository;
import com.example.rin.demo.database.repository.UserRepository;
import com.example.rin.demo.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserProductRepository userProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LikedUserProductRepository likedUserProductRepository;

    @PostMapping("{name}/{email}/{password}/{phone}")
    public User upload_user(@PathVariable String name,
                            @PathVariable String email,
                            @PathVariable String password,
                            @PathVariable String phone) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setPhone(phone);
        return userService.createUser(user);
    }

    @PutMapping("name/{code}/{name}")
    public User change_user_name(@PathVariable String name,
                                 @PathVariable String code) {
        User user = userRepository.findByPassword(code);
        user.setName(name);
        return userRepository.save(user);
    }

    @PutMapping("email/{code}/{email}")
    public User change_user_email(@PathVariable String email,
                                  @PathVariable String code) {
       User user = userRepository.findByPassword(code);
        user.setEmail(email);
        return userService.createUser(user);
    }

    @PutMapping("phone/{code}/{phone}")
    public User change_user_phone(@PathVariable String phone,
                                  @PathVariable String code) {
        User user = userRepository.findByPassword(code);
        user.setPhone(phone);
        return userService.createUser(user);
    }

    @GetMapping("/{code}")
    public User get_user(@PathVariable String code) {
        return userRepository.findByPassword(code);
    }

    @PostMapping("/login/{email}/{password}")
    public User login_user(@PathVariable String email,
                           @PathVariable String password) {
        return userService.existsByEmailandPassword(email, password);
    }

    @GetMapping("/product/{id}")
    public User getUserByProductId(@PathVariable int id) {
        Product product = productRepository.findById(id);
        System.out.println(product.getView());
        product.setView(product.getView() + 1);
        productRepository.save(product);
        System.out.println(productRepository.findById(id).getView());
        User user = userProductRepository.getByProductId(id).getUser();
        user.setPassword(null);
        return user;
    }

    @PostMapping("/password/restore/{code}")
    public boolean password_restore(@PathVariable String code) {
        //TODO restore password
        return true;
    }

    @PostMapping("/email/{code}/{user_id}/{product_id}/{text}")
    public boolean send_email(@PathVariable String code, @PathVariable int user_id, @PathVariable int product_id, @PathVariable String text) {
        //TODO send email
        return true;
    }

}
