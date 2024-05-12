package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Categoria;

import co.edu.eci.cvds.repository.CategoriaRepository;
import co.edu.eci.cvds.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository, ProductoRepository productoRepository){
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Metodo que guarda una categoria en la base de datos
     * @param categoria registratada
     */
    public void agregarCategoria(Categoria categoria){
        categoriaRepository.save(categoria);
    }


}
