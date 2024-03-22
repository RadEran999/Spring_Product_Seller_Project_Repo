package org.example.controller;

import org.example.Main;
import org.example.entity.Seller;
import org.example.exception.SellerException;
import org.example.service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {
    SellerService sellerService;
    public SellerController(SellerService sellerService){
        this.sellerService = sellerService;
    }
    @GetMapping("seller")
    public ResponseEntity<List<Seller>> getAllSellers(){
        List<Seller> sellers = sellerService.getAllSellers();
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @GetMapping("seller/{id}")
    public ResponseEntity<?> getSellerById(@PathVariable long id){
        try{
            Seller s = sellerService.getById(id);
            return new ResponseEntity<>(s, HttpStatus.OK);
        }catch (SellerException e){
            Main.log.error("Seller not found..!");
            String errorResponse = "Seller not found..!";
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping("seller")
    public ResponseEntity<?> addSeller(@RequestBody Seller a) throws SellerException {
        try {
            Seller seller = sellerService.saveSeller(a);
            return new ResponseEntity<>(seller, HttpStatus.CREATED);
        } catch(SellerException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
