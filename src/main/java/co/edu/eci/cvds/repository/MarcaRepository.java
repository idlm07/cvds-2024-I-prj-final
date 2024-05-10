package co.edu.eci.cvds.repository;

import co.edu.eci.cvds.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, String> {
    public List<Marca> findByMarca(String marca);

}