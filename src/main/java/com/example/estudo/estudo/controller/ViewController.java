package com.example.estudo.estudo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ViewController {

    static Map<String, String> errorMapping;
    static{
        errorMapping = new HashMap<>(1);
        errorMapping.put("error","* usuário ou senha inválidos");
    }


    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null)
            return new ModelAndView("login", errorMapping);

        return new ModelAndView("login");
    }


    @GetMapping("/")
    public String yourPage(Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth.isAuthenticated()) {
            String role = auth.getAuthorities().toArray()[0].toString();
            if (role != "ROLE_ANONYMOUS")
                model.addAttribute("username", auth.getName());
        }

        model.addAttribute("animais", animList);

        return "index";
    }





    public static class Animal {
        public String val1;
        public String val2;
        public String val3;

        public Animal(){}
        public Animal(String val1, String val2, String val3)
        {this.val1 = val1; this.val2 = val2; this.val3 = val3;}
    }
    static Animal[] animList;

    static {
        animList = new Animal[] {
                new Animal("Pepito",   "dog", "12.4Kg"),
                new Animal( "Fiona",   "cat",  "1.5Kg"),
                new Animal(   "Sid", "mouse",    "80g"),
                new Animal(   "Rex", "cocky",  "1.4Kg")
        };
    }

}