package co.edu.eci.cvds.service;

import co.edu.eci.cvds.SpringApplicationCvds;
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
     * Indica la informacion acerca una categoria en especifico
     * @param nombre, nombre de la categoria
     * @return, categoria en la BD
     */
    public Categoria buscarCategoria(String nombre){
        return categoriaRepository.findByNombre(SpringApplicationCvds.stringStandar(nombre)).get(0);
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
     * Agrega un producto al conjunto de productos
     * @param categoria, nombre de la categoria a la cual se desea agregar el producto
     * @param producto, nombre del producto que se desea agregar
     * @return categoria registrada den la base de datos
     */
    public Categoria agregarProducto(String categoria, String producto){
        Categoria categoriaEncontrada = categoriaRepository.findByNombre(SpringApplicationCvds.stringStandar(categoria)).get(0);
        Producto productoEncontrado = productoRepository.findByNombre(SpringApplicationCvds.stringStandar(producto)).get(0);
        categoriaEncontrada.agregarProducto(productoEncontrado);
        productoRepository.save(productoEncontrado);
        return categoriaRepository.save(categoriaEncontrada);
    }

    /**
     * Indica los productos pertenecientes a una categoria
     * @param categoria, categoria que se desea averiguar
     * @return conjunto de categorias.
     */

    public Set<Producto> listaProductos(String categoria){
        Categoria categoriaEncontrada = categoriaRepository.findByNombre(categoria).get(0);
        return categoriaEncontrada.getProductosCategoria();
    }

    public void limpiarTabla(){
        categoriaRepository.deleteAllInBatch();
    }





}