package Juego_nuevo;

public class Habilidad {
    private String nombre;
    private int costeMana;
    private int danyo;
    private String descripcion;
    private TipoHabilidad tipoHabilidad;


    //Constructor para las habilidades, por defecto son las de daño
    public Habilidad(String nombre, int costeMana, int danio, String descripcion, TipoHabilidad tipo) {
        this.nombre = nombre;
        this.costeMana = costeMana;
        this.danyo = danio;
        this.descripcion = descripcion;
        this.tipoHabilidad = tipo;
    }

public void bendicionElfa(Personaje p) {
    p.setVida(p.getVida() + 15);
    p.setAtaque(p.getAtaque() + 5);
    Objeto.comprobarVidaMaxima(p);
    System.out.println("Has usado " + nombre);
    System.out.println("Has sido bendecido: +15 vida y +5 ataque");
}




    public void golpePoderoso(Personaje p, Enemigos e) {
        int vidaQuitar = 15 + p.getAtaque();
        e.setVida(e.getVida() - vidaQuitar);
        System.out.println("Has usado " + nombre);
        System.out.println("Has inflingido " + vidaQuitar + " puntos de daño a " + e.getNombre());
    }


    //Aumenta el daño toda la batalla
    public void rugidoDeBatalla(Personaje p) {
        p.setAtaque(p.getAtaque() + 10);
        System.out.println("Has usado " + nombre);
        System.out.println("Tu ataque ha aumentado en 10 puntos");
    }


    // Bola de fuego: daño fijo + ataque del personaje
    public void bolaDeFuego(Personaje p, Enemigos e) {
        int vidaQuitar = 35 + p.getAtaque();
        e.setVida(e.getVida() - vidaQuitar);
        System.out.println("Has usado " + nombre);
        System.out.println("Has inflingido " + vidaQuitar + " puntos de daño a " + e.getNombre());

    }

    // Palabra curativa: cura al personaje
    public void palabraCurativa(Personaje p) {
        p.setVida(p.getVida() + 20);
        Objeto.comprobarVidaMaxima(p);
        System.out.println("Has usado " + nombre);
        System.out.println("Te has curado 20 puntos de vida");
    }

    // Flecha precisa: daño fijo + ataque del personaje
    public void flechaPrecisa(Personaje p, Enemigos e) {
        int vidaQuitar = 20 + p.getAtaque();
        e.setVida(e.getVida() - vidaQuitar);
        System.out.println("Has usado " + nombre);
        System.out.println("Has inflingido " + vidaQuitar + " puntos de daño a " + e.getNombre());

    }

    // Trozo de carne: cura al personaje
    public void trozoDeCarne(Personaje p) {
        p.setVida(p.getVida() + 20);
        Objeto.comprobarVidaMaxima(p);
        System.out.println("Has usado " + nombre);
        System.out.println("Te has curado 20 puntos de vida");
    }

    // Ataque furtivo: daño fijo + ataque del personaje
    public void ataqueFurtivo(Personaje p, Enemigos e) {
        int vidaQuitar = 25 + p.getAtaque();
        e.setVida(e.getVida() - vidaQuitar);
        System.out.println("Has usado " + nombre);
        System.out.println("Has inflingido " + vidaQuitar + " puntos de daño a " + e.getNombre());

    }

    public void golpeVenenoso(Personaje p, Enemigos e) {
        int vidaQuitar = p.getAtaque() + 15; // Daño base
        e.setVida(e.getVida() - vidaQuitar - 15);
        System.out.println("Has usado " + nombre);
        System.out.println("Has inflingido " + vidaQuitar + " puntos de daño a " + e.getNombre());

    }


    @Override
    public String toString() {
        return nombre + ": " + descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCosteMana() {
        return costeMana;
    }

    public int getDanyo() {
        return danyo;
    }

    public TipoHabilidad getTipoHabilidad() {
        return tipoHabilidad;
    }

    public void setTipoHabilidad(TipoHabilidad tipoHabilidad) {
        this.tipoHabilidad = tipoHabilidad;
    }
}