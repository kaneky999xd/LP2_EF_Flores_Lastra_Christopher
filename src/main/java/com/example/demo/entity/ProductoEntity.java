package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



	@Entity
	@Table(name = "tb_productos")
	@Getter
	@Setter
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public class ProductosEntity {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long productoId;
		
		private String nombre;
		private Double precio;
		private Integer stock;
		private String categoria;
		
		
		 public Long getId() {
		        return productoId;
		    }

		    public void setId(Long id) {
		        this.productoId = id;
		    }

		    public String getNombre() {
		        return nombre;
		    }

		    public void setNombre(String nombre) {
		        this.nombre = nombre;
		    }

		    public Double getPrecio() {
		        return precio;
		    }

		    public void setPrecio(Double precio) {
		        this.precio = precio;
		    }

		    public Integer getStock() {
		        return stock;
		    }

		    public void setStock(Integer stock) {
		        this.stock = stock;
		    }

		    public String getCategoria() {
		        return categoria;
		    }

		    public void setCategoria(String categoria) {
		        this.categoria = categoria;
		    }
		}
		
	