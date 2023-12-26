package com.example.productmanagement.service;

import com.example.productmanagement.model.Product;
import com.example.productmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    @Override
    public String deleteProduct(Integer id) {
        Optional<Product> optionalProduct = productRepo.findById(id);

        if (optionalProduct.isPresent()) {
            productRepo.delete(optionalProduct.get());
            return "Product deleted successfully";
        }

        return "Product not found";
    }

    @Override
    public Product editProduct(Product newProduct, Integer id) {
        return productRepo.findById(id)
                .map(oldProduct -> {
                    oldProduct.setProductName(newProduct.getProductName());
                    oldProduct.setDescription(newProduct.getDescription());
                    oldProduct.setPrice(newProduct.getPrice());
                    oldProduct.setStatus(newProduct.getStatus());
                    return productRepo.save(oldProduct);
                })
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }
}
