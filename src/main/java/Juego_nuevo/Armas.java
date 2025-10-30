package Juego_nuevo;

public class Armas extends Objeto {
    public Armas(String nombre, String descripcion) {
        super(nombre, descripcion);
    }


    public static void espadaHierroPerfeccionada(Personaje p) {

        p.setAtaque(p.getAtaque() + 10);

    }

    public static void espadaDragon(Personaje p) {

        p.setAtaque(p.getAtaque() + 25);

    }

    public static void hachaGuerra(Personaje p) {

        p.setAtaque(p.getAtaque() + 20);
        p.setVelocidad(p.getVelocidad() - 5);
    }


    public static void martilloRunico(Personaje p) {

        p.setAtaque(p.getAtaque() + 25);

    }


    public static void bastonElemental(Personaje p) {

        p.setAtaque(p.getAtaque() + 20);
    }


    public static void orbeArcano(Personaje p) {

        p.setAtaque(p.getAtaque() + 15);
        p.setMana(p.getMana() + 10);

    }

    public static void tomoProhibido(Personaje p) {

        p.setAtaque(p.getAtaque() + 30);
        p.setVida(p.getVida() - 10);

    }


    public static void arcoLargoElfico(Personaje p) {

        p.setAtaque(p.getAtaque() + 20);

    }

    public static void ballestaPesada(Personaje p) {

        p.setAtaque(p.getAtaque() + 30);
        p.setVelocidad(p.getVelocidad() - 10);
    }


    public static void arcoCortoEncantado(Personaje p) {

        p.setAtaque(p.getAtaque() + 15);

    }


    public static void dagasEnvenenadas(Personaje p) {

        p.setAtaque(p.getAtaque() + 10);

    }

    public static void espadasGemelas(Personaje p) {

        p.setAtaque(p.getAtaque() + 18);
    }


    public static void cuchillaSombra(Personaje p) {

        p.setAtaque(p.getAtaque() + 25);
        p.setVelocidad(p.getVelocidad() + 10);

    }
}
