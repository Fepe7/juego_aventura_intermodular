public class Enemigos extends Entidad {

    private String descripcion;


    public Enemigos(String nombre, int vida, int ataque, int velocidad, String descripcion) {
        super(nombre, vida, ataque, velocidad);
        this.descripcion = descripcion;
        setVivo(true);
    }

    @Override
    public String toString() {
        return "El enemigo " + nombre + " tiene " + vida + " de vida";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}