package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.UsuarioEntity;
import com.example.demo.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/reg")
    private String viewRegistrarUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        model.addAttribute("registroCorrecto", null); // Agregado para evitar errores de referencia nula en Thymeleaf
        return "registrar_usuario";
    }
    
    @PostMapping("/registrarUsuario")
    private String registrarUsuario(UsuarioEntity usuarioEntity, Model model, 
            @RequestParam("fotoUsuario") MultipartFile fotoUsuario) {
        
        usuarioService.CrearUsuario(usuarioEntity, model, fotoUsuario);
        model.addAttribute("registroCorrecto", "Usuario registrado exitosamente");
        return "registrar_usuario";
    }
    
    @GetMapping("/")
    private String viewLogin(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        return "login_usuario";
    }
    
    @PostMapping("/login")
    private String loginUsuario(UsuarioEntity usuarioEntity, Model model, HttpSession session) {
        boolean usuarioValido = usuarioService.validarUsuario(usuarioEntity, session);
        if (usuarioValido) {
            return "redirect:/menu";
        } else {
            model.addAttribute("loginInvalido", "Usuario no registrado o credenciales incorrectas");
            return "login_usuario";
        }
    }

    @GetMapping("/cerrar_sesionP")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
