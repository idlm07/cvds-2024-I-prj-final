package co.edu.eci.cvds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.eci.cvds.model.Modelo;
import co.edu.eci.cvds.repository.ModeloRepository;

import java.util.List;

@RestController
@RequestMapping("/modelos")
public class ModeloController {

    @Autowired
    private ModeloRepository modeloRepository;

    // Endpoint para obtener todos los modelos
    @GetMapping("/")
    public ResponseEntity<List<Modelo>> getAllModelos() {
        List<Modelo> modelos = modeloRepository.findAll();
        return new ResponseEntity<>(modelos, HttpStatus.OK);
    }

    // Endpoint para obtener un modelo por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Modelo> getModeloById(@PathVariable int id) {
        Modelo modelo = modeloRepository.findByIdModelo(id);
        if (modelo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(modelo, HttpStatus.OK);
    }

    // Endpoint para crear un nuevo modelo
    @PostMapping("/")
    public ResponseEntity<Modelo> createModelo(@RequestBody Modelo modelo) {
        Modelo nuevoModelo = modeloRepository.save(modelo);
        return new ResponseEntity<>(nuevoModelo, HttpStatus.CREATED);
    }

    // Endpoint para actualizar los datos de un modelo existente
    @PutMapping("/{id}")
    public ResponseEntity<Modelo> updateModelo(@PathVariable int id, @RequestBody Modelo modeloDetails) {
        Modelo modelo = modeloRepository.findByIdModelo(id);
        if (modelo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        modelo.setCilindraje(modeloDetails.getCilindraje());
        modelo.setAño(modeloDetails.getAño());
        Modelo modeloActualizado = modeloRepository.save(modelo);
        return new ResponseEntity<>(modeloActualizado, HttpStatus.OK);
    }

    // Endpoint para eliminar un modelo por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModelo(@PathVariable int id) {
        Modelo modelo = modeloRepository.findByIdModelo(id);
        if (modelo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        modeloRepository.delete(modelo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
