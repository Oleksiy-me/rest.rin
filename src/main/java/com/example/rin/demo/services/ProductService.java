package com.example.rin.demo.services;

import com.example.rin.demo.database.dao.Product;
import com.example.rin.demo.database.dao.User;
import com.example.rin.demo.database.repository.ProductRepository;
import com.example.rin.demo.database.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Value("${file.upload-dir}")
    private String FILE_DIRECTORY;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserProductRepository userProductRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> searchElements(int max, int min, int category, int location, int description, String text) {
        boolean first = true;
        ArrayList<Product> result = new ArrayList<>();
        ArrayList<Product> forRetain = new ArrayList<>();
        System.out.println(first);
        if (max != 0) {
            forRetain = (ArrayList<Product>) productRepository.findAllByPriceLessThan(max);
            if (first) {
                result = forRetain;
                first = false;
            } else {
                result.retainAll(forRetain);
            }
        }
        System.out.println(first);
        if (min != 0) {
            forRetain = (ArrayList<Product>) productRepository.findAllByPriceGreaterThan(min);
            if (first) {
                result = forRetain;
                first = false;
            } else {
                result.retainAll(forRetain);
            }
        }
        System.out.println(first);
        if (category != 0) {
            forRetain = (ArrayList<Product>) productRepository.findAllByCategory(category);
            if (first) {
                result = forRetain;
                first = false;
            } else {
                result.retainAll(forRetain);
            }
        }
        System.out.println(first);
        if (location != 0) {
            forRetain = (ArrayList<Product>) productRepository.findAllByOblasty(location);
            if (first) {
                result = forRetain;
                first = false;
            } else {
                result.retainAll(forRetain);
            }
        }
        System.out.println(first);
        if (!text.equals("0")) {
            System.out.println(1);
            if (description == 0) {
                forRetain = (ArrayList<Product>) productRepository.findAllByNameContaining(text);
                if (first) {
                    System.out.println(forRetain);
                    result = forRetain;
                    first = false;
                } else {
                    result.retainAll(forRetain);
                }
            } else if (description == 1) {
                forRetain = (ArrayList<Product>) productRepository.findAllByDescriptionContaining(text);
                if (first) {
                    System.out.println(forRetain);
                    result = forRetain;
                    first = false;
                } else {
                    result.retainAll(forRetain);
                }

                forRetain = (ArrayList<Product>) productRepository.findAllByNameContaining(text);
                result.addAll(forRetain);
            }

        }
        System.out.println(result);
        return result;
    }

    private String save_file(MultipartFile file) throws IOException {
        int new_name = (int) (Math.random() * 1_000_000);
        String file_type = file.getOriginalFilename().replace('.', '/').split("/")[1];
        File file_in = new File(FILE_DIRECTORY + new_name + "." + file_type);
        System.out.println(FILE_DIRECTORY + new_name + "." + file_type);
        file_in.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file_in);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return FILE_DIRECTORY + new_name + "." + file_type;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public void deletebById(int id) {
        productRepository.deleteById(id);
    }

    public void getById(int id) {
        productRepository.findById(id);
    }

    public User getUserByProductId(int i) {
        return userProductRepository.getByProductId(i).getUser();
    }


}
