package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.entity.ProductosEntity;
import com.example.demo.repository.ProductosRepository;
import com.example.demo.service.ProductosService;


@Service
public class ProductoServiceImpl implements ProductosService{

	 @Autowired
	    private ProductosRepository productosRepository;

	 @Override
	    public List<ProductosEntity> buscarTodosProductos() {
	        return productosRepository.findAll();
	    }

	    @Override
	    public ProductosEntity buscarProductoPorId(Long id) {
	        return productosRepository.findById(id).orElse(null);
	    }

	    @Override
	    public void crearProducto(ProductosEntity producto) {
	        productosRepository.save(producto);
	    }
	    
	    @Override
	    public void actualizarProducto(ProductosEntity producto) {
	        productosRepository.save(producto);
	        
	        
	        
	    }
	    @Override
	    public void eliminarProducto(Long id) {
	        productosRepository.deleteById(id);
	    }
	    
	    
	    @Override
	    public List<ProductosEntity> obtenerTodosProductos() {
	        return productosRepository.findAll();
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
}