package org.example.controller;

import org.example.Main;
import org.example.entity.Product;
import org.example.exception.ProductException;
import org.example.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
public class ProductController {
    ProductService productService;
    public static Logger log = LoggerFactory.getLogger(Main.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping(value = "product")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> products;
        products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        try {
            Product p = productService.getById(id);
            return new ResponseEntity<>(p, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("seller/{id}/product")
    public ResponseEntity<?> addProduct(@RequestBody Product p, @PathVariable long id)
            throws ProductException {
        try {
            Product product = productService.saveProduct(id, p);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);


        }
    }

    @PutMapping("product/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody Product p, @PathVariable long id) throws ProductException {
        try {
            p.setId(id);
            Product product = productService.updateProduct(p, id);
            log.info("Product has been updated successfully");
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) throws ProductException {
        try {
            String s = productService.deleteProduct(id);
            Main.log.info(s);
            return new ResponseEntity<>(s, HttpStatus.GONE);
        } catch (ProductException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }


    }

    /*
    @GetMapping(value = "/product", params = "title")
    public ResponseEntity<List<Product>> getAllProductsByTitle(@RequestParam String title) {
        List<Product> products;
        if (title == null) {
            products = productService.getAllProducts();
        } else {
            products = productService.getAllProductsByTitle(title);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

*/

}
