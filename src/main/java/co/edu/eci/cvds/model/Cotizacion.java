public class Cotizacion {
    private int id;
    private Carrito carrito;

    public Cotizacion(int id, Carrito carrito) {
        this.id = id;
        this.carrito = carrito;
    }

    
    public double getPrecio() {
        return carrito.getPrecio();
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
}