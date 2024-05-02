package co.edu.eci.cvds.exceptions;

public class LincolnLinesException extends Exception{
    public static final String NEGATTIVE = "No se puede ingresar un valor negativo";
    public LincolnLinesException(String message){
        super(message);
    }
}
