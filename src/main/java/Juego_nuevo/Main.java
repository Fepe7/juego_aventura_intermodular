package Juego_nuevo;

import Juego_nuevo.persistencia_datos_JSON.GuardarDatos;
import Juego_nuevo.persistencia_datos_JSON.ImportarDatos;

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
    private static Personaje[] personajesPartida;


    //Coger los personajes de la party
    public static Personaje[] personajesPartida() {
        return personajesPartida;
    }

    public static void agregarPersonajeParty(Personaje p) {
        if (personajesPartida.length < 4) {
            personajesPartida[personajesPartida.length] = p;
            System.out.println("Personaje " + p.getNombre() + " agregado a la party.");
        }else {
            System.out.println("La party ya tiene 4 personajes. No se puede agregar más.");
        }
    }


    public static void main(String[] args) {

        //Pa la consola en otra ventana
        TextConsoleWindow.install("RPG - Consola", true);
        Scanner scanner = new Scanner(System.in);


        if (ImportarDatos.existePartidaGuardada()) {
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
                personajesPartida = ImportarDatos.cargarPersonajes();
                if (personajesPartida == null || personajesPartida.length == 0) {
                    System.out.println("No se ha podido importar la partida. Se creará una nueva partida.");
                } else {
                    System.out.println("Partida importada correctamente.");
                    var objetos = ImportarDatos.cargarObjetos();
                    if (objetos != null) {
                        InventarioGlobal.setInventarioGlobal(objetos);
                        System.out.println("Objetos importados correctamente.");
                    } else {
                        System.out.println("No se han podido importar los objetos. Se usarán los objetos iniciales.");
                    }
                }
            } else {
                System.out.println("Creando una nueva partida...");
                personajesPartida = new Personaje[4];
                // Aquí podrías agregar lógica para crear nuevos personajes
                System.out.println("Nueva partida creada.");
                //Genera el protagonista y lo mete en la party
                personajesPartida[0] = Eventos.generarProragonista();

            }
        } else {
            System.out.println("No se ha encontrado ninguna partida guardada. Se creará una nueva partida.");
        }


        ArrayList<Objeto> inventarioGlobal = new ArrayList<>(InventarioGlobal.getInventarioGlobal());

        System.out.println("Estas solo. Rodeado de puertas, hacia que habitacion quieres ir?");

        while (personajesPartida[0].isVivo() || personajesPartida[1].isVivo()
                || personajesPartida[2].isVivo() || personajesPartida[3].isVivo()) {


        }
    }


    private static void guardadPartida(Personaje[] personajesPartida, ArrayList<Objeto> inventarioGlobal) {
        System.out.println("Guardando partida...");
        GuardarDatos guardarDatos = new GuardarDatos();
        GuardarDatos.guardarPersonajes(personajesPartida);
        GuardarDatos.guardarObjetos(InventarioGlobal.getInventarioGlobal());
        System.out.println("Partida guardada correctamente.");
    }

}
