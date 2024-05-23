package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.model.Carrito;
import co.edu.eci.cvds.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping
    public ResponseEntity<Carrito> createCarrito(@RequestBody Carrito carrito) {
        return ResponseEntity.ok(carritoService.saveCarrito(carrito));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> getCarritoById(@PathVariable Integer id) {
        Carrito carrito = carritoService.getCarritoById(id);
        if (carrito != null) {
            return ResponseEntity.ok(carrito);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Carrito>> getAllCarritos() {
        return ResponseEntity.ok(carritoService.getAllCarritos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Integer id) {
        carritoService.deleteCarrito(id);
        return ResponseEntity.noContent().build();
    }
}
