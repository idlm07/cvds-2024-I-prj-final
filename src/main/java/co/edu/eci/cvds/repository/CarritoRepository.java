package co.edu.eci.cvds.repository;
import java.util.Map;

import org.springframework.stereotype.Repository;

import co.edu.eci.cvds.model.CarritoDeCompras;
@Repository
public class CarritoRepository {
    private Map<String, Integer> productosEnCarrito;
    private CarritoDeCompras carritoDeCompras;

    public CarritoRepository() {
        this.carritoDeCompras = new CarritoDeCompras(); 
    }

    public void agregarProducto(String productoId) {
        if (productosEnCarrito.containsKey(productoId)) {
            productosEnCarrito.put(productoId, productosEnCarrito.get(productoId) + 1); // Incrementa la cantidad
        } else {
            productosEnCarrito.put(productoId, 1); // Agrega el producto al carrito con cantidad 1
        }
    }

    public void quitarProducto(String productoId) {
        if (productosEnCarrito.containsKey(productoId)) {
            int cantidad = productosEnCarrito.get(productoId);
            if (cantidad == 1) {
                productosEnCarrito.remove(productoId); // Si la cantidad es 1, elimina el producto del carrito
            } else {
                productosEnCarrito.put(productoId, cantidad - 1); // Si la cantidad es mayor a 1, disminuye la cantidad
            }
        }
    }

    public float calcularSubtotal() {
        return carritoDeCompras.calcularSubtotal();
    }
}
