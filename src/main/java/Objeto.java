public class Objeto {
    private String nombre;
    private String descripcion;


    public Objeto(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


    @Override
    public String toString() {
        return nombre + ": " + descripcion;
    }

    public void pocionVida(Personaje p) {
        p.setVida(p.getVida() + 20);
    }

    public void pocionVelocidad(Personaje p) {
        p.setVelocidad(p.getVelocidad() + 20);
    }

    public void comidaCurativa(Personaje p) {
        p.setVida(p.getVida() + 10);
    }

    public void pocionMana(Personaje p) {
        p.setMana(p.getMana() + 30);
    }

    public void carneSeca(Personaje p) {
        p.setVida(p.getVida() + 20);
        p.setVelocidad(p.getVelocidad() - 5);
    }

    public void elixirPoder(Personaje p) {
        p.setAtaque(p.getAtaque() + 10);
    }


    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
