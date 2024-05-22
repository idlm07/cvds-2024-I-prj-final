package co.edu.eci.cvds.controller;


import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/LincolnLines/privado")
public class PrivadoController {

    private final ProductoService productoService;

    @Autowired
    public PrivadoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/landPage")
    public String landPage(Model model){
        List<Producto> productos = productoService.buscarProductos();
        model.addAttribute("productos", productos);
        return "/privado/landpage";
    }

}
