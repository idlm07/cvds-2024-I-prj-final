package co.edu.eci.cvds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.eci.cvds.model.Revision;
import co.edu.eci.cvds.repository.RevisionRepository;

import java.util.List;

@RestController
@RequestMapping("/revisiones")
public class RevisionController {

    @Autowired
    private RevisionRepository revisionRepository;

    // Endpoint para obtener todas las revisiones
    @GetMapping("/")
    public ResponseEntity<List<Revision>> getAllRevisiones() {
        List<Revision> revisiones = revisionRepository.findAll();
        return new ResponseEntity<>(revisiones, HttpStatus.OK);
    }

    // Endpoint para obtener una revisión por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Revision> getRevisionById(@PathVariable Integer id) {
        Revision revision = revisionRepository.findByIdRevision(id);
        if (revision == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(revision, HttpStatus.OK);
    }

    // Endpoint para crear una nueva revisión
    @PostMapping("/")
    public ResponseEntity<Revision> createRevision(@RequestBody Revision revision) {
        Revision nuevaRevision = revisionRepository.save(revision);
        return new ResponseEntity<>(nuevaRevision, HttpStatus.CREATED);
    }

    // Endpoint para actualizar los datos de una revisión existente
    @PutMapping("/{id}")
    public ResponseEntity<Revision> updateRevision(@PathVariable Integer id, @RequestBody Revision revisionDetails) {
        Revision revision = revisionRepository.findByIdRevision(id);
        if (revision == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        revision.setPrecio(revisionDetails.getPrecio());
        revision.setDescripcion(revisionDetails.getDescripcion());
        revision.setCarrito(revisionDetails.getCarrito());
        revision.setServicios(revisionDetails.getServicios());
        Revision revisionActualizada = revisionRepository.save(revision);
        return new ResponseEntity<>(revisionActualizada, HttpStatus.OK);
    }

    // Endpoint para eliminar una revisión por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevision(@PathVariable Integer id) {
        Revision revision = revisionRepository.findByIdRevision(id);
        if (revision == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        revisionRepository.delete(revision);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
