package co.edu.eci.cvds.service;


import co.edu.eci.cvds.exception.LincolnLinesException;

import co.edu.eci.cvds.model.Categoria;
import co.edu.eci.cvds.model.Producto;


import co.edu.eci.cvds.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;



/**
 * Clase Service de Producto
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;


    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;

    }

    /**
     * Funcion que registra un producto a la base de datos.
     * @param producto, producto a registrar
     *
     */
    public void agregarProducto(Producto producto){
        productoRepository.save(producto);
    }

    /**
     * Funcion que devuelve un producto en especifico registrado en la base de datos
     * @param nombre, nombre del producto
     * @return producto si se encuentra en la base de datos, null sino
     */
    public Producto buscarProductoPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre.toUpperCase()).get(0);

    }

    /**
     * Funcion que devuelve lista de productos
     * @return lista de productos registrada en la base de datos
     */
    public List<Producto> buscarProductos() {
        return productoRepository.findAll();
    }

    /**
     * Metodo que actualiza el producto especificado
     * @param producto con los nuevos valores
     */
    public void actualizarProducto(Producto producto) throws LincolnLinesException {
        Producto productoExistente = this.buscarProductoPorNombre(producto.getNombre());
        productoExistente.setValor(producto.getValor());
        productoExistente.setImpuesto(producto.getImpuesto());
        productoExistente.setDescuento(producto.getDescuento());
        productoExistente.setDescripcionBreve(producto.getDescripcionBreve());
        productoExistente.setDescripcionTecnica(producto.getDescripcionTecnica());
        productoExistente.setImagen(producto.getImagen());
        productoExistente.setMoneda(producto.getMoneda());
        productoRepository.save(producto);

    }

    /**
     * Metodo que borra producto de la base de datos
     * @param nombre, nombre del producto a borrar.
     */
    public void borrarProducto(String nombre) {
        Producto productoExistente = this.buscarProductoPorNombre(nombre);
        productoExistente.eliminarProducto();
        productoRepository.delete(productoExistente);
    }

    /**
     * Conocer las categorias a las que pertenece el productp
     * @param nombreProducto
     * @return
     */
    public List<String> conocerCategorias(String nombreProducto){
        Producto producto = this.buscarProductoPorNombre(nombreProducto);
        ArrayList<String> categorias = new ArrayList<>();
        for(Categoria categoria:producto.getCategorias()){
            categorias.add(categoria.getNombre());
        }
        return categorias;
    }

    public void limpiarTabla(){
        productoRepository.deleteAllInBatch();
    }










}