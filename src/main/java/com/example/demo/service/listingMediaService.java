package com.example.demo.service;

import com.example.demo.model.listingsMedia;
import com.example.demo.repository.listingMediaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class listingMediaService {

    @Autowired
    listingMediaRepo repo;

    public List<listingsMedia> FindAll(){
        return repo.findAll();
    }
    public Page<listingsMedia> FindAll(Pageable p){
        return repo.findAll(p);
    }
    public listingsMedia Findbyid(int id){
        return repo.findById(id).orElse(null);
    }
    public void save(listingsMedia l){
     repo.save(l);
    }
    public void remove(int id){
        repo.delete(Findbyid(id));
    }

}
