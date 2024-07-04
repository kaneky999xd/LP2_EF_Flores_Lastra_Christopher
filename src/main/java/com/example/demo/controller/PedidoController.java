package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.DetallePedidoEntity;
import com.example.demo.entity.PedidosEntity;
import com.example.demo.entity.ProductosEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.model.Pedido;
import com.example.demo.repository.PedidosRepository;

import jakarta.servlet.http.HttpSession;





@Controller
public class PedidoController {
	
	@Autowired
	private PedidosRepository pedidoRepository;
	
	@GetMapping("/guardar_factura")
	public String guardarFactura(HttpSession session) {
		String correoString = (String) session.getAttribute("usuario");
		UsuarioEntity usuarioEntity = new UsuarioEntity();
		usuarioEntity.setCorreo(correoString);
		
		PedidosEntity pedidoEntity = new PedidosEntity();
		pedidoEntity.setFechaCompra(LocalDate.now());
		pedidoEntity.setUsuarioEntity(usuarioEntity);
		
		List<DetallePedidoEntity> detallePedidoEntityList = new ArrayList<DetallePedidoEntity>();
		Double total = 0.0;
		
		List<Pedido>productoSession = null;
		if(session.getAttribute("carrito") == null) {
			productoSession = new ArrayList<Pedido>();
		}else {
			productoSession = (List<Pedido>) session.getAttribute("carrito");
		}
		
		for(Pedido pedido: productoSession) {
			DetallePedidoEntity detallePedidoEntity = new DetallePedidoEntity();
			ProductosEntity productoEntity = new ProductosEntity();
			productoEntity.setProductoId(pedido.getProductoId().longValue());
			
			detallePedidoEntity.setProductoEntity(productoEntity);
			detallePedidoEntity.setCantidad(pedido.getCantidad());
			detallePedidoEntity.setPedidoEntity(pedidoEntity);
			detallePedidoEntityList.add(detallePedidoEntity);
			
		}
		pedidoEntity.setDetallePedido(detallePedidoEntityList);
		pedidoRepository.save(pedidoEntity);
		
		session.removeAttribute("carrito");
		return "redirect:/menu";
		
	}

}