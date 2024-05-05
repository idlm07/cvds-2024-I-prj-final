public class OfertaComercial {
    private String nombre;
    private int id;
    private double valor;
    private double descuento;
    private String descripcionBreve;
    private String descripcionTecnica;
    private String categoria;
    private double impuesto;

    public OfertaComercial(String nombre, int id, double valor, double descuento, String descripcionBreve, String descripcionTecnica, String categoria, double impuesto) {
        this.nombre = nombre;
        this.id = id;
        this.valor = valor;
        this.descuento = descuento;
        this.descripcionBreve = descripcionBreve;
        this.descripcionTecnica = descripcionTecnica;
        this.categoria = categoria;
        this.impuesto = impuesto;
    }

   
    public double calcularPrecioConDescuento() {
        double precioConDescuento = valor - (valor * descuento);
        return precioConDescuento;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public String getDescripcionBreve() {
        return descripcionBreve;
    }

    public void setDescripcionBreve(String descripcionBreve) {
        this.descripcionBreve = descripcionBreve;
    }

    public String getDescripcionTecnica() {
        return descripcionTecnica;
    }

    public void setDescripcionTecnica(String descripcionTecnica) {
        this.descripcionTecnica = descripcionTecnica;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }
}
