package co.edu.eci.cvds.ID;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

public class ClienteID implements Serializable {
    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private String apellido;
}
