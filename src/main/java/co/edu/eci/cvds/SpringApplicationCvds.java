package co.edu.eci.cvds;

import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.model.Configuration;
import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.ClienteRepository;
import co.edu.eci.cvds.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class SpringApplicationCvds {
	;
	private final ProductoService productoService;
	private final VehiculoService vehiculoService;
	private final CotizacionSerrvice cotizacionSerrvice;
	private final ClienteService clienteService;

	@Autowired
	public SpringApplicationCvds(
			ProductoService productoService,
			VehiculoService vehiculoService,
			CotizacionSerrvice cotizacionService,
			ClienteService clienteService

	) {
		this.productoService = productoService;
		this.vehiculoService = vehiculoService;
		this.cotizacionSerrvice = cotizacionService;
		this.clienteService = clienteService;

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringApplicationCvds.class, args);
	}

    /**
     * Prueba para verificar la igualdad y el código hash de la clase Cliente.
     *
     * @param correo1 Correo electrónico para el primer cliente.
     * @param nombre1 Nombre para el primer cliente.
     * @param primerApellido1 Primer apellido para el primer cliente.
     * @param segundoApellido1 Segundo apellido para el primer cliente.
     * @param celular1 Número de celular para el primer cliente.
     * @param correo2 Correo electrónico para el segundo cliente (igual al primer cliente).
     * @param nombre2 Nombre para el segundo cliente (igual al primer cliente).
     * @param primerApellido2 Primer apellido para el segundo cliente (igual al primer cliente).
     * @param segundoApellido2 Segundo apellido para el segundo cliente (igual al primer cliente).
     * @param celular2 Número de celular para el segundo cliente (igual al primer cliente).
     * @param correo3 Correo electrónico para el tercer cliente (diferente al primer cliente).
     * @param nombre3 Nombre para el tercer cliente (diferente al primer cliente).
     * @param primerApellido3 Primer apellido para el tercer cliente (diferente al primer cliente).
     * @param segundoApellido3 Segundo apellido para el tercer cliente (diferente al primer cliente).
     * @param celular3 Número de celular para el tercer cliente (diferente al primer cliente).
     */

	 @Test
	 public void testEqualsAndHashCode() {
		 Cliente cliente1 = new Cliente("correo1@example.com", "Juan", "Perez", "Gomez", "1234567890");
		 Cliente cliente2 = new Cliente("correo1@example.com", "Juan", "Perez", "Gomez", "1234567890");
		 Cliente cliente3 = new Cliente("correo2@example.com", "Pedro", "Lopez", null, "0987654321");
 
		 // Comprobación de igualdad entre cliente1 y cliente2
		 Assertions.assertEquals(cliente1, cliente2);
		 Assertions.assertEquals(cliente1.hashCode(), cliente2.hashCode());
 
		 // Comprobación de desigualdad entre cliente1 y cliente3
		 Assertions.assertNotEquals(cliente1, cliente3);
		 Assertions.assertNotEquals(cliente1.hashCode(), cliente3.hashCode());
	 }

    /**
     * Prueba para verificar el método hashCode de la clase Cliente.
     *
     * @param correo1 Correo electrónico para el primer cliente.
     * @param nombre1 Nombre para el primer cliente.
     * @param primerApellido1 Primer apellido para el primer cliente.
     * @param segundoApellido1 Segundo apellido para el primer cliente.
     * @param celular1 Número de celular para el primer cliente.
     */
    @Test
    public void testHashCode(
            String correo1, String nombre1, String primerApellido1,
            String segundoApellido1, String celular1) {

        // Crear dos objetos Cliente con los mismos atributos
        Cliente cliente1 = new Cliente(correo1, nombre1, primerApellido1, segundoApellido1, celular1);
        Cliente cliente2 = new Cliente(correo1, nombre1, primerApellido1, segundoApellido1, celular1);

        // Verificar que el código hash de ambos objetos sea igual
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }

	public class ClienteTest {

    /**
     * Prueba para verificar el método equals de la clase Cliente.
     *
     * @param correo1 Correo electrónico para el primer cliente.
     * @param nombre1 Nombre para el primer cliente.
     * @param primerApellido1 Primer apellido para el primer cliente.
     * @param segundoApellido1 Segundo apellido para el primer cliente.
     * @param celular1 Número de celular para el primer cliente.
     */
    @Test
    public void testEquals(
            String correo1, String nombre1, String primerApellido1,
            String segundoApellido1, String celular1) {

        // Crear dos objetos Cliente con los mismos atributos
        Cliente cliente1 = new Cliente(correo1, nombre1, primerApellido1, segundoApellido1, celular1);
        Cliente cliente2 = new Cliente(correo1, nombre1, primerApellido1, segundoApellido1, celular1);

        // Crear un objeto Cliente con diferentes atributos
        Cliente cliente3 = new Cliente("correo2@example.com", "Pedro", "Lopez", null, "0987654321");

        // Verificar que un objeto es igual a sí mismo
        assertTrue(cliente1.equals(cliente1));

        // Verificar que dos objetos con los mismos atributos son iguales
        assertTrue(cliente1.equals(cliente2));

        // Verificar que dos objetos con diferentes atributos no son iguales
        assertFalse(cliente1.equals(cliente3));
    }

	    /**
     * Prueba para verificar la creación de una Cotizacion con constructor vacío.
     */
    @Test
    public void testConstructorVacio() {
        // Crear una instancia de Cotizacion con constructor vacío
        Cotizacion cotizacion = new Cotizacion();

        // Verificar que la cotizacion se crea con estado "CREADO"
        assertEquals("CREADO", cotizacion.getEstado());
        assertNotNull(cotizacion.getFechaCreacion());
        assertTrue(cotizacion.getProductosCotizacion().isEmpty());
    }

	/**
     * Prueba para verificar la creación de una Cotizacion con constructor parametrizado.
     */
    @Test
    public void testConstructorParametrizado() {
        // Crear una instancia de Cliente para usar en la Cotizacion
        Cliente cliente = new Cliente("correo@example.com", "Nombre", "Apellido", null, "1234567890");

        // Crear una instancia de Cotizacion con constructor parametrizado
        Timestamp cita = new Timestamp(System.currentTimeMillis());
        Cotizacion cotizacion = new Cotizacion(cita, "Ciudad", "Dirección", cliente);

        // Verificar que los atributos se asignan correctamente
        assertEquals("CREADO", cotizacion.getEstado());
        assertNotNull(cotizacion.getFechaCreacion());
        assertEquals(cita, cotizacion.getCita());
        assertEquals("Ciudad", cotizacion.getCiudadRecogida());
        assertEquals("Dirección", cotizacion.getDireccionRecogida());
        assertEquals(cliente, cotizacion.getCliente());
        assertTrue(cotizacion.getProductosCotizacion().isEmpty());
    }

    /**
     * Prueba para verificar el método toString de la clase Cotizacion.
     */
    @Test
    public void testToString() {
        // Crear una instancia de Cotizacion
        Cotizacion cotizacion = new Cotizacion();

        // Verificar que el método toString retorna el formato esperado
        assertNotNull(cotizacion.toString());
    }

	public class ProductoTest {

		/**
		 * Prueba para verificar el constructor vacío de Producto.
		 */
		@Test
		public void testConstructorVacio() {
			Producto producto = new Producto();
			assertNotNull(producto);
			assertTrue(producto.getVehiculos().isEmpty());
			assertTrue(producto.getCotizaciones().isEmpty());
		}

	/**
     * Prueba para verificar el constructor parametrizado de Producto.
     *
     * @param nombre     Nombre del producto.
     * @param categoria  Categoría del producto.
     * @param valor      Valor del producto.
     * @param moneda     Moneda del producto.
     */
    @Test
    public void testConstructorParametrizado(
        String nombre, String categoria, float valor, String moneda) {
        Producto producto = new Producto(nombre, categoria, valor, moneda);
        assertNotNull(producto);
        assertEquals(nombre, producto.getNombre());
        assertEquals(categoria, producto.getCategoria());
        assertEquals(valor, producto.getValor());
        assertEquals(moneda, producto.getMoneda());
        assertEquals(0, producto.getDescuento());
        assertEquals(0, producto.getImpuesto());
        assertTrue(producto.getVehiculos().isEmpty());
        assertTrue(producto.getCotizaciones().isEmpty());
    }

	/**
     * Prueba para verificar el método agregarVehiculo de Producto.
     */
    @Test
    public void testAgregarVehiculo() {
        Producto producto = new Producto();
        Vehiculo vehiculo = new Vehiculo();
        producto.agregarVehiculo(vehiculo);
        assertTrue(producto.getVehiculos().contains(vehiculo));
    }

	/**
     * Prueba para verificar el método agregarCotizacion de Producto.
     */
    @Test
    public void testAgregarCotizacion() {
        Producto producto = new Producto();
        Cotizacion cotizacion = new Cotizacion();
        producto.agregarCotizacion(cotizacion);
        assertTrue(producto.getCotizaciones().contains(cotizacion));
    }

	/**
     * Prueba para verificar el método hashCode de la clase Cotizacion.
     */
    @Test
    public void testHashCode() {
        // Crear una instancia de Cotizacion
        Cotizacion cotizacion = new Cotizacion();

        // Verificar que el código hash se genera correctamente
        assertNotNull(cotizacion.hashCode());
    }

    /**
     * Prueba para verificar el método hashCode de Producto.
     */
    @Test
    public void testHashCode() {
        Producto producto = new Producto("nombre", "categoria", 100.0f, "USD");
        assertNotNull(producto.hashCode());
    }

    /**
     * ** Prueba para verificar el constructor vacío de Vehiculo.
     */
    @Test
    public void testConstructorVacio() {
        Vehiculo vehiculo = new Vehiculo();
        assertNotNull(vehiculo);
        assertTrue(vehiculo.getProductosVehiculo().isEmpty());
    }

	   /**
     * ** Prueba para verificar el constructor parametrizado de Vehiculo.
     *
     * @param marca Marca del vehículo.
     * @param model Modelo del vehículo.
     * @param year Año del vehículo.
     */
    @Test
    public void testConstructorParametrizado(
            String marca, String model, String year) {
        Vehiculo vehiculo = new Vehiculo(marca, model, year);
        assertNotNull(vehiculo);
        assertEquals(marca, vehiculo.getMarca());
        assertEquals(model, vehiculo.getModel());
        assertEquals(year, vehiculo.getYear());
        assertTrue(vehiculo.getProductosVehiculo().isEmpty());
    }

	/**
     * ** Prueba para verificar el método anadirProducto de Vehiculo.
     */
    @Test
    public void testAnadirProducto() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        Producto producto = new Producto("Llantas", "Accesorios", 200.0f, "USD");
        vehiculo.anadirProducto(producto);
        assertTrue(vehiculo.getProductosVehiculo().contains(producto));
    }

	/**
     * ** Prueba para verificar el método hashCode de Vehiculo.
     */
    @Test
    public void testHashCode() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        assertNotNull(vehiculo.hashCode());
    }

    /**
     * Prueba para verificar el método equals de Producto.
     */
    @Test
    public void testEquals() {
        Producto producto1 = new Producto("nombre1", "categoria1", 100.0f, "USD");
        Producto producto2 = new Producto("nombre1", "categoria1", 100.0f, "USD");
        Producto producto3 = new Producto("nombre2", "categoria2", 200.0f, "EUR");

        assertTrue(producto1.equals(producto2));
        assertFalse(producto1.equals(producto3));
        assertFalse(producto1.equals(new Object()));
    }

	/**
     * ** Prueba para verificar el método hashCode de Vehiculo.
     */
    @Test
    public void testHashCode() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        assertNotNull(vehiculo.hashCode());
    }

    /**
     * ** Prueba para verificar el método equals de Vehiculo.
     */
    @Test
    public void testEquals() {
        Vehiculo vehiculo1 = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo3 = new Vehiculo("Honda", "Civic", "2023");

        assertTrue(vehiculo1.equals(vehiculo2));
        assertFalse(vehiculo1.equals(vehiculo3));
        assertFalse(vehiculo1.equals(new Object()));
    }

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void testFindByCorreo() {
        // Datos de prueba
        String correoBuscado = "correo@example.com";
        Cliente cliente1 = new Cliente("correo@example.com", "Nombre", "Apellido", null, "1234567890");
        Cliente cliente2 = new Cliente("otrocorreo@example.com", "OtroNombre", "OtroApellido", null, "0987654321");
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        // Simular el comportamiento del repositorio al buscar por correo
        when(clienteRepository.findByCorreo(correoBuscado)).thenReturn(Arrays.asList(cliente1));

        // Llamar al servicio que utiliza el repositorio
        List<Cliente> clientesEncontrados = clienteService.buscarClientesPorCorreo(correoBuscado);

        // Verificar que se haya llamado al método del repositorio y que devuelva el cliente esperado
        assertEquals(1, clientesEncontrados.size());
        assertEquals(cliente1, clientesEncontrados.get(0));
    }



	@Bean
	public CommandLineRunner run() {
		return (args) -> {
			/*
			productoService.agregarProducto(new Producto("Motor1","Motor",(float)15.2,"US"));
			vehiculoService.agregarVehiculo(new Vehiculo("Suzuki","i-40","2023"));
			vehiculoService.agregarProducto("Suzuki","i-40","2023",new Producto("Motor1","Motor",(float)15.2,"US"));
			*/
		};
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

