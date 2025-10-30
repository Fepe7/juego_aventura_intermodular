package Juego_nuevo;

import java.util.Scanner;
import java.util.Random;

public class Eventos {


    static Random random = new Random();


    //Evento hoguera donde se recupera la vida y el mana de los personajes
    private static void irAHoguera(Personaje[] personajes) {
        System.out.println("\n=== HOGUERA ===");
        System.out.println("Te acercas a la hoguera reconfortante...");
        System.out.println("El calor de las llamas restaura tus fuerzas.");

        for (Personaje p : personajes) {
            if (p.isVivo()) {
                p.setVida(p.getVida_maxima());
                p.setMana(p.getMana() + 20); //
                System.out.println("Se recupera 20 puntos de maná.");
                System.out.println(p.getNombre() + " ha recuperado toda su vida! [" + p.getVida() + "/" + p.getVida_maxima() + "]");
            }
        }

        System.out.println("\nTu equipo está completamente recuperado y listo para continuar.");
    }




    private static Armas generarArmaAleatoria() {
        String[][] armas = {
                {"Espada de Hierro Perfeccionada", "Aumenta el ataque en 10 puntos (Solo Guerrero)"},
                {"Bastón Elemental", "Aumenta el ataque en 20 puntos (Solo Mago)"},
                {"Arco Largo Élfico", "Aumenta el ataque en 20 puntos (Solo Arquero)"},
                {"Dagas Envenenadas", "Aumenta el ataque en 10 puntos (Solo Asesino)"}
        };

        int indice = random.nextInt(armas.length);
        return new Armas(armas[indice][0], armas[indice][1]);
    }

    private static Armaduras generarArmaduraAleatoria() {
        String[][] armaduras = {
                {"Escudo Perfeccionado", "Aumenta la vida máxima en 10 puntos"},
                {"Armadura de Placas", "Aumenta la vida máxima en 30 (Solo Guerrero)"},
                {"Túnica Mágica", "Aumenta la vida maxíma en 15 puntos"},
                {"Armadura Ligera", "Aumenta vida y velocidad en 10 (Arquero/Asesino)"},
                {"Escudo Bendito", "Aumenta la vida máxima en 20 puntos"}
        };

        int indice = random.nextInt(armaduras.length);
        return new Armaduras(armaduras[indice][0], armaduras[indice][1]);
    }

    private static Objeto generarObjetoAleatorio() {
        String[][] objetos = {
                {"Poción de vida", "Restaura 50 puntos de vida"},
                {"Poción de maná", "Restaura 30 puntos de maná"},
                {"Poción de velocidad", "Aumenta la velocidad en 20 puntos"},
                {"Comida curativa", "Restaura 10 puntos de vida"},
                {"Carne seca", "Restaura 20 de vida pero reduce 5 de velocidad"},
                {"Elixir de poder", "Aumenta el ataque en 10 puntos"}
        };

        int indice = random.nextInt(objetos.length);
        return new Objeto(objetos[indice][0], objetos[indice][1]);
    }




}