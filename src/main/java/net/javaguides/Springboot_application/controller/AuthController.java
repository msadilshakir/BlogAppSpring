package net.javaguides.Springboot_application.controller;

import jakarta.servlet.Registration;
import jakarta.validation.Valid;
import net.javaguides.Springboot_application.dto.RegistrationDto;
import net.javaguides.Springboot_application.entity.User;
import net.javaguides.Springboot_application.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "/blog/register";
    }

    //handler to handle user form submit request
    @PostMapping("/register/save")
    private String register(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult result, Model model){
        User existingUser = userService.findByEmail(user.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already a user with same email");
        }
        if(result.hasErrors()){
            model.addAttribute("user",user);
            return "blog/register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "/blog/login";
    }
}
