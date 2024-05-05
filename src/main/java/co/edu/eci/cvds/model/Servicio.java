public class Servicio extends OfertaComercial {
    private String tipoUsuario;

    public Servicio(String nombre, int id, double valor, double descuento, String descripcionBreve, String descripcionTecnica, String categoria, double impuesto, String tipoUsuario) {
        super(nombre, id, valor, descuento, descripcionBreve, descripcionTecnica, categoria, impuesto);
        this.tipoUsuario = tipoUsuario;
    }

    // Getter y setter para tipoUsuario
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}