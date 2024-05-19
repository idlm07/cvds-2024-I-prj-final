package co.edu.eci.cvds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.eci.cvds.model.Carrito;
import co.edu.eci.cvds.repository.CarritoRepository;

import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @Autowired
    private CarritoRepository carritoRepository;

    // Endpoint para obtener todos los carritos
    @GetMapping("/")
    public ResponseEntity<List<Carrito>> getAllCarritos() {
        List<Carrito> carritos = carritoRepository.findAll();
        return new ResponseEntity<>(carritos, HttpStatus.OK);
    }

    // Endpoint para obtener un carrito por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Carrito> getCarritoById(@PathVariable String id) {
        Carrito carrito = carritoRepository.findByIdCarrito(id);
        if (carrito == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carrito, HttpStatus.OK);
    }

    // Endpoint para crear un nuevo carrito
    @PostMapping("/")
    public ResponseEntity<Carrito> createCarrito(@RequestBody Carrito carrito) {
        Carrito nuevoCarrito = carritoRepository.save(carrito);
        return new ResponseEntity<>(nuevoCarrito, HttpStatus.CREATED);
    }

    // Endpoint para actualizar los datos de un carrito existente
    @PutMapping("/{id}")
    public ResponseEntity<Carrito> updateCarrito(@PathVariable String id, @RequestBody Carrito carritoDetails) {
        Carrito carrito = carritoRepository.findByIdCarrito(id);
        if (carrito == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        carrito.setFechaPedido(carritoDetails.getFechaPedido());
        carrito.setEstado(carritoDetails.getEstado());
        carrito.setTotal(carritoDetails.getTotal());
        Carrito carritoActualizado = carritoRepository.save(carrito);
        return new ResponseEntity<>(carritoActualizado, HttpStatus.OK);
    }

    // Endpoint para eliminar un carrito por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable String id) {
        Carrito carrito = carritoRepository.findByIdCarrito(id);
        if (carrito == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        carritoRepository.delete(carrito);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
