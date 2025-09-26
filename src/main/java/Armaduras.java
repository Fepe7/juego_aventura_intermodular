public class Armaduras extends Objeto {
    public Armaduras(String nombre, String descripcion) {
        super(nombre, descripcion);
    }

    public void escudoPerfeccionado(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() + 10);
    }

    public void armaduraPlacas(Personaje p) {
        if (p.getClase() != TiposClases.GUERRERO) {
            System.out.println("No puedes equiparte esta armadura siendo de una clase disinta a " + TiposClases.GUERRERO);
        } else {
            p.setVida_maxima(p.getVida_maxima() + 30);
            p.setVelocidad(p.getVelocidad() - 10);
        }
    }

    public void tunicaMagica(Personaje p) {
        if (p.getClase() != TiposClases.MAGO) {
            System.out.println("No puedes equiparte esta armadura siendo de una clase disinta a " + TiposClases.MAGO);
        } else {
            p.setVida_maxima(p.getVida_maxima() + 15);
            p.setMana(p.getMana() + 15);
        }
    }


    public void armaduraLigera(Personaje p) {
        if (p.getClase() != TiposClases.ARQUERO && p.getClase() != TiposClases.ASESINO) {
            System.out.println("No puedes equiparte esta armadura siendo de una clase disinta a " + TiposClases.ARQUERO + " o " + TiposClases.ASESINO);
        } else {
            p.setVida_maxima(p.getVida_maxima() + 10);
            p.setVelocidad(p.getVelocidad() + 10);
        }
    }

    public void escudoBendito(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() + 20);
    }



    public void quitarEscudoPerfeccionado(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() - 10);
    }

    public void quitarArmaduraPlacas(Personaje p) {
        if (p.getClase() == TiposClases.GUERRERO) {
            p.setVida_maxima(p.getVida_maxima() - 30);
            p.setVelocidad(p.getVelocidad() + 10);
        }
    }

    public void quitarTunicaMagica(Personaje p) {
        if (p.getClase() == TiposClases.MAGO) {
            p.setVida_maxima(p.getVida_maxima() - 15);
            p.setMana(p.getMana() - 15);
        }
    }

    public void quitarArmaduraLigera(Personaje p) {
        if (p.getClase() == TiposClases.ARQUERO || p.getClase() == TiposClases.ASESINO) {
            p.setVida_maxima(p.getVida_maxima() - 10);
            p.setVelocidad(p.getVelocidad() - 10);
        }
    }

    public void quitarEscudoBendito(Personaje p) {
        p.setVida_maxima(p.getVida_maxima() - 20);
    }



}
