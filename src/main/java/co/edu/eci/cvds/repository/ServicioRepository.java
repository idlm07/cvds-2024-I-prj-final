package co.edu.eci.cvds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.eci.cvds.model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

  
   
}
