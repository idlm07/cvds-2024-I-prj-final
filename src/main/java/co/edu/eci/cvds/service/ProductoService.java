package co.edu.eci.cvds.service;

import co.edu.eci.cvds.Exception.LincolnLinesException;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

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
     * @return producto registrado
     * @throws LincolnLinesException VALOR_NEGATIVO si el producto ingresado tiene algun valor (valor, impuesto o descuento) negativo
     */
    public Producto agregarProducto(Producto producto) throws LincolnLinesException {
            if(producto.getValor() < 0 || producto.getImpuesto() < 0 || producto.getDescuento() < 0)  throw new LincolnLinesException(LincolnLinesException.VALOR_NEGATIVO);
            return productoRepository.save(producto);

    }

    /**
     * Funcion que devuelve un producto en especifico registrado en la base de datos
     * @param nombre, nombre del producto
     * @return producto si se encuentra en la base de datos, null sino
     */
    public Producto buscarProductoPorNombre(String nombre) {
        if(!productoRepository.findByNombre(nombre).isEmpty()) return productoRepository.findByNombre(nombre).get(0);
        else return null;
    }

    public List<Producto> buscarProductos() {
        return productoRepository.findAll();
    }

    /**
     * @param producto
     */
    public void actualizarProducto(Producto producto) throws LincolnLinesException {
        if(producto.getValor() < 0 || producto.getImpuesto() < 0 || producto.getDescuento() < 0) throw new LincolnLinesException(LincolnLinesException.VALOR_NEGATIVO);
        Producto productoExistente = this.buscarProductoPorNombre(producto.getNombre());
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setDescripcionBreve(producto.getDescripcionBreve());
        productoExistente.setDescripcionTecnica(producto.getDescripcionTecnica());
        productoExistente.setCategoria(producto.getCategoria());
        productoExistente.setImagen(producto.getImagen());
        productoExistente.setValor(producto.getValor());
        productoExistente.setMoneda(producto.getMoneda());
        productoExistente.setImpuesto(producto.getImpuesto());
        productoExistente.setDescuento(producto.getDescuento());
        productoRepository.save(producto);

    }

    /**
     * Metodo que borra producto de la base de datos
     * @param nombre, nombre del producto a borrar.
     */
    public void borrarProducto(String nombre) {
        productoRepository.deleteById(nombre);
    }



}
