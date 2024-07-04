package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.ProductoEntity;
import com.example.demo.entity.CategoriaEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.repository.ProductoRepository;

import com.example.demo.service.CategoriaService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.impl.PdfService;

import jakarta.servlet.http.HttpSession;



@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoservice;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService tipoCategoriaService;

    @Autowired
    private ProductoRepository productosRepository;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/menu")
    private String ViewMenu(HttpSession session, Model model) {

        if(session.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        String correo = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioporCorreo(correo);
        model.addAttribute("usuarioNombre", "Bienvenido "+usuarioEncontrado.getNombres());

        List<ProductoEntity> productos = productoservice.buscarTodosProductos();
        model.addAttribute("lst_productos", productos);

        return "menu";
    }

    @GetMapping("/registrar_producto")
    private String ViewRegistrarProducto(Model model, HttpSession session) {

        String correo = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioporCorreo(correo);

        List<CategoriaEntity> categorias = tipoCategoriaService.buscarTodosCategorias();
        model.addAttribute("lst_categorias", categorias);
        model.addAttribute("producto", new ProductoEntity());
        model.addAttribute("usuarioNombre", "Bienvenido "+usuarioEncontrado.getNombres());

        return "crear_producto";
    }

    @PostMapping("/registrar_producto")
    private String RegistrarProducto(ProductoEntity producto) {

        productoservice.crearProducto(producto);

        return "redirect:/menu";
    }

    @GetMapping("/editar_producto/{id}")
    private String ViewEditarProducto(@PathVariable("id") Integer id, Model model, HttpSession session) {

        String correo = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioporCorreo(correo);

        ProductoEntity productoBuscar = productoservice.buscarPorId(id);
        List<CategoriaEntity> categorias = tipoCategoriaService.buscarTodosCategorias();
        model.addAttribute("lst_categorias", categorias);

        model.addAttribute("usuarioNombre", "Bienvenido "+usuarioEncontrado.getNombres());
        model.addAttribute("producto", productoBuscar);

        return "editar_producto";
    }

    @PostMapping("/editar_producto")
    private String EditarProducto(Model model, ProductoEntity producto) {

        productoservice.actualizarProducto(producto);

        return "redirect:/menu";
    }

    @GetMapping("/ver_producto/{id}")
    private String ViewDetallesProducto(@PathVariable("id") Integer id, Model model, HttpSession session) {

        String correo = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioporCorreo(correo);

        ProductoEntity productoEncontrado = productoservice.buscarPorId(id);

        model.addAttribute("producto", productoEncontrado);
        model.addAttribute("usuarioNombre", "Bienvenido "+usuarioEncontrado.getNombres());

        return "detalle_producto";
    }

    @GetMapping("/eliminar_producto/{id}")
    private String eliminarProducto(@PathVariable("id") Integer id, Model model) {
        ProductoEntity productoEliminado = productoservice.buscarPorId(id);
        productoservice.eliminarProducto(id);
        
        model.addAttribute("mensaje", "Se eliminó el producto con código " + productoEliminado.getId_Producto());

        return "redirect:/menu";
    }

    @GetMapping("/generar_pdf")
    private ResponseEntity<InputStreamResource> generarPdf(HttpSession session, Model model) throws IOException {

        String correo = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioporCorreo(correo);

        Map<String, Object> datosPdf = new HashMap<>();
        datosPdf.put("productoPdf", productosRepository.findAll());
        datosPdf.put("usuarioNombre", "Hecho por "+usuarioEncontrado.getNombres());

        ByteArrayInputStream pdfBytes = pdfService.generarPdfdeHtml("template_pdf", datosPdf);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=productos.pdf");

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfBytes));
    }
}
