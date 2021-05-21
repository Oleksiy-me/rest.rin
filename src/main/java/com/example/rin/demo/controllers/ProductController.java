package com.example.rin.demo.controllers;

import com.example.rin.demo.database.dao.Product;

import com.example.rin.demo.database.dao.join_table.UserProduct;
import com.example.rin.demo.database.repository.LikedUserProductRepository;
import com.example.rin.demo.database.repository.ProductRepository;
import com.example.rin.demo.database.repository.UserProductRepository;
import com.example.rin.demo.database.repository.UserRepository;
import com.example.rin.demo.services.ProductService;
import com.example.rin.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserProductRepository userProductRepository;
    @Autowired
    LikedUserProductRepository likedUserProductRepository;

    @GetMapping("/all")
    public List<Product> get_all() {
        List<Product> list = productRepository.findAllByOrderByIdDesc();
        list.retainAll(productRepository.findAllByActive(1));
        return list;
    }
    @PostMapping("/{code}")
    public Product upload_product_0(@RequestBody Product product, @PathVariable String code) {

        product = productRepository.save(product);
        userProductRepository.save(new UserProduct(product, userRepository.findByPassword(code)));
        return userProductRepository.getByProductId(product.getId()).getProduct();
    }
    @GetMapping("/{text}/{description}/{location}/{category}/{min}/{max}")
    public List<Product> searchProduct(@PathVariable int max, @PathVariable int min, @PathVariable int category, @PathVariable int location, @PathVariable int description, @PathVariable String text) {
        if (max == 0 && min == 0 && category == 0 && location == 0 && description == 0 && text.equals("0")) {
            return productService.findAll();
        }
        List<Product> list = (productService.searchElements(max, min, category, location, description, text));
        list.retainAll(productRepository.findAllByActive(1));
        return list;
    }
    @GetMapping("/search/{location}/{radius}")
    public List<Product> searchProductInRadius(@PathVariable int radius, @PathVariable String location) {
        List<Product> products = productRepository.findAllByActive(1);
        products.stream().filter(new Predicate<Product>() {
            @Override
            public boolean test(Product product) {
                float longitude_user = Float.parseFloat(location.split("!")[0]);
                float latitude_user = Float.parseFloat(location.split("!")[1]);
                float longitude_product = Float.parseFloat(product.getLocation().split("/")[0]);
                float latitude_product = Float.parseFloat(product.getLocation().split("/")[1]);
                //TODO not working
                double length = (2 * 6371 * (1 /
                        Math.sin(Math.sqrt(Math.sin((longitude_product - longitude_user) / 2) * Math.sin((longitude_product - longitude_user) / 2) + Math.cos(longitude_product) * Math.cos(longitude_user) * Math.sin((latitude_product - latitude_user) / 2) * Math.sin((latitude_product - latitude_user) / 2)))));

                System.out.println(product.getId() + " " + length);
                if (radius > length) {
                    return true;
                }

                return false;
            }
        }).collect(Collectors.toList());
        return products;
    }
    @DeleteMapping("/{code}/{id}")
    public Product delete_product(@PathVariable int id, @PathVariable String code) {
        if (userProductRepository.existsByUser_PasswordAndProduct_Id(code, id)) {
            productRepository.deleteById(id);
        }
        return null;
    }
    @PostMapping("activate/{code}/{id}")
    public Product activeProductByUserCodeAndId(@PathVariable String code, @PathVariable int id) {

        if (userRepository.findByPassword(code) == null) return new Product();
        Product active_products = userProductRepository.getByProductIdAndUser_Password(id, code).getProduct();

        if (active_products.getActive() == 0) {
            active_products.setActive(1);
        } else {
            active_products.setActive(0);
        }

        return productRepository.save(active_products);
    }

    @GetMapping("unactive/{code}")
    public List<Product> getUnactiveProductsByUserCode(@PathVariable String code) {
        List<Product> unactive_products = new ArrayList<>();
        for (UserProduct userProduct : userProductRepository.findAllByUser_PasswordAndProduct_Active(code, 0)) {
            unactive_products.add(userProduct.getProduct());
        }
        return unactive_products;
    }
    @GetMapping("active/{code}")
    public List<Product> getActiveProductsByUserCode(@PathVariable String code) {
        List<Product> active_products = new ArrayList<>();
        for (UserProduct userProduct : userProductRepository.findAllByUser_PasswordAndProduct_Active(code, 1)) {
            active_products.add(userProduct.getProduct());
        }
        return active_products;
    }

    @GetMapping("/liked/{code}/{id}")
    public boolean isProductLiked(@PathVariable int id, @PathVariable String code) {
        return likedUserProductRepository.existsByUser_PasswordAndProduct_Id(code, id);
    }
    @GetMapping("/liked/{code}")
    public List<Product> getLikedProductsByUserCode(@PathVariable String code) {
        return userService.getLikedProductsByUserCode(code);
    }
    @PostMapping("/like/{code}/{id}")
    public boolean likeProduct(@PathVariable int id, @PathVariable String code) {
        return userService.likeProduct(code, id);
    }


}
