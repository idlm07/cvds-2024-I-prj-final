package co.edu.eci.cvds;


import co.edu.eci.cvds.Exception.LincolnLinesException;
import co.edu.eci.cvds.ID.VehiculoID;
import co.edu.eci.cvds.model.*;
import co.edu.eci.cvds.repository.CotizacionRepository;
import co.edu.eci.cvds.repository.ProductoRepository;
import co.edu.eci.cvds.repository.VehiculoRepository;
import co.edu.eci.cvds.service.*;


import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.money.Monetary;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;



@SpringBootTest
class SpringApplicationTests {

    @Autowired
    VehiculoService vehiculoService;

    @Autowired
    ProductoService productoService;

    @Autowired
    CategoriaService categoriaService;





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
        Producto productoEncontrado = productoService.buscarProductoPorNombre("Luces Led De Lujo Para Persiana Parrilla");
        assertEquals(2,vehiculoService.getVehiculos().size());
        assertTrue(vehiculoService.getVehiculos().contains(vehiculo));
        assertTrue(vehiculoService.getVehiculos().contains( new Vehiculo("Suzuki","Swfit","2012")));
        assertEquals(vehiculoService.getVehiculo("Volkswagen","Gol"),vehiculo);
        assertEquals(producto,productoEncontrado);
    }
}





