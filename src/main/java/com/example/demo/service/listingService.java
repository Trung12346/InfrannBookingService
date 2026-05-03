package com.example.demo.service;

import com.example.demo.model.listings;
import com.example.demo.repository.listingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class listingService {

    @Autowired
    listingRepo repo;

    public List<listings> FindAllListing(){
        return repo.findAll();
    }
    public listings Findbyid(int id){
        return repo.findById(id).orElse(null);
    }
    public void deleteListings(int id){
        repo.delete(repo.findById(id).orElse(null));
    }
    public void saveListings(listings l){
        repo.save(l);
    }

    public Page<listings> FilterbyName(String title,Pageable p){
        return repo.filterByName(title,p);
    }

    public Page<listings> FindAll(Pageable p){
        return repo.findAll(p);
    }
}
