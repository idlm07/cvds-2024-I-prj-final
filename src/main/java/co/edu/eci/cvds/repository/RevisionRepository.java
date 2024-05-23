package co.edu.eci.cvds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.eci.cvds.model.Revision;

@Repository
public interface RevisionRepository extends JpaRepository<Revision, Integer> {

   
}
