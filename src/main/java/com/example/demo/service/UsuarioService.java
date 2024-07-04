package com.example.demo.service;





import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.UsuarioEntity;

import jakarta.servlet.http.HttpSession;

public interface UsuarioService {

	void CrearUsuario(UsuarioEntity usuarioentity, Model model, MultipartFile foto);
	boolean validarUsuario(UsuarioEntity usuarioentity ,HttpSession session);
	UsuarioEntity buscarUsuarioporCorreo(String correo);
	
}