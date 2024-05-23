package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Carrito;
import co.edu.eci.cvds.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    public Carrito saveCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public Carrito getCarritoById(Integer id) {
        return carritoRepository.findById(id).orElse(null);
    }

    public List<Carrito> getAllCarritos() {
        return carritoRepository.findAll();
    }

    public void deleteCarrito(Integer id) {
        carritoRepository.deleteById(id);
    }
}
