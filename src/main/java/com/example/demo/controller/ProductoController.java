package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.entity.DetallePedidoEntity;
import com.example.demo.entity.ProductosEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.model.Pedido;
import com.example.demo.service.ProductosService;
import com.example.demo.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductosService productoService;

    @GetMapping("/menu")
    public String showMenu(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        String correo = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
        model.addAttribute("foto", usuarioEntity.getUrlImagen());

        List<ProductosEntity> productos = productoService.buscarTodosProductos();
        model.addAttribute("productos", productos);

        List<Pedido> productoSession = null;
        if (session.getAttribute("carrito") == null) {
            productoSession = new ArrayList<Pedido>();
        } else {
            productoSession = (List<Pedido>) session.getAttribute("carrito");
        }
        model.addAttribute("cant_carrito", productoSession.size());

        List<DetallePedidoEntity> detallePedidoEntityList = new ArrayList<DetallePedidoEntity>();
        Double total = 0.0;

        for (Pedido pedido : productoSession) {
            DetallePedidoEntity detallePedidoEntity = new DetallePedidoEntity();
            ProductosEntity productoEntity = productoService.buscarProductoPorId(Long.valueOf(pedido.getProductoId()));
            detallePedidoEntity.setProductoEntity(productoEntity);
            detallePedidoEntity.setCantidad(pedido.getCantidad());
            detallePedidoEntityList.add(detallePedidoEntity);
            total += pedido.getCantidad() * productoEntity.getPrecio();
        }
        model.addAttribute("carrito", detallePedidoEntityList);
        model.addAttribute("total", total);

        return "menu";
    }


    // Método GET para mostrar el formulario de registro de productos
    @GetMapping("/registrar_producto")
    public String mostrarFormularioRegistro() {
        return "registrar_producto";
    }

    // Método POST para agregar un producto
    @PostMapping("/agregar_producto")
    public String agregarProducto(@RequestParam("nombre") String nombre,
                                  @RequestParam("precio") Double precio,
                                  @RequestParam("stock") Integer stock,
                                  @RequestParam("categoria") String categoria) {

        // Crear un nuevo objeto ProductosEntity y configurar los datos
        ProductosEntity producto = new ProductosEntity();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);

        // Guardar el producto usando el servicio
        productoService.crearProducto(producto);

        // Redirigir de vuelta al menú principal después de agregar el producto
        return "redirect:/menu";
    }
    
    
    
    //Actualizar producto
    
    @GetMapping("/editar_producto/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        ProductosEntity producto = productoService.buscarProductoPorId(id);
        model.addAttribute("producto", producto);
        return "editar_producto"; // Nombre de la vista de edición (editar_producto.html)
    }

    @PostMapping("/actualizar_producto/{id}")
    public String actualizarProducto(@PathVariable("id") Long id,
                                     @RequestParam("nombre") String nombre,
                                     @RequestParam("precio") Double precio,
                                     @RequestParam("stock") Integer stock,
                                     @RequestParam("categoria") String categoria) {

        ProductosEntity productoActual = productoService.buscarProductoPorId(id);

        if (productoActual != null) {
            productoActual.setNombre(nombre);
            productoActual.setPrecio(precio);
            productoActual.setStock(stock);
            productoActual.setCategoria(categoria);

            productoService.actualizarProducto(productoActual);
        }

        return "redirect:/menu";
    }
    
    
    
    
    //Detalles Producto 
    
    

    @GetMapping("/detalles_producto/{id}")
    public String mostrarDetallesProducto(@PathVariable Long id, Model model) {
        ProductosEntity productosEntity = productoService.buscarProductoPorId(id);
        model.addAttribute("producto", productosEntity);
        return "detalles_producto";
    }
    
    
    //Eliminiar Producto
    
    
    // Método para eliminar un producto
    @PostMapping("/eliminar_producto/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/menu"; 
    }
}
    

    
    
    
    
    

    
    
    
    
