package co.edu.eci.cvds.controller;


import co.edu.eci.cvds.model.Categoria;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.service.CategoriaService;
import co.edu.eci.cvds.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/LincolnLines/privado")
public class PrivadoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    @Autowired
    public PrivadoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/landPage")
    public String landPage(Model model, @RequestParam(value = "mensaje",required = false)String mensaje){
        List<Producto> productos = productoService.buscarProductos();
        List<Integer> paginas  = new ArrayList<>();
        int contador = 1;
        for(int i = 0; i<productos.size(); i++){
            if(i%3==0){
                paginas.add(contador);
                contador++;
            }
        }
        model.addAttribute("productos", productos);
        model.addAttribute("paginas", paginas);
        model.addAttribute("mensaje", mensaje);
        return "/privado/landpage";
    }

    @GetMapping("/actualizarProducto")
    public String actualizarProducto(Model model,@RequestParam("nombre") String nombre){
        Producto producto =productoService.buscarProductoPorNombre(nombre);
        List<Categoria> categorias = categoriaService.listarCategorias();
        model.addAttribute("producto",producto);
        model.addAttribute("categorias", categorias);
        return "/privado/actualizarProducto";
    }



}
