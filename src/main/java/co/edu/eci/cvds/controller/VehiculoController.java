package co.edu.eci.cvds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.VehiculoRepository;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    // Endpoint para obtener todos los vehículos
    @GetMapping("/")
    public ResponseEntity<List<Vehiculo>> getAllVehiculos() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        return new ResponseEntity<>(vehiculos, HttpStatus.OK);
    }

    // Endpoint para obtener un vehículo por su marca
    @GetMapping("/{marca}")
    public ResponseEntity<Vehiculo> getVehiculoByMarca(@PathVariable String marca) {
        Vehiculo vehiculo = vehiculoRepository.findByMarca(marca);
        if (vehiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehiculo, HttpStatus.OK);
    }

    // Endpoint para crear un nuevo vehículo
    @PostMapping("/")
    public ResponseEntity<Vehiculo> createVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo nuevoVehiculo = vehiculoRepository.save(vehiculo);
        return new ResponseEntity<>(nuevoVehiculo, HttpStatus.CREATED);
    }

    // Endpoint para actualizar los datos de un vehículo existente
    @PutMapping("/{marca}")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable String marca, @RequestBody Vehiculo vehiculoDetails) {
        Vehiculo vehiculo = vehiculoRepository.findByMarca(marca);
        if (vehiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        vehiculo.setIdModelo(vehiculoDetails.getIdModelo());
        vehiculo.setIdCliente(vehiculoDetails.getIdCliente());
        Vehiculo vehiculoActualizado = vehiculoRepository.save(vehiculo);
        return new ResponseEntity<>(vehiculoActualizado, HttpStatus.OK);
    }

    // Endpoint para eliminar un vehículo por su marca
    @DeleteMapping("/{marca}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable String marca) {
        Vehiculo vehiculo = vehiculoRepository.findByMarca(marca);
        if (vehiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        vehiculoRepository.delete(vehiculo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
