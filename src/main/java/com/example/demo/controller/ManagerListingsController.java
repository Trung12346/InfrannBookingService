package com.example.demo.controller;

import com.example.demo.model.listings;
import com.example.demo.service.UserService;
import com.example.demo.service.listingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ManagerListings")
public class ManagerListingsController {

    @Autowired
    listingService repo;

    @Autowired
    UserService repoUser;



    @GetMapping("/home")
    public String Managerlistings(Model model, @RequestParam(value = "page",defaultValue = "0") int page, HttpServletRequest request){

        int pageSize = 5;

        Page<listings> tempPage = repo.FindAll(PageRequest.of(0, pageSize));
        int totalPages = tempPage.getTotalPages();

        int currentPage = page;

        if (totalPages == 0) {
            currentPage = 0;
        } else if (page < 0) {
            currentPage = 0;
        } else if (page >= totalPages) {
            currentPage = totalPages - 1;
        }

        Page<listings> list = repo.FindAll(PageRequest.of(currentPage, pageSize));

        model.addAttribute("list", list);
        model.addAttribute("listings", new listings());
        model.addAttribute("listuser", repoUser.Findalluser());
        model.addAttribute("listfilter", repo.FindAllListing());
        model.addAttribute("currentURL", "/ManagerListings/home");
        model.addAttribute("currentPages", currentPage);
        model.addAttribute("totalPages", list.getTotalPages());
         model.addAttribute("idForUpdate",null);
        return "HomeListingManager";

    }

    @PostMapping("/createRoom")
    public String createroom(listings l){


        repo.saveListings(l);

        return "redirect:/ManagerListings/home";
    }

    @GetMapping("/updateRoom")
    public String updateRoom(Model model, @RequestParam("id") int id,HttpServletRequest request
    ,@RequestParam(value = "currentPages",defaultValue = "0") int page    ){
        int Pagesize = 5;

        Page<listings> p = repo.FindAll(PageRequest.of(0,Pagesize));
        int totalPages = p.getTotalPages();


        if(page > totalPages - 1 || page < 0){
            page = 0;
        }
        int currentPages = page;
        p = repo.FindAll(PageRequest.of(currentPages,Pagesize));



        model.addAttribute("list",p);
        model.addAttribute("listings",repo.Findbyid(id));
        model.addAttribute("listuser",repoUser.Findalluser());
        model.addAttribute("listfilter",repo.FindAllListing());
        model.addAttribute("currentURL",request.getRequestURI());
        model.addAttribute("currentPages",currentPages);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("idForUpdate",id);
      return "HomeListingManager";
    }

    @GetMapping("/filterRoom")
    public String Filter(Model model,@RequestParam("title") String title,@RequestParam("danhmuc") String danhmuc,HttpServletRequest request,
                         @RequestParam(value = "page",defaultValue = "0") int page){
         int Pagesize = 5;

         String keyword;

         if(!title.trim().isEmpty()){
             keyword = title;
         }
         else if(!danhmuc.trim().isEmpty()){
             keyword = danhmuc;
         }
         else{
             return "redirect:/ManagerListings/home";
         }



        Page<listings> p = repo.FilterbyName(keyword,PageRequest.of(0,Pagesize));
         int totalPages = p.getTotalPages();
         if(page < 0 || page > totalPages){
             page = 0;
         }
          p = repo.FilterbyName(keyword,PageRequest.of(page,Pagesize));

        if (title.isEmpty() && !danhmuc.isEmpty()){
            model.addAttribute("currentURL",request.getRequestURI());

            System.out.println(title+danhmuc);
            model.addAttribute("list",p);
            model.addAttribute("listfilter",repo.FindAllListing());
            model.addAttribute("listings",new listings());
            model.addAttribute("currentPages",page);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("listuser", repoUser.Findalluser());
            model.addAttribute("idForUpdate",null);
        }else{
            model.addAttribute("currentURL",request.getRequestURI());
            model.addAttribute("list",p);
            model.addAttribute("listfilter",repo.FindAllListing());
            model.addAttribute("listings",new listings());
            model.addAttribute("listuser", repoUser.Findalluser());
            model.addAttribute("currentPages",page);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("idForUpdate",null);
        }
        return "HomeListingManager";
    }
}
