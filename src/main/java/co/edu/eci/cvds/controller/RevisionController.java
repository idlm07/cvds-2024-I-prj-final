package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.model.Revision;
import co.edu.eci.cvds.service.RevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/revisiones")
public class RevisionController {

    @Autowired
    private RevisionService revisionService;

    @PostMapping
    public ResponseEntity<Revision> createRevision(@RequestBody Revision revision) {
        return ResponseEntity.ok(revisionService.saveRevision(revision));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revision> getRevisionById(@PathVariable Integer id) {
        Revision revision = revisionService.getRevisionById(id);
        if (revision != null) {
            return ResponseEntity.ok(revision);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Revision>> getAllRevisiones() {
        return ResponseEntity.ok(revisionService.getAllRevisiones());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevision(@PathVariable Integer id) {
        revisionService.deleteRevision(id);
        return ResponseEntity.noContent().build();
    }
}
