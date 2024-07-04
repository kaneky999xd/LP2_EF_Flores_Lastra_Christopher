package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductoEntity;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoRepository productorepository;
	
	@Override
	public List<ProductoEntity> buscarTodosProductos() {
		return productorepository.findAll();
	}

	@Override
	public ProductoEntity buscarPorId(Integer id) {
		return productorepository.findById(id).get();
	}

	@Override
	public ProductoEntity crearProducto(ProductoEntity producto) {
		return productorepository.save(producto);
	}

	@Override
	public ProductoEntity actualizarProducto(ProductoEntity productos) {
		
		ProductoEntity productoBuscado = buscarPorId(productos.getId_Producto());
		if(productoBuscado != null) {
			
			productoBuscado.setNombre_producto(productos.getNombre_producto());
			productoBuscado.setPrecio(productos.getPrecio());
			productoBuscado.setStock(productos.getStock());
			productoBuscado.setCatagoriaLYSG(productos.getCatagoriaLYSG());
			return productorepository.save(productoBuscado);
					
		}
		
		return null;
		
	}

	@Override
	public void eliminarProducto(Integer id) {

		productorepository.deleteById(id);
		
	}

}