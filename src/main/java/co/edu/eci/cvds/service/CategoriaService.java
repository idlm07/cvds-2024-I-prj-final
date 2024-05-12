package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Categoria;

import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.repository.CategoriaRepository;
import co.edu.eci.cvds.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository, ProductoRepository productoRepository){
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Metodo que guarda una categoria en la base de datos
     * @param categoria registratada
     */
    public void agregarCategoria(Categoria categoria){
        categoriaRepository.save(categoria);
    }

    /**
     * Metodo que indica las categorias en la base de datos
     * @return
     */
    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

    /**
     * Conjunto de productos pertenecientes a una categoria
     * @param categoria, categoria de la cual que desea conocer los productos
     * @return Conjunto de productos pertenecientes a dicha categoria
     */
    public Set<Producto> listarProductos(String categoria){
        Categoria currentCategoria= categoriaRepository.findByNombre(categoria).get(0);
        return currentCategoria.getProductosCategoria();
    }

    /**
     * Metodo que asocia una categoria con un producto
     * @param categoria, categoria a la que pertenece el producto
     * @param producto, producto que se desea asociar
     */
    public void asociarProducto(Categoria categoria, Producto producto){
        producto.agregarCategoria(categoria);
        categoriaRepository.save(categoria);
        productoRepository.save(producto);
    }




}
