package Juego_nuevo;

public class Armaduras extends Objeto {
    public Armaduras(String nombre, String descripcion) {
        super(nombre, descripcion);
    }

    public void escudoPerfeccionado(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() + 10);
    }

    public void armaduraPlacas(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() + 30);
        p.setVelocidad(p.getVelocidad() - 10);

    }

    public void tunicaMagica(Personaje p) {

        p.setVida_maxima(p.getVida_maxima() + 15);
        p.setMana(p.getMana() + 15);

    }


    public void armaduraLigera(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() + 10);
        p.setVelocidad(p.getVelocidad() + 10);

    }

    public void escudoBendito(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() + 20);
    }


    public void quitarEscudoPerfeccionado(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() - 10);
    }

    public void quitarArmaduraPlacas(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() - 30);
        p.setVelocidad(p.getVelocidad() + 10);

    }

    public void quitarTunicaMagica(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() - 15);
        p.setMana(p.getMana() - 15);

    }

    public void quitarArmaduraLigera(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() - 10);
        p.setVelocidad(p.getVelocidad() - 10);

    }

    public void quitarEscudoBendito(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() - 20);
    }


}
