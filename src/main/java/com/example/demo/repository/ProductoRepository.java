package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductosEntity;

@Repository
public interface ProductosRepository extends JpaRepository<ProductosEntity, Long> {
}