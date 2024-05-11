package co.edu.eci.cvds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.repository.ProductoRepository;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Endpoint para obtener todos los productos
    @GetMapping("/")
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoRepository.findAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Endpoint para obtener un producto por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer id) {
        Producto producto = productoRepository.findByIdProducto(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    // Endpoint para crear un nuevo producto
    @PostMapping("/")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoRepository.save(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // Endpoint para actualizar los datos de un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Integer id, @RequestBody Producto productoDetails) {
        Producto producto = productoRepository.findByIdProducto(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        producto.setMarca(productoDetails.getMarca());
        producto.setDescripcion(productoDetails.getDescripcion());
        producto.setPrecio(productoDetails.getPrecio());
        Producto productoActualizado = productoRepository.save(producto);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    // Endpoint para eliminar un producto por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        Producto producto = productoRepository.findByIdProducto(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productoRepository.delete(producto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
