package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.listingRepo;
import com.example.demo.repository.mediaRepo;
import com.example.demo.service.listingMediaService;
import com.example.demo.service.listingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.security.Principal;

@Controller
@RequestMapping("/businessListings")
public class BusinessListingsController {


    @Autowired
    listingMediaService repo;
    @Autowired
    UserRepository UserRepo;
    @Autowired
    listingService listingRepo;
    @Autowired
    mediaRepo mediarepo;



   @GetMapping("/Home")
    public String home(Model model,@RequestParam(value = "page",defaultValue = "0") int page ){
        int start = Math.max(0,page);

       Page<listingsMedia> p = repo.FindAll(PageRequest.of(start,5));
       if(start > p.getTotalPages()) start = 0;

       p = repo.FindAll(PageRequest.of(start,5));

       model.addAttribute("list",p);
       model.addAttribute("currentPages",start);
       model.addAttribute("totalPages",p.getTotalPages());
       model.addAttribute("listings",new listings());
       model.addAttribute("media",new media());
       model.addAttribute("DTOtranfer",new DTOlistingMedia());
       return "businessListing";
   }

    @PostMapping("/createRoom")
    public String createRoom(
            @ModelAttribute("DTOtransfer") DTOlistingMedia DTO,
            Principal p
    ) {
        listingsMedia lm = new listingsMedia();

        Users u = UserRepo.findByUsername(p.getName());

        listings listing = DTO.getListings();
        listing.setU(u);

        media media =  DTO.getMedia();
        media.setPermission(u.getRole());

        mediarepo.save(media);
        listingRepo.saveListings(listing);

        lm.setL(listing);
        lm.setM(media);

        repo.save(lm);

        return "redirect:/businessListings/Home";
    }
//   @GetMapping("")



}
