package Juego_nuevo;

import Juego_nuevo.persistencia_datos_JSON.EstadoPartida;

import Juego_nuevo.mapa.Map;

import java.io.File;
import java.util.ArrayList;


import java.util.Scanner;

public class Main {


    public static void mostrarVictoria() {
        System.out.println("\n==============================");
        System.out.println("        ¡VICTORIA! 🏆");
        System.out.println("==============================");
        System.out.println("¡Has derrotado al jefe final!");
        System.out.println("¡Felicidades, has ganado la partida!");
        System.out.println("==============================\n");
    }

    public static void mostrarDerrota() {
        System.out.println("\n==============================");
        System.out.println("        DERROTA ☠️");
        System.out.println("==============================");
        System.out.println("Todos tus personajes han caído.");
        System.out.println("Fin del juego. ¡Inténtalo de nuevo!");
        System.out.println("==============================\n");
    }


    //Array estatico de persoanjes
    private Personaje[] personajesPartida = new Personaje[0];

    private int seed;


    //Coger los personajes de la party
    public static Personaje[] personajesPartida(Personaje[] personajesPartida) {
        return personajesPartida;
    }

    public static void agregarPersonajeParty(Personaje p, Personaje[] personajesPartida) {
        if (personajesPartida.length < 4) {
            personajesPartida[personajesPartida.length] = p;
            System.out.println("Personaje " + p.getNombre() + " agregado a la party.");
        } else {
            System.out.println("La party ya tiene 4 personajes. No se puede agregar más.");
        }
    }


    public static void main(String[] args) {

        //Pa la consola en otra ventana
        TextConsoleWindow.install("RPG - Consola", true);
        Scanner scanner = new Scanner(System.in);

        Personaje[] personajesPartida = new Personaje[4];
        ArrayList<Objeto> inventarioGlobal = new ArrayList<>(InventarioGlobal.getInventarioGlobal());

        //Fichero de guardado
        File partidaGuardada = new File("Partida.json");


        if (partidaGuardada.exists()) {
            int opcion = 0;
            try {
                do {
                    System.out.println("""
                            Se ha detectado una partida guardada.
                            1) Importar partida guardada
                            2) Crear una nueva partida
                            Escoge una opción (1-2): """);
                    opcion = scanner.nextInt();
                    if (opcion < 1 || opcion > 2) {
                        System.out.println("Introduce uno de los números indicados.");
                    }
                } while (opcion < 1 || opcion > 2);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }

            if (opcion == 1) {
                personajesPartida = EstadoPartida.cargarPartida().getPersonajes();
                if (personajesPartida == null || personajesPartida.length == 0) {
                    System.out.println("No se ha podido importar la partida. Se creará una nueva partida.");
                    // Crear nueva partida aquí
                    personajesPartida = new Personaje[1];
                    personajesPartida[0] = Eventos.generarProragonista();
                    Map mapa = new Map();
                    EstadoPartida.guardarPartida(personajesPartida, inventarioGlobal, mapa.getSeed());
                } else {
                    System.out.println("Partida importada correctamente.");
                    var objetos = EstadoPartida.cargarPartida().getObjetos();
                    if (objetos != null) {
                        InventarioGlobal.setInventarioGlobal(objetos);
                        System.out.println("Objetos importados correctamente.");
                    }
                    var datosPartida = EstadoPartida.cargarPartida();
                    if (datosPartida != null) {
                        System.out.println("Seed importada correctamente: " + datosPartida.getSeed());
                    }
                }
            } else {
                System.out.println("Creando una nueva partida...");

                //Array de los personajes de la party
                personajesPartida = new Personaje[1];

                System.out.println("Nueva partida creada.");
                //Genera el protagonista y lo mete en la party
                personajesPartida[0] = Eventos.generarProragonista();

                //Genera el mapa y guarda la seed
                Map mapa = new Map();
                int seed = mapa.getSeed();
                EstadoPartida.guardarPartida(personajesPartida, inventarioGlobal, seed);


            }
        } else {
            System.out.println("No se ha encontrado ninguna partida guardada. Se creará una nueva partida.");
            //Array de los personajes de la party
            personajesPartida = new Personaje[4];

            System.out.println("Nueva partida creada.");
            //Genera el protagonista y lo mete en la party
            personajesPartida[0] = Eventos.generarProragonista();

            //Genera el mapa y guarda la seed
            Map mapa = new Map();
            int seed = mapa.getSeed();
            EstadoPartida.guardarPartida(personajesPartida, inventarioGlobal, seed);


        }


        System.out.println("Estas solo. Rodeado de puertas, hacia que habitacion quieres ir?");

        while (algunPersonajeVivo(personajesPartida)) {


        }
    }


    //Comprueba si algun personaje esta vivo, si no hay ninguno vivo devuelve false
    private static boolean algunPersonajeVivo(Personaje[] personajesPartida) {
        if (personajesPartida == null) {
            return false;
        }
        for (Personaje p : personajesPartida) {
            if (p != null && p.isVivo()) {
                return true;
            }
        }
        return false;
    }


    private static void guardadPartida(Personaje[] personajesPartida, ArrayList<Objeto> inventarioGlobal, int seed) {
        System.out.println("Guardando partida...");
        EstadoPartida.guardarPartida(personajesPartida, inventarioGlobal, seed);
        System.out.println("Partida guardada correctamente.");
    }


}
