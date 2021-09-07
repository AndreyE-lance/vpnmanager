package com.elantsev.vpnmanager.controller;

import com.elantsev.vpnmanager.model.OperationResult;
import com.elantsev.vpnmanager.model.User;
import com.elantsev.vpnmanager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/default")
    public String getIndex(@RequestParam(required = false)String success, @RequestParam(required = false)String msg, HttpServletRequest httpServletRequest, Model model){
        if(httpServletRequest.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("allUsers", userService.getAllUsers());
            OperationResult operationResult = null;
            if(success==null){
                operationResult = new OperationResult(false, "", false);
            } else {
                operationResult = new OperationResult(Boolean.parseBoolean(success),msg, true);
            }
            model.addAttribute("save",operationResult);
            return "manager";}
        return "news";
    }

    @PostMapping("/default")
    public String deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                             @RequestParam(required = true, defaultValue = "" ) String action){
        userService.deleteUser(userId);
        return "redirect:/default";
    }

    @PostMapping("/newuser")
    public String addUser(@ModelAttribute(value="user") User user){
        if(userService.saveUser(user)){
            return "redirect:/default?success=true&msg=OK";
        } else {
            return "redirect:/default?success=false&msg=Fail";
        }
    }

    @GetMapping("/newuser")
    public String showNewUserForm(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "add";
    }
}
