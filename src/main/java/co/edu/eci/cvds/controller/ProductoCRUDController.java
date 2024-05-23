package co.edu.eci.cvds.controller;


import co.edu.eci.cvds.exception.LincolnLinesException;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.service.CategoriaService;
import co.edu.eci.cvds.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;





@Controller
@RequestMapping("/CRUD/producto")
public class ProductoCRUDController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    @Autowired
    public ProductoCRUDController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @PostMapping("/actualizar")
    public String actualizarProducto(@RequestParam("nombreActualizar") String nombre,
                                     @RequestParam("descripcionBActualizar") String descripcionB,
                                     @RequestParam("descripcionTActualizar") String descripcionT,
                                     @RequestParam("imagenActualizar") String imagen,
                                     @RequestParam("monedaActualizar") String moneda,
                                     @RequestParam("valorActualizar") String valor,
                                     @RequestParam("impuestoActualizar") String impuesto,
                                     @RequestParam("descuentoActualizar") String descuento,
                                     @RequestParam("categoriaActualizar") String categoria){
        String mensaje;
        try{
            Producto productoNuevo = new Producto(nombre, Float.parseFloat(valor), moneda, Float.parseFloat(impuesto), Float.parseFloat(descuento));
            productoNuevo.setImagen(imagen);
            productoNuevo.setDescripcionBreve(descripcionB);
            productoNuevo.setDescripcionTecnica(descripcionT);
            categoriaService.agregarProducto(categoria, productoNuevo);
            productoService.actualizarProducto(productoNuevo);
            mensaje = "Producto creada con exito";
        }catch (LincolnLinesException e){
            mensaje = e.getMessage();
        }
        return "redirect:/LincolnLines/privado/landPage?mensaje=" + mensaje;
    }


    @PostMapping("/agregar")
    public String agregarProducto(@RequestParam("nombre") String nombre,
                                     @RequestParam("descripcionB") String descripcionB,
                                     @RequestParam("descripcionT") String descripcionT,
                                     @RequestParam("imagen") String imagen,
                                     @RequestParam("moneda") String moneda,
                                     @RequestParam("valor") int valor,
                                     @RequestParam("impuesto") float impuesto,
                                     @RequestParam("descuento") float descuento,
                                     @RequestParam("categoria") String categoria){
        String mensaje;
        try{
            Producto productoNuevo = new Producto(nombre, valor, moneda);
            productoNuevo.setImagen(imagen);
            productoNuevo.setDescripcionBreve(descripcionB);
            productoNuevo.setDescripcionTecnica(descripcionT);
            productoNuevo.setImpuesto(impuesto);
            productoNuevo.setDescuento(descuento);
            categoriaService.agregarProducto(categoria, productoNuevo);
            productoService.actualizarProducto(productoNuevo);
            mensaje = "Producto creada con exito";
        }catch (LincolnLinesException e){
            mensaje = e.getMessage();
        }
        return "redirect:/LincolnLines/privado/landPage?mensaje="+mensaje;
    }

    @GetMapping("/borrar")
    public String borrarProducto(@RequestParam("nombre") String nombre){
        productoService.borrarProducto(nombre);
        return "redirect:/LincolnLines/privado/landPage?mensaje=Producto " +nombre + " borrado con exito";
    }

}
