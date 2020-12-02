package com.cybertek.controller;

import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Role;
import com.cybertek.service.RoleService;
import com.cybertek.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    //same path
    @GetMapping({"/create", "/initialize"})
    public String createUser(Model model){

        model.addAttribute("user", new UserDTO());
        model.addAttribute("roleList", roleService.findAll());
        model.addAttribute("userList", userService.findAll());

        return "user/create";
    }

    @PostMapping("/create")
    public String employeeAdd(@ModelAttribute("user") UserDTO userDTO, Model model) {
        toString(userDTO);
        userService.save(userDTO);
        return "redirect:/user/create";
    }


    @GetMapping("/update/{username}")
    public String editUser(@PathVariable("username") String username, Model model){
        model.addAttribute("user", userService.findById(username));
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("roleList", roleService.findAll());
        return "user/update";
    }

    @PostMapping("/update/{username}")
    public String updateUser(@PathVariable("username") String username, UserDTO user){
        userService.deleteById(username);
        userService.update(user);
        return "redirect:/user/create";

    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username){
        userService.deleteById(username);
        return "redirect:/user/create";
    }

    public void toString(UserDTO userDTO){
        try {
            ObjectMapper mapper =new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            System.out.println(mapper.writeValueAsString(userDTO));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
