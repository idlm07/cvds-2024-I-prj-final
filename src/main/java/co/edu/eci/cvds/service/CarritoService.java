package co.edu.eci.cvds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.eci.cvds.repository.CarritoRepository;
@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;

    public void agregarProducto(String productoId) {
        carritoRepository.agregarProducto(productoId);
    }

    public void quitarProducto(String productoId) {
        carritoRepository.quitarProducto(productoId);
    }

    public float calcularSubtotal() {
        return carritoRepository.calcularSubtotal();
    }
}
