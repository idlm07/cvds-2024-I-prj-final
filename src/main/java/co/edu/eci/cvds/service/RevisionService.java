package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Revision;
import co.edu.eci.cvds.repository.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevisionService {

    @Autowired
    private RevisionRepository revisionRepository;

    public Revision saveRevision(Revision revision) {
        return revisionRepository.save(revision);
    }

    public Revision getRevisionById(Integer id) {
        return revisionRepository.findById(id).orElse(null);
    }

    public List<Revision> getAllRevisiones() {
        return revisionRepository.findAll();
    }

    public void deleteRevision(Integer id) {
        revisionRepository.deleteById(id);
    }
}
