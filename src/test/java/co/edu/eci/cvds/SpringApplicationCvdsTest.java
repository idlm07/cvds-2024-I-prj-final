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
    private CotizacionService cotizacionService;

    @Autowired
    private ClienteService clienteService;

    @Test
    void shouldAgregarProducto() {
        productoService.limpiarTabla();
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        Producto producto1 = new Producto();
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        try {
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            producto1 = new Producto("celular",4800000f,"COP");
            producto1.setDescripcionBreve("Prueba de agregacion");
            producto1.setDescuento(0.25f);
            productoService.agregarProducto(producto1);
            categoriaService.agregarProducto("Electronica","Computador");
            categoriaService.agregarProducto("Electronica","CelUlar");
            categoriaService.agregarProducto("Mecanica","Casco");
            categoriaService.agregarProducto("Sports","Casco");
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        Producto producto2 = productoService.buscarProductoPorNombre("ComputaDor");
        Producto producto3 = productoService.buscarProductoPorNombre("casco");
        Categoria electronica = categoriaService.buscarCategoria("electronica");
        Categoria sports = categoriaService.buscarCategoria("sports");
        Categoria mecanica = categoriaService.buscarCategoria("mecanica");
        assertEquals(2, categoriaService.listaProductos("Electronica").size());
        assertEquals(1, categoriaService.listaProductos("Mecanica").size());
        assertEquals(1, categoriaService.listaProductos("Sports").size());
        assertTrue(categoriaService.listaProductos("Electronica").contains(producto1));
        assertTrue(categoriaService.listaProductos("Mecanica").contains(producto3));
        assertTrue(categoriaService.listaProductos("Sports").contains(producto3));
        assertTrue(categoriaService.listaProductos("Electronica").contains(producto2));
        assertTrue(productoService.conocerCategorias("celular").contains(electronica));
        assertTrue(productoService.conocerCategorias("CascO").contains(mecanica));
        assertTrue(productoService.conocerCategorias("casco").contains(sports));
        assertTrue(productoService.conocerCategorias("computador").contains(electronica));
        assertTrue(productoService.buscarProductos().contains(producto2));
        assertTrue(productoService.buscarProductos().contains(producto3));
        assertTrue(productoService.buscarProductos().contains(producto1));
        assertTrue(categoriaService.listarCategorias().contains(electronica));
        assertTrue(categoriaService.listarCategorias().contains(mecanica));
        assertTrue(categoriaService.listarCategorias().contains(sports));
    }


    @Test
    void shouldAgregarVehiculo(){
        productoService.limpiarTabla();
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        vehiculoService.agregarVehiculo(new Vehiculo("Suzuki","Swfit","2012"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        assertEquals(2,vehiculoService.getVehiculos().size());
        assertEquals("Suzuki",vehiculoService.getVehiculo("Suzuki","swfit").getMarca());
    }

    @Test
    void shouldAsociarProductoVehiculo(){
        productoService.limpiarTabla();
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Supra","2001"));
        try {
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica","Computador");
            categoriaService.agregarProducto("Electronica","CelUlar");
            categoriaService.agregarProducto("Mecanica","Casco");
            categoriaService.agregarProducto("Sports","Casco");
            vehiculoService.agregarProducto("volkswagen","gOL","computador");
            vehiculoService.agregarProducto("volkswagen","gOL","computador");
            vehiculoService.agregarProducto("volkswagen","gol","casco");
            vehiculoService.agregarProducto("volkswagen","supra","casco");
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        Vehiculo vehiculo = vehiculoService.getVehiculo("volkswagen","gol");
        Vehiculo vehiculo1 = vehiculoService.getVehiculo("volkswagen","supra");
        Producto computador = productoService.buscarProductoPorNombre("ComputaDor");
        Producto casco = productoService.buscarProductoPorNombre("casco");
        assertEquals(2,vehiculoService.listraProductosOptimos("volkswagen","gol").size());
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(computador));
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(casco));
        assertTrue(productoService.vehiculos("casco").contains(vehiculo));
        assertTrue(productoService.vehiculos(computador.getNombre()).contains(vehiculo));
        assertTrue(productoService.vehiculos("casco").contains(vehiculo1));
        assertEquals(2,productoService.vehiculos("casco").size());
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","supra").contains(casco));
    }

    @Test
    void shouldAgregarACarrito(){
        productoService.limpiarTabla();
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        try {
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica","Computador");
            categoriaService.agregarProducto("Electronica","CelUlar");
            categoriaService.agregarProducto("Mecanica","Casco");
            categoriaService.agregarProducto("Sports","Casco");
            vehiculoService.agregarProducto("volkswagen","gOL","computador");
            vehiculoService.agregarProducto("volkswagen","gol","casco");
            cotizacion = cotizacionService.agregarAlCarrito("casco","volkswagen","gol",-1);
            cotizacionService.agregarAlCarrito("casco","volkswagen",null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito("computador",null,"gol",cotizacion.getIden());
            vehiculoService.agregarProducto("volkswagen","gol","computador");
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        Vehiculo vehiculo = vehiculoService.getVehiculo("volkswagen","gol");
        Producto computador = productoService.buscarProductoPorNombre("ComputaDor");
        Producto casco = productoService.buscarProductoPorNombre("casco");
        assertEquals(3,cotizacionService.verCarrito(cotizacion.getIden()).size());
        assertTrue(cotizacionService.verCarrito(cotizacion.getIden()).contains(casco));
        assertTrue(cotizacionService.verCarrito(cotizacion.getIden()).contains(computador));
        assertEquals(2,cotizacionService.contadorProducto("casco",cotizacion.getIden()));
        assertEquals(1,cotizacionService.contadorProducto("computador",cotizacion.getIden()));
        assertEquals(vehiculo,cotizacionService.getVehiculo(cotizacion.getIden()));
        assertEquals(2,vehiculoService.listraProductosOptimos("volkswagen","gol").size());
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(computador));
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(casco));
        assertTrue(productoService.vehiculos("casco").contains(vehiculo));
        assertTrue(productoService.vehiculos(computador.getNombre()).contains(vehiculo));
    }









}

