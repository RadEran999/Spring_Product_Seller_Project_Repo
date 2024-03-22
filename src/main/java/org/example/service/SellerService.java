package org.example.service;

import org.example.Main;
import org.example.entity.Seller;
import org.example.exception.SellerException;
import org.example.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    SellerRepository sellerRepository;
    @Autowired
    public SellerService(SellerRepository sellerRepository){
        this.sellerRepository = sellerRepository;
    }
    public List<Seller> getAllSellers(){
        return sellerRepository.findAll();
    }

    public Seller getById(long id)  throws SellerException {
        Optional<Seller> s = sellerRepository.findById(id);
        if(s.isEmpty()){
            throw new SellerException("No Such Seller Exists ... ");
        }else{
            return s.get();
        }
    }
    public Seller saveSeller(Seller a) throws SellerException {
        String name = a.getName();

        if(name == null)
        {
            Main.log.error("Seller is null");
            throw new SellerException("Seller is null");
        }
        else if(name.isEmpty()){
            Main.log.error("Seller is blank");
            throw new SellerException("Seller is blank");
        }
        else{
                List<Seller> sellers = getAllSellers();

                for(int i = 0; i < sellers.size(); i++) {
                    if (sellers.get(i).name.equals(a.name)) {
                        Main.log.error("Seller name already exists..!");
                        throw new SellerException("Seller name already exists..!");
                    }
                }
                return sellerRepository.save(a);
        }

    }
}
