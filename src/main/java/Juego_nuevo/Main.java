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
    private static ArrayList<Personaje> personajesPartida;


    //Coger los personajes de la party
    public static ArrayList<Personaje> personajesPartida() {
        return personajesPartida;
    }

    public static void agregarPersonajeParty(Personaje p) {
        if (personajesPartida.size() < 4) {
            personajesPartida.add(p);
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

            }
        } else {
            System.out.println("No se ha encontrado ninguna partida guardada. Se creará una nueva partida.");
        }


        ArrayList<Objeto> inventarioGlobal = new ArrayList<>(InventarioGlobal.getInventarioGlobal());
        int ronda = 0;
        final int rondaFinal = 5;

        System.out.println("Estas solo. Rodeado de puertas, hacia que habitacion quieres ir?");

        while (personajesPartida[0].isVivo() || personajesPartida[1].isVivo()
                || personajesPartida[2].isVivo() || personajesPartida[3].isVivo()) {

            // Genera la descripción de la habitación con la API (sin variable 'descripcion')
            try {
                System.out.println("\n----- HABITACION -----");
                System.out.println("----------------------\n");
            } catch (Exception ex) {
                System.out.println("[Narrador IA no disponible] " + ex.getMessage());
            }

            Enemigo enemigo = Combates.combate(ronda);
            System.out.println("¡Un " + enemigo.getNombre() + " ha aparecido! Prepárate para la batalla.");

            while (enemigo.isVivo() && (personajesPartida[0].isVivo() || personajesPartida[1].isVivo()
                    || personajesPartida[2].isVivo() || personajesPartida[3].isVivo())) {
                Juego.turnoActual(personajesPartida, enemigo);
            }

            if (!enemigo.isVivo()) {
                if (ronda == rondaFinal - 1) {
                    mostrarVictoria();
                    break;
                }
                System.out.println("\n¿Quieres guardar partida (S/N) ?\n");
                String respuesta = scanner.next();
                if ("s".equalsIgnoreCase(respuesta)) {
                    guardadPartida(personajesPartida, inventarioGlobal);
                }
                ronda++;
                SistemaPostBatalla.opcionesPostBatalla(personajesPartida, inventarioGlobal);
            } else {
                mostrarDerrota();
                break;
            }
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
