package co.edu.eci.cvds;


import co.edu.eci.cvds.Exception.LincolnLinesException;
import co.edu.eci.cvds.model.*;
import co.edu.eci.cvds.repository.CotizacionRepository;
import co.edu.eci.cvds.repository.ProductoRepository;
import co.edu.eci.cvds.repository.VehiculoRepository;
import co.edu.eci.cvds.service.*;


import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import javax.money.Monetary;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;



@SpringBootTest
class SpringApplicationTests {
/**
    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @InjectMocks
    private ProductoService productoService;



    @Mock
    private CotizacionRepository cotizacionRepository;

    @InjectMocks
    private CotizacionSerrvice cotizacionSerrvice;







    @Test
    void shouldLLenarTablas(){
        try {
            Vehiculo vehiculo = new Vehiculo("Suzuki","i-40","2023");
            when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);
            when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));
            when(vehiculoRepository.findByMarcaAndModel(anyString(),anyString())).thenReturn(List.of(vehiculo));
            Vehiculo vehiculoGuardado = vehiculoService.agregarVehiculo(vehiculo);
            assertEquals(vehiculo, vehiculoGuardado);
            assertEquals(1,vehiculoService.getVehiculos().size());
            vehiculoGuardado = vehiculoRepository.findByMarcaAndModel(vehiculo.getMarca(),vehiculo.getModel()).get(0);
            assertEquals(vehiculoService.getVehiculo("Suzuki","i-40"), vehiculoGuardado);

            Producto producto = new Producto("Motor1","Motor",(float)15.2,"US");
            when(productoRepository.save(any(Producto.class))).thenReturn(producto);
            when(productoRepository.findAll()).thenReturn(List.of(producto));
            when(productoRepository.findByNombre(anyString())).thenReturn(List.of(producto));
            Producto productoGuardado = productoService.agregarProducto(producto);
            assertEquals(producto, productoGuardado);
            assertEquals(1,productoService.buscarProductos().size());
            productoGuardado = productoRepository.findByNombre("Motor1").get(0);
            assertEquals(productoService.buscarProductoPorNombre("Motor1"),productoGuardado);
        }catch (LincolnLinesException e){
            fail("Lanzo excepción");
        }

    }

    /**
     * Prueba para verificar el método equals de Producto.

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
        try{
            Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
            Cotizacion cotizacion = new Cotizacion(vehiculo);
            when(vehiculoRepository.findByMarcaAndModel(anyString(),anyString())).thenReturn(List.of(vehiculo));
            when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
            Producto producto =  new Producto("Producto1","Categoria",200f,"US");
            Producto producto1 = new Producto("Producto", "Categoria",200f,"US");
            vehiculoService.agregarVehiculo(vehiculo);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(producto1);
            vehiculoService.agregarProducto("TOYOTAF","PRIUS",producto);
            vehiculoService.agregarProducto("TOYOTAF","PRIUS",producto1);
            cotizacion = cotizacionSerrvice.agregarAlCarrito(producto,vehiculo,null);
            when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
            cotizacionSerrvice.agregarAlCarrito(producto1,vehiculo,cotizacion);
            List<Producto> carrito = cotizacionSerrvice.verCarrito(cotizacion.getIden());
            assertEquals(2, carrito.size());
            assertTrue(carrito.contains(producto) && carrito.contains(producto1));
        }catch (LincolnLinesException e){
            fail("Lanzo Excepcion");
        }

    }
    @Test
    void shouldNotAgregarCarrito(){
        Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
        Cotizacion cotizacion = new Cotizacion(vehiculo);
        when(vehiculoRepository.findByMarcaAndModel(anyString(),anyString())).thenReturn(List.of(vehiculo));
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
        Producto producto =  new Producto("Producto1","Categoria",200f,"US");
        Producto producto1 = new Producto("Producto2", "Categoria",200f,"US");
        vehiculoService.agregarVehiculo(vehiculo);
        try {
            productoService.agregarProducto(producto);
            productoService.agregarProducto(producto1);
        } catch (LincolnLinesException e) {
            fail("No lanzo Excepcion");
        }

        try{

            vehiculoService.agregarProducto("TOYOTAF","PRIUS",producto);
            cotizacion = cotizacionSerrvice.agregarAlCarrito(producto,vehiculo,null);
        }catch (LincolnLinesException e){
            fail("Lanzo excepcion");
        }

        try{
            cotizacionSerrvice.agregarAlCarrito(producto1,cotizacion.getVehiculo(),cotizacion);
            fail("No lanzo Excepcion");
        }catch (LincolnLinesException e){
            when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
            List<Producto> carrito = cotizacionSerrvice.verCarrito(cotizacion.getIden());
            assertEquals(1, carrito.size());
            assertTrue(carrito.contains(producto) && !carrito.contains(producto1));
        }
    }

    @Test
    void shouldCalcularTotalCarrito(){
        try{
            Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
            Cotizacion cotizacion = new Cotizacion(vehiculo);
            when(vehiculoRepository.findByMarcaAndModel(anyString(),anyString())).thenReturn(List.of(vehiculo));
            when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
            Producto producto =  new Producto("Producto1","Categoria",500000f,"COP");
            Producto producto1 = new Producto("Producto", "Categoria",200f,"USD");
            vehiculoService.agregarVehiculo(vehiculo);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(producto1);
            vehiculoService.agregarProducto("TOYOTAF","PRIUS",producto);
            vehiculoService.agregarProducto("TOYOTAF","PRIUS",producto1);
            cotizacion = cotizacionSerrvice.agregarAlCarrito(producto,vehiculo,null);
            when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
            cotizacionSerrvice.agregarAlCarrito(producto1,cotizacion.getVehiculo(),cotizacion);
            Money calculado = cotizacionSerrvice.calcularTotalCarrito(cotizacion);
            assertEquals(Monetary.getCurrency("COP"),calculado.getCurrency());
            float tasaError = (779600f-calculado.getNumber().floatValue())/779600f;
            assertTrue(tasaError <= 0.15);
        }catch (LincolnLinesException e){
            fail("Lanzo Excepcion");
        }
    }

    @Test
    void shouldAgendar(){
       try{
           Cotizacion cotizaion1 = new Cotizacion(new Vehiculo("Suzuki","Swift","2014"));
           Cotizacion cotizaion2 = new Cotizacion(new Vehiculo("Mercedes","Sedan","2022"));
           cotizacionSerrvice.agendarCita(LocalDateTime.of(2025, 5, 10, 8, 0), "Bogota", "Calle 159 #7-74", cotizaion1);
           when(cotizacionRepository.findByCita()).thenReturn(List.of(cotizaion1));
           cotizacionSerrvice.agendarCita(LocalDateTime.of(2026,5,10,15,0),"Bogota","Calle 66 #11-50",cotizaion2);
           when(cotizacionRepository.findByCita()).thenReturn(List.of(cotizaion1,cotizaion2));
           assertEquals(2,cotizacionSerrvice.cotizacionesAgendadas().size());
       }catch (LincolnLinesException e){
           fail("Lanzo excepcion");
       }

    }

    @Test
    void shouldCalcularTotal(){
        try{
            Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
            Cotizacion cotizacion = new Cotizacion(vehiculo);
            when(vehiculoRepository.findByMarcaAndModel(anyString(),anyString())).thenReturn(List.of(vehiculo));
            when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
            Producto producto =  new Producto("Producto1","Categoria",500000f,"COP");
            Producto producto1 = new Producto("Producto", "Categoria",5,"USD");
            Producto producto3 = new Producto("Producto2", "Categoria1",50,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.2f);
            producto1.setDescuento(0.1f);
            producto1.setImpuesto(0.6f);
            vehiculoService.agregarVehiculo(vehiculo);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(producto1);
            vehiculoService.agregarProducto("TOYOTAF","PRIUS",producto);
            vehiculoService.agregarProducto("TOYOTAF","PRIUS",producto1);
            vehiculoService.agregarProducto("TOYOTAF","PRIUS",producto3);
            cotizacion = cotizacionSerrvice.agregarAlCarrito(producto,vehiculo,null);
            when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
            cotizacionSerrvice.agregarAlCarrito(producto1,cotizacion.getVehiculo(),cotizacion);
            cotizacionSerrvice.agregarAlCarrito(producto3,cotizacion.getVehiculo(),cotizacion);
            float calculado = cotizacionSerrvice.cotizacionTotal(cotizacion);
            float esperado = 728799.79f + 101947.09f + 30514.26f;
            assertTrue(0.15 <= (esperado-calculado)/esperado);
        }catch (LincolnLinesException e){
            fail("Lanzar excepcion");
        }
    }





    /**
     * Prueba para quitar un producto del carrito.

    @Test
    void testQuitarDelCarrito() {
        try{
            Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
            Cotizacion cotizacion = new Cotizacion(vehiculo);
            when(vehiculoRepository.findByMarcaAndModel(anyString(),anyString())).thenReturn(List.of(vehiculo));
            when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
            Producto producto =  new Producto("Producto1","Categoria",200f,"US");
            Producto producto1 = new Producto("Producto", "Categoria",200f,"US");
            vehiculoService.agregarVehiculo(vehiculo);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(producto1);
            vehiculoService.agregarProducto("TOYOTAF","PRIUS",producto);
            vehiculoService.agregarProducto("TOYOTAF","PRIUS",producto1);
            cotizacion = cotizacionSerrvice.agregarAlCarrito(producto,vehiculo,null);
            when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
            cotizacionSerrvice.agregarAlCarrito(producto1,cotizacion.getVehiculo(),cotizacion);
            cotizacionSerrvice.quitarDelCarrito(producto,cotizacion);
            assertEquals(1,cotizacionSerrvice.verCarrito(cotizacion.getIden()).size());
            assertFalse(cotizacionSerrvice.verCarrito(cotizacion.getIden()).contains(producto));
            assertTrue(cotizacionSerrvice.verCarrito(cotizacion.getIden()).contains(producto1));
        }catch (LincolnLinesException e){
            fail("Lanzo Excepcion");
        }
    }

    /**
     * Prueba para agregar un vehículo.

    @Test
    void testAgregarVehiculo() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);
        Vehiculo addedVehiculo = vehiculoService.agregarVehiculo(vehiculo);
        assertEquals(vehiculo, addedVehiculo);
    }

    /**
     * Prueba para obtener un vehículo por marca, modelo y año.

    @Test
    void testGetVehiculo() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        vehiculoService.agregarVehiculo(vehiculo);
        when(vehiculoRepository.findByMarcaAndModel(anyString(),anyString())).thenReturn(List.of(vehiculo));
        Vehiculo retrievedVehiculo = vehiculoService.getVehiculo("Toyota", "Corolla");
        assertEquals(vehiculo, retrievedVehiculo);
    }

    /**
     * Prueba para obtener la lista de vehículos.

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

    @Test
    void testBuscarProductos() {
        try{
            Producto producto1 = new Producto("Mouse", "Computación", 20.0f, "USD");
            Producto producto2 = new Producto("Teclado", "Computación", 40.0f, "USD");
            productoService.agregarProducto(producto1);
            productoService.agregarProducto(producto2);
            when(productoRepository.findAll()).thenReturn(List.of(producto1,producto2));
            List<Producto> productos = productoService.buscarProductos();
            assertEquals(2, productos.size());
            assertTrue(productos.contains(producto1));
            assertTrue(productos.contains(producto2));
        }catch (LincolnLinesException e){
            fail("Lanzo Excepcion");
        }

    }

    /**
     * Prueba para actualizar producto.

    @Test
    void testActualizarProducto() {
        try{
            Producto producto = new Producto("Mouse", "Computación", 20.0f, "USD");
            productoService.agregarProducto(producto);
            when(productoRepository.findByNombre(anyString())).thenReturn(List.of(producto));
            Producto updatedProducto = new Producto("Mouse", "Computación", 25.0f, "USD");
            productoService.actualizarProducto(updatedProducto);
            when(productoRepository.findByNombre(anyString())).thenReturn(List.of(updatedProducto));
            Producto foundProducto = productoService.buscarProductoPorNombre("Mouse");
            assertEquals(25.0f, foundProducto.getValor());
        }catch (LincolnLinesException e){
            fail("Lanzo Excepcion");
        }


    }


    @Test
    void testBorrarProducto() {

        try{
            Producto producto = new Producto("Impresora", "Oficina", 200.0f, "USD");
            when(productoRepository.save(any(Producto.class))).thenReturn(producto);
            productoService.agregarProducto(producto);
            productoService.borrarProducto("Impresora");
            Producto foundProducto = productoService.buscarProductoPorNombre("Impresora");
            assertNull(foundProducto);
        }catch (LincolnLinesException e){
            fail("Lanzo Excepcion");
        }


    }

    /**
     * Prueba para borrar un producto.

    @Test
    void testBorrarProductoService() {
       try{
           Producto producto = new Producto("Impresora", "Oficina", 200.0f, "USD");
           productoService.agregarProducto(producto);

           productoService.borrarProducto("Impresora");

           Producto foundProducto = productoService.buscarProductoPorNombre("Impresora");
           assertNull(foundProducto);
       }catch (LincolnLinesException e){
           fail("Lanzo excepcion");
       }
    }

    /**
     * Prueba para obtener un vehículo utilizando el servicio.

    @Test
    void testGetVehiculoService() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        vehiculoService.agregarVehiculo(vehiculo);
        when(vehiculoRepository.findByMarcaAndModel(anyString(),anyString())).thenReturn(List.of(vehiculo));
        Vehiculo retrievedVehiculo = vehiculoService.getVehiculo("Toyota", "Corolla");
        assertEquals(vehiculo, retrievedVehiculo);
    }

    @Test
    void shouldBeEquals(){
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Toyota", "Corolla", "2022");
        Producto producto = new Producto("Producto1","Categoria",200f,"COP");
        Producto producto1 = new Producto("Producto1","Categoria",200f,"COP");
        producto.setImagen("...");
        producto1.setImagen("...");
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion2 = new Cotizacion();
        Cliente cliente = new Cliente("Camilo","Casta","3183074075","castanocamilo522@gmail.com");
        Cliente cliente1 = new Cliente("Camilo","Casta","3183074075","castanocamilo522@gmail.com");
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
        try {
            producto.setImpuesto(0.15f);
        }catch (LincolnLinesException e){
            fail("lanzo Excepcion");
        }
        producto.setImagen("...");
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion2 = new Cotizacion();
        cotizacion2.agendar(null,"Bogota",null);
        Cliente cliente = new Cliente("Camilo","Casta","3183074075","castanocamilo522@gmail.com");
        Cliente cliente1 = new Cliente("Camilo","Casta","3183074075","castanocamilo522@gmail.com");
        cliente.setApellido("Quintanilla");
        assertNotEquals(vehiculo, vehiculo2);
        assertNotEquals(producto, producto1);
        assertNotEquals(cotizacion, cotizacion2);
        assertNotEquals(cliente, cliente1);
    }
    */
}
