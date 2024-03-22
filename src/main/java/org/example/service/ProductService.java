package org.example.service;

import org.example.Main;
import org.example.entity.Seller;
import org.example.entity.Product;
import org.example.exception.ProductException;
import org.example.repository.SellerRepository;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    ProductRepository productRepository;
    SellerRepository sellerRepository;
    @Autowired
    public ProductService(ProductRepository productRepository, SellerRepository sellerRepository){
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product saveProduct(long id, Product p) throws ProductException {

        String title = p.getTitle();
        if(title == null){
            Main.log.error("Product title is null, enter a valid title");
            throw new ProductException("Product title is null, enter a valid title");
        }
        else if(title.isEmpty()){
            Main.log.error("Product title is blank, enter a valid title");
            throw new ProductException("Product title is blank, enter a valid title");

        }
        else if(p.getPrice() <= 0){
            Main.log.error("Product price should be greater than zero");
            throw new ProductException("Product price should be greater than zero");
        }
        else {
             Optional<Seller> optional = sellerRepository.findById(id);
            Seller a;
            a = optional.get();
            Product savedProduct = productRepository.save(p);
            a.getProducts().add(savedProduct);
            sellerRepository.save(a);
            return savedProduct;
        }

    }

public Product updateProduct(Product p, long id) throws ProductException {

    String title = p.getTitle();
    if(title == null ){
        Main.log.error("Product title is null, please enter a valid title");
        throw new ProductException("Product title is null, enter a valid title");
    }
    else if(title.isEmpty()){
        Main.log.error("Product title is blank, enter a valid title");
        throw new ProductException("Product title is blank, enter a valid title");

    }
    else if(p.getPrice() <= 0){
        Main.log.error("Product price should be greater than zero");
        throw new ProductException("Product price should be greater than zero");
    }
    else {
        Product updatedProduct;
        updatedProduct = productRepository.save(p);
        return updatedProduct;
    }
}

    public String deleteProduct(long id) throws ProductException {
        Optional<Product> p = productRepository.findById(id);
       if(p.isEmpty()){
           Main.log.warn("Product " + id + " does not exist..!");
           throw new ProductException("Product " + id + " does not exist..!");
       }
       else{
           productRepository.delete(p.get());
           Main.log.info("Product has been deleted successfully..!");
           return "Product has been deleted successfully..!";
       }


    }



    public Product getById(long id) throws ProductException {
        Optional<Product> p = productRepository.findById(id);
        if(p.isEmpty()){
            Main.log.warn("Product " + id + " does not exist...!");
            throw new ProductException("Product " + id + " does not exist...!");
        }else{
            return p.get();
        }
    }


    public List<Product> getAllProductsByTitle(String title) throws ProductException{
        List<Product> listOfProducts = productRepository.findByTitle2(title);
        if(!listOfProducts.isEmpty()) {
            return listOfProducts;
        }
        else{
            Main.log.warn("Product's with " + title + " does not exist..!");
            throw new ProductException("Product's with " + title + " does not exist..!");
        }
    }
}
