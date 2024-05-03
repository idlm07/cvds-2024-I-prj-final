package co.edu.eci.cvds;

import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.VehiculoRepository;
import co.edu.eci.cvds.service.ProductoService;
import co.edu.eci.cvds.service.VehiculoService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/*import org.junit.jupiter.api.Test;*/

@SpringBootTest
class SpringApplicationTests {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @Test
    void shouldAgregarVehiculo(){
        Vehiculo vehiculo = new Vehiculo("Suzuki","i-40","2023");
        vehiculoService.agregarVehiculo(vehiculo);
        assertFalse(vehiculoService.getVehiculos().isEmpty());

    }

}
