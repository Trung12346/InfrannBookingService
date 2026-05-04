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
import java.util.Objects;

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
       model.addAttribute("listpicture",mediarepo.findAll());
       model.addAttribute("currentPages",start);
       model.addAttribute("totalPages",p.getTotalPages());
       model.addAttribute("DTOtranfer",new DTOlistingMedia());
       return "businessListing";
   }

    @PostMapping("/createRoom")
    public String createRoom(
            @ModelAttribute("DTOtranfer") DTOlistingMedia DTO,
            Principal p
    ) {
       listingsMedia lm = new listingsMedia();

        for (listingsMedia a : repo.FindAll()) {

            if (
                    Objects.equals(a.getM().getId(), DTO.getMedia().getId()) &&
                            Objects.equals(a.getL().getId(), DTO.getListings().getId())
            ) {
                lm = a;
                System.out.println("FOUND lm id = " + lm.getId());
                break;
            }
        }

        if (lm.getId() == 0) {
            System.out.println("NOT FOUND, CREATE NEW");
            lm = new listingsMedia();
        }

        Users u = UserRepo.findByUsername(p.getName());
        System.out.println(lm.id);
        listings listing = DTO.getListings();
        listing.setU(u);

        media media =  DTO.getMedia();
        media.setStatus(0);
        media.setPermission(u.getRole());
        media.setFileType(media.getUrl().substring(media.getUrl().lastIndexOf(".")));
        media.setU(u);


        mediarepo.save(media);
        listingRepo.saveListings(listing);

        lm.setL(listing);
        lm.setM(media);



        repo.save(lm);

        return "redirect:/businessListings/Home";
    }
   @GetMapping("/updateRoom")
    public String updateRoom(Model model,@RequestParam("id") int id,@RequestParam(value = "page",defaultValue = "0") int page){
      int start = Math.max(0,page);

      Page<listingsMedia> p = repo.FindAll(PageRequest.of(start,5));
      int totalPages = p.getTotalPages();

      if(start >= totalPages) start = 0;

      p = repo.FindAll(PageRequest.of(start,5));

       listingsMedia l = repo.Findbyid(id);

       DTOlistingMedia dto = new DTOlistingMedia();
       dto.setListings(l.getL());
       dto.setMedia(l.getM());

      model.addAttribute("list",p);
      model.addAttribute("listpicture",p);
      model.addAttribute("currentPages",start);
      model.addAttribute("totalPages",p.getTotalPages());
       model.addAttribute("DTOtranfer",dto);
       return "businessListing";
   }

   @GetMapping("/deleteRoom")
    public String deleteRoom(@RequestParam("id") int id){
      repo.remove(id);
      return "redirect:/businessListings/Home";
   }


}
