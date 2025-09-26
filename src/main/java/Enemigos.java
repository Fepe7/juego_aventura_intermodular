public class Enemigos extends Entidad{

    private String nombre;
    private int vida;
    private int ataque;
    private int velocidad;
    private boolean vivo;
    private String descripcion;


    public Enemigos(String nombre, int vida, int ataque, int velocidad, String descripcion) {
        super(nombre, vida, ataque, velocidad);
        this.vivo = true;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "El enemigo " + nombre + " tiene " + vida + " de vida";
    }

    public String getNombre() {
        return nombre;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}