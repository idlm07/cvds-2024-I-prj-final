package co.edu.eci.cvds.Exception;

public class LincolnLinesException extends Exception{
    public static final String DATOS_FALTANTES = "No se proporcionaron los datos necesario";
    public static final  String VALOR_NEGATIVO = "No se aceptan valores negativos";
    public static final String PRODUCTO_NO_COMPATIBLE = "El Producto no es apto para el vehiculo";
    public static final String FECHA_NO_DISPONIBLE = "No se puede separar cita para esa fecha u hora";
    public LincolnLinesException(String message){
        super(message);
    }
}
