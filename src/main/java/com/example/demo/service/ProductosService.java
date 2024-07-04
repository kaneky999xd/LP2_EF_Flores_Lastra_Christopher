package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.ProductosEntity;



public interface ProductosService {

    List<ProductosEntity> buscarTodosProductos();

    ProductosEntity buscarProductoPorId(Long id);
    void crearProducto(ProductosEntity producto);
}
 
