package co.edu.eci.cvds.repository;

import co.edu.eci.cvds.model.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {
    public List<Auto> findByid(Integer id);

}