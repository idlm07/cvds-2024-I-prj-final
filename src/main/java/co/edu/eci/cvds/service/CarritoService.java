package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Carrito;
import co.edu.eci.cvds.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarritoService {

    private Map<Integer, Carrito> carritos = new HashMap<>();
    private int currentId = 1;

    public Carrito saveCarrito(Carrito carrito) {
        if (carrito.getId() == null) {
            carrito.setId(currentId++);
        }
        carritos.put(carrito.getId(), carrito);
        return carrito;
    }

    public Carrito getCarritoById(Integer id) {
        return carritos.get(id);
    }

    public List<Carrito> getAllCarritos() {
        return new ArrayList<>(carritos.values());
    }

    public void deleteCarrito(Integer id) {
        carritos.remove(id);
    }

    public void addProductoToCarrito(Carrito carrito, Carrito producto) {
        carrito.getProductos().add(producto);
        carritos.put(carrito.getId(), carrito);  // Update the carrito in the map
    }

    public void removeProductoFromCarrito(Carrito carrito, Integer productoId) {
        carrito.getProductos().removeIf(producto -> producto.getId().equals(productoId));
        carritos.put(carrito.getId(), carrito);  // Update the carrito in the map
    }

    public void finalizarCompra(Carrito carrito) {
        // Logic to finalize the purchase, such as updating stock, processing payment, etc.
        // For now, we'll just clear the carrito
        carrito.getProductos().clear();
        carritos.put(carrito.getId(), carrito);  // Update the carrito in the map
    }

    public void addProductoToCarrito(Carrito carrito, Producto producto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addProductoToCarrito'");
    }
}
