package co.edu.eci.cvds;


import co.edu.eci.cvds.exception.LincolnLinesException;
import co.edu.eci.cvds.model.*;
import co.edu.eci.cvds.service.*;



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
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();


        Producto producto1;
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
            producto1 = productoService.buscarProductoPorNombre("Computador");
            categoriaService.agregarProducto("Electronica",producto1);
            producto1 = productoService.buscarProductoPorNombre("CelUlar");
            categoriaService.agregarProducto("Electronica",producto1);
            producto1 = productoService.buscarProductoPorNombre("Casco");
            categoriaService.agregarProducto("Mecanica",producto1);
            categoriaService.agregarProducto("Sports",producto1);
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        Producto celular = productoService.buscarProductoPorNombre("Celular");
        Producto computaDor = productoService.buscarProductoPorNombre("ComputaDor");
        Producto casco = productoService.buscarProductoPorNombre("casco");
        Categoria electronica = categoriaService.buscarCategoria("electronica");
        Categoria sports = categoriaService.buscarCategoria("sports");
        Categoria mecanica = categoriaService.buscarCategoria("mecanica");
        assertEquals(2, categoriaService.listaProductos("Electronica").size());
        assertEquals(1, categoriaService.listaProductos("Mecanica").size());
        assertEquals(1, categoriaService.listaProductos("Sports").size());
        assertTrue(categoriaService.listaProductos("Electronica").contains(celular));
        assertTrue(categoriaService.listaProductos("Mecanica").contains(casco));
        assertTrue(categoriaService.listaProductos("Sports").contains(casco));
        assertTrue(categoriaService.listaProductos("Electronica").contains(computaDor));
        assertTrue(productoService.buscarProductos().contains(casco));
        assertTrue(productoService.buscarProductos().contains(celular));
        assertTrue(productoService.buscarProductos().contains(computaDor));
        assertTrue(categoriaService.listarCategorias().contains(electronica));
        assertTrue(categoriaService.listarCategorias().contains(mecanica));
        assertTrue(categoriaService.listarCategorias().contains(sports));
        assertEquals(3,productoService.buscarProductos().size());
        assertEquals(3,categoriaService.listarCategorias().size());
    }


    @Test
    void shouldAgregarVehiculo(){
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        vehiculoService.agregarVehiculo(new Vehiculo("Suzuki","Swfit","2012"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        assertEquals(2,vehiculoService.getVehiculos().size());
        assertEquals("SUZUKI",vehiculoService.getVehiculo("Suzuki","swfit").getMarca());
    }

    @Test
    void shouldAsociarProductoVehiculo(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();

        //Agregar Categorias
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));

        //Agregar Vehiculos
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Supra","2001"));
        Producto producto1;
        try {
            //Agregar productos
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            producto1 = new Producto("celular",4800000f,"COP");
            producto1.setDescripcionBreve("Prueba de agregacion");
            producto1.setDescuento(0.25f);
            productoService.agregarProducto(producto1);
            //Asociar productos con categorias
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica", productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports", productoService.buscarProductoPorNombre("Casco"));
            //Asociar Vehiculos con productos
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"),categoriaService.listarCategorias());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("Casco"),categoriaService.listarCategorias());
            vehiculoService.agregarProducto("volkswagen","supra",productoService.buscarProductoPorNombre("Casco"),categoriaService.listarCategorias());
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        Producto computador = productoService.buscarProductoPorNombre("ComputaDor");
        Producto casco = productoService.buscarProductoPorNombre("casco");
        assertEquals(2,vehiculoService.listraProductosOptimos("volkswagen","gol").size());
        assertEquals(1,vehiculoService.listraProductosOptimos("volkswagen","suprA").size());
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(computador));
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(casco));
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","supra").contains(casco));
    }

    @Test
    void shouldAgregarACarrito(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();

        //Agregar Categorias
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));

        //Agregar Vehiculos
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Supra","2001"));
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Agregar productos
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            //Asociar productos con categorias
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Mecanica", productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports", productoService.buscarProductoPorNombre("Casco"));
            //Asociar Vehiculos con productos
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"),categoriaService.listarCategorias());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("Casco"),categoriaService.listarCategorias());
            vehiculoService.agregarProducto("volkswagen","supra",productoService.buscarProductoPorNombre("Casco"),categoriaService.listarCategorias());
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("cascO"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("cascO"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("computador"),categoriaService.listarCategorias());
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
        assertEquals(2,cotizacionService.contadorProducto(productoService.buscarProductoPorNombre("casco"),cotizacion.getIden()));
        assertEquals(1,cotizacionService.contadorProducto(productoService.buscarProductoPorNombre("computador"),cotizacion.getIden()));
        assertEquals(vehiculo,cotizacionService.getVehiculo(cotizacion.getIden()));
        assertEquals(2,vehiculoService.listraProductosOptimos("volkswagen","gol").size());
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(computador));
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(casco));
        assertEquals(Cotizacion.EN_PROCESO,cotizacionService.conocerEstado(cotizacion.getIden()));
    }

    @Test
    void shouldNotAgregarCarrito(){
        //Limpieza de tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();

        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        try{
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
        }catch (LincolnLinesException e){
            fail(e.getMessage());
        }

        try {
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
           fail("No lanzo excepcion");
        } catch (LincolnLinesException e) {
            assertEquals(LincolnLinesException.PRODUCTO_NO_COMPATIBLE, e.getMessage());
        }

        try {
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,-1);
            fail("No lanzo excepcion");
        } catch (LincolnLinesException e) {
            assertEquals(LincolnLinesException.DATOS_FALTANTES, e.getMessage());
        }
    }

    @Test
    void shouldQuitarDelCarrito(){
        //Limpieza de tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
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
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"),categoriaService.listarCategorias());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"),categoriaService.listarCategorias());
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),null,cotizacion.getIden());
            cotizacionService.quitarDelCarrito(productoService.buscarProductoPorNombre("casco"),cotizacion.getIden());
            cotizacionService.quitarDelCarrito(productoService.buscarProductoPorNombre("casco"),cotizacion.getIden());
            cotizacionService.quitarDelCarrito(productoService.buscarProductoPorNombre("cOmpuTador"),cotizacion.getIden());
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        assertTrue(cotizacionService.verCarrito(cotizacion.getIden()).isEmpty());
        assertEquals(Cotizacion.ELIMINADO,cotizacionService.conocerEstado(cotizacion.getIden()));

    }


    @Test
    void shouldCalcularTotalCarrito(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();

        //Agregar Categorias
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));

        //Agregar Vehiculos
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Supra","2001"));
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Agregar productos
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            //Asociar productos con categorias
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Mecanica", productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports", productoService.buscarProductoPorNombre("Casco"));
            //Asociar Vehiculos con productos
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"),categoriaService.listarCategorias());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("Casco"),categoriaService.listarCategorias());
            vehiculoService.agregarProducto("volkswagen","supra",productoService.buscarProductoPorNombre("Casco"),categoriaService.listarCategorias());
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("cascO"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("cascO"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("computador"),categoriaService.listarCategorias());
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
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion1 = new Cotizacion();
        Producto producto;
        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"),categoriaService.listarCategorias());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"),categoriaService.listarCategorias());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("Celular"),categoriaService.listarCategorias());
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            cotizacion1 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("celular"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        float calculado = cotizacionService.calcularFinal(cotizacion.getIden());
        float verdadero = 8373740519f - 6709090009.60f + 536727200.77f + 353415.79f;
        float tasaError = Math.abs(verdadero-calculado)/verdadero;
        assertTrue(tasaError <= 0.05);
        assertEquals(4800000f,cotizacionService.calcularFinal(cotizacion1.getIden()));
        assertEquals(Cotizacion.FINALIZADO,cotizacionService.conocerEstado(cotizacion.getIden()));

    }

    @Test
    void shouldAgendarCita(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Producto producto;
        Cotizacion cotizacion2 = new Cotizacion();
        Cliente cliente;

        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"),categoriaService.listarCategorias());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"),categoriaService.listarCategorias());
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            cotizacion2 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            clienteService.agregarCliente(new Cliente("Camilo", "Castaño Quintanilla", "3183074075",null));
            cliente = clienteService.getCLiente("CamiLO","castaño Quintanilla");
            cotizacionService.agendarCita(LocalDateTime.of(2040,12,31,9,0),null,null,cotizacion.getIden(),cliente);
            cotizacionService.agendarCita(LocalDateTime.of(2040,12,31,11,35),"Bogota","Calle 156 #9-24",cotizacion2.getIden(),cliente);

        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        cotizacion2 = cotizacionService.encontrarCotizacion(cotizacion2.getIden());
        assertEquals(3,cotizacionService.listarCotizaciones().size());
        assertEquals(2,cotizacionService.cotizacionesAgendadas().size());
        assertTrue(cotizacionService.cotizacionesAgendadas().contains(cotizacion));
        assertTrue(cotizacionService.cotizacionesAgendadas().contains(cotizacion2));
        assertEquals("Calle 156 #9-24",cotizacionService.direccionRegocida(cotizacion2.getIden()));

    }


    @Test
    void shoulNodAgendarCita(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Producto producto;
        Cotizacion cotizacion2 = new Cotizacion();
        Cotizacion cotizacion3 = new Cotizacion();
        Cliente cliente = new Cliente();

        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"),categoriaService.listarCategorias());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"),categoriaService.listarCategorias());
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            cotizacion2 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacion3 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);

            clienteService.agregarCliente(new Cliente("Camilo", "Castaño Quintanilla", "3183074075",null));
            cliente = clienteService.getCLiente("CamiLO","castaño Quintanilla");
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        //Cita faltante
        try{
            cotizacionService.agendarCita(null,null,null,cotizacion.getIden(),cliente);
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.DATOS_FALTANTES,e.getMessage());
            assertTrue(cotizacionService.cotizacionesAgendadas().isEmpty());
        }
        //Cliente faltante
        try{
            cotizacionService.agendarCita(LocalDateTime.of(2050,12,31,10,30),null,null,cotizacion.getIden(),null);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.DATOS_FALTANTES,e.getMessage());
        }
        //Entrega faltante
        try{
            cotizacionService.agendarCita(LocalDateTime.of(2050,12,31,10,30),"Bogota",null,cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.DATOS_FALTANTES,e.getMessage());
        }

        try{
            cotizacionService.agendarCita(LocalDateTime.of(2050,12,31,10,30),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.DATOS_FALTANTES,e.getMessage());
        }
        //Hora antes de las 8
        try{
            cotizacionService.agendarCita(LocalDateTime.of(2050,12,31,5,30),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }
        //Hora despues de las 3pm
        try{
            cotizacionService.agendarCita(LocalDateTime.of(2050,12,31,15,1),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }

        //No se agenda con una hora de diferencia

        try{
            cotizacionService.agendarCita(LocalDateTime.now().plusMinutes(30),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }

        //Se agendo para ahorita
        try{
            cotizacionService.agendarCita(LocalDateTime.now(),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }
        //Se agendo para fecha anterior
        try{
            cotizacionService.agendarCita(LocalDateTime.of(2024,5,13,10,1),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }
        //Paso de dos horas entre citas
        try{
            cotizacionService.agendarCita(LocalDateTime.of(20250,5,13,10,1),"null","Calle de prueba",cotizacion.getIden(),cliente);
        }catch (LincolnLinesException e){
            fail(e.getMessage());
        }

        try{
            cotizacionService.agendarCita(LocalDateTime.of(20250,5,13,11,1),null,"Calle de prueba",cotizacion2.getIden(),cliente);
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
           assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }


        //Cotizacion ya agendada
        try{
            cotizacionService.agendarCita(LocalDateTime.of(20250,5,10,10,1),null,"Calle de prueba",cotizacion.getIden(),cliente);

        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.COTIZACION_AGENDADA,e.getMessage());
        }

        //Carrito Vacio
        cotizacionService.quitarDelCarrito(productoService.buscarProductoPorNombre("casco"),cotizacion3.getIden());
        try{
            cotizacionService.agendarCita(LocalDateTime.of(20250,5,10,10,1),null,"Calle de prueba",cotizacion3.getIden(),cliente);
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.CARRITO_VACIO,e.getMessage());
        }





    }










}

