package co.edu.eci.cvds.exception;

public class LincolnLinesException extends Exception{
    public static final String DATOS_FALTANTES = "No se proporcionaron los datos necesario";
    public static final  String VALOR_NEGATIVO = "No se aceptan valores negativos";
    public static final String PRODUCTO_NO_COMPATIBLE = "El Producto no es apto para el vehiculo";
    public static final String FECHA_NO_DISPONIBLE = "No se puede separar cita para esa fecha u hora";
    public static final String FECHA_CLONCLUIDA = "No se puden agregar productos al carrito despues de agendar cita";
    public LincolnLinesException(String message){
        super(message);
    }
}
