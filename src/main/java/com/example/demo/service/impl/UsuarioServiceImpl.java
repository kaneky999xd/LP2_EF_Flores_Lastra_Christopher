package com.example.demo.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.UsuarioEntity;
import com.example.demo.repository.UsarioRepository;
import com.example.demo.service.UsuarioService;
import com.example.demo.utils.Utilitarios;

import jakarta.servlet.http.HttpSession;




@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsarioRepository usuariorepository;
	
	@Override
	public void CrearUsuario(UsuarioEntity usuarioentity, Model model, MultipartFile foto) {

		
				String nombre_foto = Utilitarios.guardarImagen(foto);
				usuarioentity.setUrl_Imagen(nombre_foto);
				
				
				String passwordHash = Utilitarios.extraerHash(usuarioentity.getPassword());
				usuarioentity.setPassword(passwordHash);
				
				usuariorepository.save(usuarioentity);
				
				model.addAttribute("usuario", new UsuarioEntity());
		
	}

	@Override
	public boolean validarUsuario(UsuarioEntity usuarioentity, HttpSession session) {
		
		UsuarioEntity usuarioEncontradoCorreo = usuariorepository.findById(usuarioentity.getCorreo()).get();
				
		
		if(usuarioEncontradoCorreo == null) {
			
			return false;
		}
		 
		if(!Utilitarios.checkPassword(usuarioentity.getPassword(), 
				usuarioEncontradoCorreo.getPassword())) {
		
			return false;
			
		}
		
		session.setAttribute("usuario", usuarioEncontradoCorreo.getCorreo());
		
		return true;
	}

	@Override
	public UsuarioEntity buscarUsuarioporCorreo(String correo) {
		return usuariorepository.findByCorreo(correo);
	}

}