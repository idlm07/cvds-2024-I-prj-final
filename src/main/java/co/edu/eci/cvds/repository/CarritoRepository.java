package co.edu.eci.cvds.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.eci.cvds.model.Carrito;


@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

   

}
