package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.model.Modelo;
import co.edu.eci.cvds.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @PostMapping
    public ResponseEntity<Modelo> createModelo(@RequestBody Modelo modelo) {
        return ResponseEntity.ok(modeloService.saveModelo(modelo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modelo> getModeloById(@PathVariable Integer id) {
        Modelo modelo = modeloService.getModeloById(id);
        if (modelo != null) {
            return ResponseEntity.ok(modelo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Modelo>> getAllModelos() {
        return ResponseEntity.ok(modeloService.getAllModelos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModelo(@PathVariable Integer id) {
        modeloService.deleteModelo(id);
        return ResponseEntity.noContent().build();
    }
}
