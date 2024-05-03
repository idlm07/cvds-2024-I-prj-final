package co.edu.eci.cvds.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import co.edu.eci.cvds.service.CarritoService;
@RestController
@RequestMapping("/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @PostMapping("/agregar/{productoId}")
    public void agregarProductoAlCarrito(@PathVariable("productoId") String productoId) {
        carritoService.agregarProducto(productoId);
    }

    @PostMapping("/quitar/{productoId}")
    public void quitarProductoDelCarrito(@PathVariable("productoId") String productoId) {
        carritoService.quitarProducto(productoId);
    }

    @GetMapping("/subtotal")
    public float obtenerSubtotal() {
        return carritoService.calcularSubtotal();
    }
}
