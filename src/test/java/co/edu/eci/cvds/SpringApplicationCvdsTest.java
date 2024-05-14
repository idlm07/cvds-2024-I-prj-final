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
        assertEquals("SUZUKI",vehiculoService.getVehiculo("Suzuki","swfit").getMarca());
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
        assertEquals(1, casco.getCotizaciones().size());
        assertTrue(casco.getCotizaciones().contains(cotizacion));
        assertTrue(productoService.vehiculos("casco").contains(vehiculo));
        assertTrue(vehiculoService.listarCotizaciones(vehiculo.getMarca(),vehiculo.getModel()).contains(cotizacion));
    }

    @Test
    void shouldNotAgregarCarrito(){
        //Limpieza de tablas
        productoService.limpiarTabla();
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        try{
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica","Computador");
        }catch (LincolnLinesException e){
            fail(e.getMessage());
        }

        try {
            cotizacionService.agregarAlCarrito("casco","volkswagen","gol",-1);
           fail("No lanzo excepcion");
        } catch (LincolnLinesException e) {
            assertEquals(LincolnLinesException.PRODUCTO_NO_COMPATIBLE, e.getMessage());
        }

        try {
            cotizacionService.agregarAlCarrito("casco",null,"gol",-1);
            fail("No lanzo excepcion");
        } catch (LincolnLinesException e) {
            assertEquals(LincolnLinesException.DATOS_FALTANTES, e.getMessage());
        }

        try {
            cotizacionService.agregarAlCarrito("casco","vOLKSWAGEN",null,-1);
            fail("No lanzo excepcion");
        } catch (LincolnLinesException e) {
            assertEquals(LincolnLinesException.DATOS_FALTANTES, e.getMessage());
        }
    }

    @Test
    void shouldQuitarDelCarrito(){
        //Limpieza de tablas
        productoService.limpiarTabla();
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        //Codigo
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
            cotizacionService.quitarDelCarrito("casco",cotizacion.getIden());
            cotizacionService.quitarDelCarrito("casco",cotizacion.getIden());
            cotizacionService.quitarDelCarrito("comPutador",cotizacion.getIden());
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        Producto computador = productoService.buscarProductoPorNombre("ComputaDor");
        Producto casco = productoService.buscarProductoPorNombre("casco");
        assertTrue(cotizacionService.verCarrito(cotizacion.getIden()).isEmpty());
        assertTrue(casco.getCotizaciones().isEmpty());
        assertTrue(computador.getCotizaciones().isEmpty());
    }


    @Test
    void shouldCalcularTotalCarrito(){
        //Limpiar BD
        productoService.limpiarTabla();
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        //Codigo
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
        float calculado = cotizacionService.calcularTotalCarrito(cotizacion.getIden());
        float tasaError = Math.abs(8373740519f-calculado)/8373740519f;
        assertTrue(tasaError <= 0.05);
    }

    @Test
    void shouldCalcularTotal(){
        //Limpiar BD
        productoService.limpiarTabla();
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Producto producto = new Producto();
        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
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
        float calculado = cotizacionService.calcularFinal(cotizacion.getIden());
        float verdadero = 8373740519f - 6709090009.60f + 536727200.77f + 353415.79f;
        float tasaError = Math.abs(verdadero-calculado)/verdadero;
        assertTrue(tasaError <= 0.05);
    }

    @Test
    void shouldAgendarCita(){
        //Limpiar BD
        productoService.limpiarTabla();
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion2 = new Cotizacion();
        Cotizacion cotizacion3 = new Cotizacion();
        Producto producto = new Producto();
        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
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

            cotizacion2 = cotizacionService.agregarAlCarrito("casco","volkswagen","gol",-1);
            cotizacionService.agregarAlCarrito("casco","volkswagen",null,cotizacion2.getIden());
            cotizacionService.agregarAlCarrito("computador",null,"gol",cotizacion2.getIden());

            cotizacionService.agregarAlCarrito("casco","volkswagen","gol",-1);

            cotizacionService.agendarCita(LocalDateTime.of(2045,10,12,9,0),"Bogota","Calle 154 #8-29",cotizacion.getIden(),"Camilo","CaStaño");
            cotizacionService.agendarCita(LocalDateTime.of(2045,10,12,11,35),"Bogota","Calle 154 #8-29",cotizacion2.getIden(),"Camilo","CaStaño");
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        assertEquals(3,cotizacionService.listarCotizaciones().size());
        assertEquals(2,cotizacionService.cotizacionesAgendadas().size());
        assertTrue(cotizacionService.listarCotizaciones().contains(cotizacion));
        assertTrue(cotizacionService.listarCotizaciones().contains(cotizacion2));



    }











}

