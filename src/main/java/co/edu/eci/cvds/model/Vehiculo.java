public class Vehiculo {
    private int id;
    private String marca;
    private String placa;
    private int ano;
    private String ciudad;
    private double cilindraje;
    private String modelo;

    public Vehiculo(int id, String marca, String placa, int ano, String ciudad, double cilindraje, String modelo) {
        this.id = id;
        this.marca = marca;
        this.placa = placa;
        this.ano = ano;
        this.ciudad = ciudad;
        this.cilindraje = cilindraje;
        this.modelo = modelo;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public double getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(double cilindraje) {
        this.cilindraje = cilindraje;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
