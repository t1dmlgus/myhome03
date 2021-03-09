package com.s1dmlgus.myhome03.web.controller;


import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.user.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @GetMapping()
    public String index(Model model, @AuthenticationPrincipal PrincipalDetails userDetails){

        try{

            Member user = userDetails.getUser();
            System.out.println("user = " + user);

            model.addAttribute("user", user);

        }catch (NullPointerException e){

//            e.printStackTrace();
            System.out.println("NullPointerException11 = " + e);
        }


        return "index";
    }


}
