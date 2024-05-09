package co.edu.eci.cvds;

import co.edu.eci.cvds.model.*;
import co.edu.eci.cvds.repository.ClienteRepository;
import co.edu.eci.cvds.repository.CotizacionRepository;
import co.edu.eci.cvds.repository.ProductoRepository;
import co.edu.eci.cvds.repository.VehiculoRepository;
import co.edu.eci.cvds.service.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;



@SpringBootTest
class SpringApplicationTests {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @InjectMocks
    private ProductoService productoService;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private CotizacionRepository cotizacionRepository;

    @InjectMocks
    private CotizacionSerrvice cotizacionSerrvice;






    @Test
    void shouldLLenarTablas(){
        Vehiculo vehiculo = new Vehiculo("Suzuki","i-40","2023");
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        Vehiculo vehiculoGuardado = vehiculoService.agregarVehiculo(vehiculo);
        assertEquals(vehiculo, vehiculoGuardado);
        assertEquals(1,vehiculoService.getVehiculos().size());
        vehiculoGuardado = vehiculoRepository.findByMarcaAndModelAndYearVehicle(vehiculo.getMarca(),vehiculo.getModel(),vehiculo.getYearVehicle()).get(0);
        assertEquals(vehiculoService.getVehiculo("Suzuki","i-40","2023"), vehiculoGuardado);

        Producto producto = new Producto("Motor1","Motor",(float)15.2,"US");
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        when(productoRepository.findByNombre(anyString())).thenReturn(List.of(producto));
        Producto productoGuardado = productoService.agregarProducto(producto);
        assertEquals(producto, productoGuardado);
        assertEquals(1,productoService.buscarProductos().size());
        productoGuardado = productoRepository.findByNombre("Motor1").get(0);
        assertEquals(productoService.buscarProductoPorNombre("Motor1"),productoGuardado);
    }

    /**
     * Prueba para verificar el método equals de Producto.
     */
    @Test
     void testEquals() {
        Producto producto1 = new Producto("nombre1", "categoria1", 100.0f, "USD");
        Producto producto2 = new Producto("nombre1", "categoria1", 100.0f, "USD");
        Producto producto3 = new Producto("nombre2", "categoria2", 200.0f, "EUR");

        assertEquals(producto1, producto2);
        assertNotEquals(producto1, producto3);
        assertNotEquals(producto1, new Object());
    }



    @Test
     void shouldAgregarAlCarrito() {
        Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
        Cotizacion cotizacion = new Cotizacion(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
        Producto producto =  new Producto("Producto1","Categoria",200f,"US");
        Producto producto1 = new Producto("Producto", "Categoria",200f,"US");
        vehiculoService.agregarVehiculo(vehiculo);
        productoService.agregarProducto(producto);
        productoService.agregarProducto(producto1);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto1);
        cotizacion = cotizacionSerrvice.agregarAlCarritoPrimeraVez(producto,vehiculo);
        when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
        cotizacionSerrvice.agregarAlCarritoNVez(producto1,cotizacion);
        List<Producto> carrito = cotizacionSerrvice.verCarrito(cotizacion.getIden());
        assertEquals(2, carrito.size());
        assertTrue(carrito.contains(producto) && carrito.contains(producto1));
    }
    @Test
    void shouldNotAgregarCarrito(){
        Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
        Cotizacion cotizacion = new Cotizacion(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
        Producto producto =  new Producto("Producto1","Categoria",200f,"US");
        Producto producto1 = new Producto("Producto2", "Categoria",200f,"US");
        vehiculoService.agregarVehiculo(vehiculo);
        productoService.agregarProducto(producto);
        productoService.agregarProducto(producto1);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto);
        cotizacion = cotizacionSerrvice.agregarAlCarritoPrimeraVez(producto,vehiculo);
        cotizacionSerrvice.agregarAlCarritoNVez(producto1,cotizacion);
        when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
        List<Producto> carrito = cotizacionSerrvice.verCarrito(cotizacion.getIden());
        assertEquals(1, carrito.size());
        assertTrue(carrito.contains(producto) && !carrito.contains(producto1));
    }





    /**
     * Prueba para quitar un producto del carrito.
     */
    @Test
     void testQuitarDelCarrito() {
        Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
        Cotizacion cotizacion = new Cotizacion(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
        Producto producto =  new Producto("Producto1","Categoria",200f,"US");
        Producto producto1 = new Producto("Producto", "Categoria",200f,"US");
        vehiculoService.agregarVehiculo(vehiculo);
        productoService.agregarProducto(producto);
        productoService.agregarProducto(producto1);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto1);
        cotizacion = cotizacionSerrvice.agregarAlCarritoPrimeraVez(producto,vehiculo);
        when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
        cotizacionSerrvice.agregarAlCarritoNVez(producto1,cotizacion);
        cotizacionSerrvice.quitarDelCarrito(producto,cotizacion);
        assertEquals(1,cotizacionSerrvice.verCarrito(cotizacion.getIden()).size());
        assertFalse(cotizacionSerrvice.verCarrito(cotizacion.getIden()).contains(producto));
        assertTrue(cotizacionSerrvice.verCarrito(cotizacion.getIden()).contains(producto1));
    }

    /**
     * Prueba para agregar un vehículo.
     */
    @Test
     void testAgregarVehiculo() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);
        Vehiculo addedVehiculo = vehiculoService.agregarVehiculo(vehiculo);
        assertEquals(vehiculo, addedVehiculo);
    }

    /**
     * Prueba para obtener un vehículo por marca, modelo y año.
     */
    @Test
     void testGetVehiculo() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        vehiculoService.agregarVehiculo(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        Vehiculo retrievedVehiculo = vehiculoService.getVehiculo("Toyota", "Corolla", "2022");
        assertEquals(vehiculo, retrievedVehiculo);
    }

    /**
     * Prueba para obtener la lista de vehículos.
     */
    @Test
     void testGetVehiculos() {
        Vehiculo vehiculo1 = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Honda", "Civic", "2023");
        vehiculoService.agregarVehiculo(vehiculo1);
        vehiculoService.agregarVehiculo(vehiculo2);
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo1,vehiculo2));
        List<Vehiculo> vehiculos = vehiculoService.getVehiculos();
        assertEquals(2, vehiculos.size());
        assertTrue(vehiculos.contains(vehiculo1));
        assertTrue(vehiculos.contains(vehiculo2));
    }


    /**
     * Prueba para buscar un producto por su nombre.
     */
    @Test
     void testBuscarProductos() {
        Producto producto1 = new Producto("Mouse", "Computación", 20.0f, "USD");
        Producto producto2 = new Producto("Teclado", "Computación", 40.0f, "USD");
        productoService.agregarProducto(producto1);
        productoService.agregarProducto(producto2);
        when(productoRepository.findAll()).thenReturn(List.of(producto1,producto2));
        List<Producto> productos = productoService.buscarProductos();

        assertEquals(2, productos.size());
        assertTrue(productos.contains(producto1));
        assertTrue(productos.contains(producto2));
    }

    /**
     * Prueba para actualizar producto.
     */
    @Test
     void testActualizarProducto() {
        Producto producto = new Producto("Mouse", "Computación", 20.0f, "USD");
        productoService.agregarProducto(producto);
        when(productoRepository.findByNombre(anyString())).thenReturn(List.of(producto));
        Producto updatedProducto = new Producto("Mouse", "Computación", 25.0f, "USD");
        productoService.actualizarProducto(updatedProducto);
        when(productoRepository.findByNombre(anyString())).thenReturn(List.of(updatedProducto));
        Producto foundProducto = productoService.buscarProductoPorNombre("Mouse");
        assertEquals(25.0f, foundProducto.getValor());
    }


    @Test
     void testBorrarProducto() {

        Producto producto = new Producto("Impresora", "Oficina", 200.0f, "USD");
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        productoService.agregarProducto(producto);

        productoService.borrarProducto("Impresora");

        Producto foundProducto = productoService.buscarProductoPorNombre("Impresora");
        assertNull(foundProducto);
    }

    /**
     * Prueba para borrar un producto.
     */
    @Test
     void testBorrarProductoService() {
        Producto producto = new Producto("Impresora", "Oficina", 200.0f, "USD");
        productoService.agregarProducto(producto);

        productoService.borrarProducto("Impresora");

        Producto foundProducto = productoService.buscarProductoPorNombre("Impresora");
        assertNull(foundProducto);
    }

    /**
     * Prueba para obtener un vehículo utilizando el servicio.
     */
    @Test
     void testGetVehiculoService() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        vehiculoService.agregarVehiculo(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        Vehiculo retrievedVehiculo = vehiculoService.getVehiculo("Toyota", "Corolla", "2022");
        assertEquals(vehiculo, retrievedVehiculo);
    }
    
    @Test
    void shouldBeEquals(){
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Toyota", "Corolla", "2022");
        Producto producto = new Producto("Producto1","Categoria",200f,"COP");
        Producto producto1 = new Producto("Producto1","Categoria",200f,"COP");
        producto.setImpuesto(0.15f);
        producto.setImagen("...");
        producto1.setImpuesto(0.15f);
        producto1.setImagen("...");
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion2 = new Cotizacion();
        Cliente cliente = new Cliente("castanocamilo522@gmail.com","Camilo","Casta",null,"3183074075");
        Cliente cliente1 = new Cliente("castanocamilo522@gmail.com","Camilo","Casta",null,"3183074075");
        assertEquals(vehiculo, vehiculo2);
        assertEquals(producto, producto1);
        assertEquals(cotizacion, cotizacion2);
        assertEquals(cliente, cliente1);
    }

    @Test
    void shouldNotBeEquals(){
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Toyota", "Corolla", "2022");
        vehiculo2.setModel("Cambio");
        Producto producto = new Producto("Producto1","Categoria",200f,"COP");
        Producto producto1 = new Producto("Producto1","Categoria",200f,"COP");
        producto.setImpuesto(0.15f);
        producto.setImagen("...");
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion2 = new Cotizacion();
        cotizacion2.setCiudadRecogida("Bogota");
        Cliente cliente = new Cliente("castanocamilo522@gmail.com","Camilo","Casta",null,"3183074075");
        Cliente cliente1 = new Cliente("castanocamilo522@gmail.com","Camilo","Casta",null,"3183074075");
        cliente.setSegundoApellido("Quintanilla");
        assertNotEquals(vehiculo, vehiculo2);
        assertNotEquals(producto, producto1);
        assertNotEquals(cotizacion, cotizacion2);
        assertNotEquals(cliente, cliente1);
    }
    @Test
    public void testVerificarDisponibilidadCita_CitaDisponible() {
        assertTrue(cotizacionService.verificarDisponibilidadCita("2024-05-10T09:00"));
    }

    @Test
    public void testVerificarDisponibilidadCita_CitaNoDisponible() {
        assertTrue(cotizacionService.verificarDisponibilidadCita("2024-05-10T08:30"));
    }

    @Test
    public void testRegistrarCita() {
        cotizacionService.registrarCita("2024-05-10T10:00");
        assertEquals(1, cotizacionService.getCitas().size());
    }

    @Test
    public void testSolicitarCitaDesdeCarrito_CitaDisponible() {
        Producto producto = new Producto();
        Vehiculo vehiculo = new Vehiculo();
        String fechaHora = "2024-05-10T09:00";
        String servicio = "Servicio 1";
        String cliente = "Cliente 1";
        
        Cotizacion cotizacion = cotizacionService.solicitarCitaDesdeCarrito(producto, vehiculo, fechaHora, servicio, cliente);
        
        assertNotNull(cotizacion);
        assertEquals(1, cotizacionService.getCitas().size());
    }

    @Test
    public void testSolicitarCitaDesdeCarrito_CitaNoDisponible() {
        Producto producto = new Producto();
        Vehiculo vehiculo = new Vehiculo();
        String fechaHora = "2024-05-10T08:30"; // Hora ya ocupada
        String servicio = "Servicio 1";
        String cliente = "Cliente 1";
        
        Cotizacion cotizacion = cotizacionService.solicitarCitaDesdeCarrito(producto, vehiculo, fechaHora, servicio, cliente);
        
        assertNull(cotizacion);
        assertEquals(0, cotizacionService.getCitas().size());
    }



}
