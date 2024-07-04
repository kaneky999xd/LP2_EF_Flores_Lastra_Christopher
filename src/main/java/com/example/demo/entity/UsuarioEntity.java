package com.example.demo.entity;

import java.util.Date;



import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UsuarioEntity {

	@Id
	@Column(name = "correo")
	private String correo;
	
	@Column(name = "nombres")
	private String nombres;
	
	@Column(name = "apellido")
	private String apellido;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_nac;
	
	@Column(name = "imagen")
	private String url_Imagen;
}