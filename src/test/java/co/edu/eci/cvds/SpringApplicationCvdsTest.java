package co.edu.eci.cvds;


import co.edu.eci.cvds.exception.LincolnLinesException;
import co.edu.eci.cvds.model.*;
import co.edu.eci.cvds.service.*;


import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SpringApplicationTests {

    @Autowired
    VehiculoService vehiculoService;

    @Autowired
    ProductoService productoService;

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    private EntityManager entityManager;


    @Test
    void shouldBeEquals(){
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Toyota", "Corolla", "2022");
        Producto producto = new Producto();
        Producto producto1 = new Producto();
        Producto producto2 = new Producto();
        Producto producto3 = new Producto();
        try {
            producto = new Producto("Producto1","Categoria",200f,"COP");
            producto1 = new Producto("Producto1","Categoria",200f,"COP");
            producto2 = new Producto("Producto2","Categoria",200f,"COP");
            producto3 = new Producto("Producto1","Categoria",200f,"COP");

            producto.setValor(200f);
            producto1.setValor(200f);
            producto3.setValor(200f);
            producto.setImpuesto(0.2f);
            producto1.setImpuesto(0.2f);
            producto3.setImpuesto(0.2f);
        } catch (LincolnLinesException e) {
            fail("Lanzo excepcion");
        }
        producto.setImagen("...");
        producto1.setImagen("...");
        producto3.setImagen("...");
        Cotizacion cotizacion = new Cotizacion(vehiculo);
        Cotizacion cotizacion1 = new Cotizacion(vehiculo);
        Cliente cliente = new Cliente("Camilo","Castano","3183074075",null);
        Cliente cliente1 = new Cliente("Camilo","Castano","3183074075",null);

        try {
            cotizacion.agendar(LocalDateTime.of(2025,12,31,15,0),null,null,cliente);
            cotizacion1.agendar(LocalDateTime.of(2025,12,31,15,0),null,null,cliente1);
        } catch (LincolnLinesException e) {
            fail("Lanzo excepcion");
        }
        vehiculo.anadirProducto(producto);
        vehiculo.anadirProducto(producto1);
        vehiculo2.anadirProducto(producto1);
        vehiculo.anadirProducto(producto2);
        vehiculo2.anadirProducto(producto2);
        assertEquals(vehiculo, vehiculo2);
        assertEquals(producto, producto1);
        assertEquals(producto1, producto3);
        assertEquals(cotizacion, cotizacion1);
        assertEquals(cliente, cliente1);
        assertEquals(2,vehiculo.getProductosVehiculo().size());
        assertTrue(vehiculo.getProductosVehiculo().contains(producto3));
        assertEquals(1,vehiculo.getCotizaciones().size());
        assertEquals(1,producto.getVehiculos().size());
        assertEquals(1,vehiculo.getCotizaciones().size());
       assertEquals(1,cliente1.getCotizaciones().size());
    }


    @Test
    void shouldLLenarTablas(){
        Vehiculo vehiculo = new Vehiculo("Suzuki","Swfit","2012");
        vehiculoService.agregarVehiculo(vehiculo);
        vehiculo = new Vehiculo("Volkswagen","Gol","2001");
        vehiculoService.agregarVehiculo(vehiculo);
        Categoria categoria = new Categoria("Electronica");
        Producto producto = null;
        try {
            producto = new Producto("Luces Led De Lujo Para Persiana Parrilla","Electronica",100000,"COP");
            producto.setDescuento(0.5f);
            producto.setImpuesto(0.2f);
            producto.setImagen("/img/luces1.png");
        } catch (LincolnLinesException e) {
            fail("Lanzo excepcion");
        }
        categoriaService.agregarCategoria(categoria);
        productoService.agregarProducto(producto);
        categoriaService.asociarProducto(categoria,producto);
        vehiculoService.agregarProducto("Volkswagen","Gol",producto);
        vehiculo = vehiculoService.agregarProducto("Volkswagen","Gol",producto);
        assertEquals(93,vehiculoService.getVehiculos().size());
        assertEquals(vehiculoService.getVehiculo("Volkswagen","Gol"),vehiculo);
        assertEquals(1,categoriaService.listarCategorias().size());
        assertTrue(categoriaService.listarProductos("Electronica").contains(producto));
    }


}





