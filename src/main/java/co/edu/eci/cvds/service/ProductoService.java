package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto addConfiguration(Producto Producto) {
        return productoRepository.save(Producto);
    }

    public Producto getConfiguration(String producto) {
        return productoRepository.findByproducto(producto).get(1);
    }

    public List<Producto> getAllProducto() {
        return productoRepository.findAll();
    }

    public Producto updateProducto(Producto producto) {
        if (productoRepository.findByproducto(producto.getProducto()).size() == 0) {
            return productoRepository.save(producto);
        }
        return null;
    }

    public void deleteProducto(String Producto) {
        productoRepository.deleteById(Producto);
    }
}
