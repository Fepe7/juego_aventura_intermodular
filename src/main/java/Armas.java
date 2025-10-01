public class Armas extends Objeto {
    public Armas(String nombre, String descripcion) {
        super(nombre, descripcion);
    }

    // Armas GUERRERO
    public void espadaHierroPerfeccionada(Personaje p) {
        if (p.getClase() != TiposClases.GUERRERO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.GUERRERO);
        } else {
            p.setAtaque(p.getAtaque() + 10);
        }
    }

    public void espadaDragon(Personaje p) {
        if (p.getClase() != TiposClases.GUERRERO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.GUERRERO);
        } else {
            p.setAtaque(p.getAtaque() + 25);
        }
    }

    public void hachaGuerra(Personaje p) {
        if (p.getClase() != TiposClases.GUERRERO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.GUERRERO);
        } else {
            p.setAtaque(p.getAtaque() + 20);
            p.setVelocidad(p.getVelocidad() - 5);
        }
    }

    public void martilloRunico(Personaje p) {
        if (p.getClase() != TiposClases.GUERRERO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.GUERRERO);
        } else {
            p.setAtaque(p.getAtaque() + 25);
        }
    }


    // Armas MAGO
    public void bastonElemental(Personaje p) {
        if (p.getClase() != TiposClases.MAGO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.MAGO);
        } else {
            p.setAtaque(p.getAtaque() + 20);
        }
    }

    public void orbeArcano(Personaje p) {
        if (p.getClase() != TiposClases.MAGO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.MAGO);
        } else {
            p.setAtaque(p.getAtaque() + 15);
            p.setMana(p.getMana() + 10);
        }
    }

    public void tomoProhibido(Personaje p) {
        if (p.getClase() != TiposClases.MAGO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.MAGO);
        } else {
            p.setAtaque(p.getAtaque() + 30);
            p.setVida(p.getVida() - 10);
        }
    }


    // Armas ARQUERO
    public void arcoLargoElfico(Personaje p) {
        if (p.getClase() != TiposClases.ARQUERO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.ARQUERO);
        } else {
            p.setAtaque(p.getAtaque() + 20);
        }
    }

    public void ballestaPesada(Personaje p) {
        if (p.getClase() != TiposClases.ARQUERO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.ARQUERO);
        } else {
            p.setAtaque(p.getAtaque() + 30);
            p.setVelocidad(p.getVelocidad() - 10);
        }
    }

    public void arcoCortoEncantado(Personaje p) {
        if (p.getClase() != TiposClases.ARQUERO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.ARQUERO);
        } else {
            p.setAtaque(p.getAtaque() + 15);
        }
    }


    // Armas ASESINO
    public void dagasEnvenenadas(Personaje p) {
        if (p.getClase() != TiposClases.ASESINO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.ASESINO);
        } else {
            p.setAtaque(p.getAtaque() + 10);
        }
    }

    public void espadasGemelas(Personaje p) {
        if (p.getClase() != TiposClases.ASESINO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.ASESINO);
        } else {
            p.setAtaque(p.getAtaque() + 18);
        }
    }

    public void cuchillaSombra(Personaje p) {
        if (p.getClase() != TiposClases.ASESINO) {
            System.out.println("No puedes llevar este arma con una clase distinta a " + TiposClases.ASESINO);
        } else {
            p.setAtaque(p.getAtaque() + 25);
            p.setVelocidad(p.getVelocidad() + 10);
        }
    }
}
