package co.edu.eci.cvds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.eci.cvds.model.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {

    Modelo findByIdModelo(int id);

   
}
