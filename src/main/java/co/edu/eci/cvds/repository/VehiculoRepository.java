package co.edu.eci.cvds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.eci.cvds.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    Vehiculo findByMarca(String marca);


}
