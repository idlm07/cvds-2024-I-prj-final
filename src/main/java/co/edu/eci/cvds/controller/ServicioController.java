package co.edu.eci.cvds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.eci.cvds.model.Servicio;
import co.edu.eci.cvds.repository.ServicioRepository;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    // Endpoint para obtener todos los servicios
    @GetMapping("/")
    public ResponseEntity<List<Servicio>> getAllServicios() {
        List<Servicio> servicios = servicioRepository.findAll();
        return new ResponseEntity<>(servicios, HttpStatus.OK);
    }

    // Endpoint para obtener un servicio por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Integer id) {
        Servicio servicio = servicioRepository.findByIdServicio(id);
        if (servicio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio, HttpStatus.OK);
    }

    // Endpoint para crear un nuevo servicio
    @PostMapping("/")
    public ResponseEntity<Servicio> createServicio(@RequestBody Servicio servicio) {
        Servicio nuevoServicio = servicioRepository.save(servicio);
        return new ResponseEntity<>(nuevoServicio, HttpStatus.CREATED);
    }

    // Endpoint para actualizar los datos de un servicio existente
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable Integer id, @RequestBody Servicio servicioDetails) {
        Servicio servicio = servicioRepository.findByIdServicio(id);
        if (servicio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        servicio.setDescripcion(servicioDetails.getDescripcion());
        servicio.setPrecio(servicioDetails.getPrecio());
        servicio.setFechaInicio(servicioDetails.getFechaInicio());
        servicio.setFechaFin(servicioDetails.getFechaFin());
        servicio.setCarrito(servicioDetails.getCarrito());
        servicio.setProductos(servicioDetails.getProductos());
        Servicio servicioActualizado = servicioRepository.save(servicio);
        return new ResponseEntity<>(servicioActualizado, HttpStatus.OK);
    }

    // Endpoint para eliminar un servicio por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Integer id) {
        Servicio servicio = servicioRepository.findByIdServicio(id);
        if (servicio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        servicioRepository.delete(servicio);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
