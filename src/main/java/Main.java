import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public class PantallasJuego {

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
    }





    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Personaje[] personajesPartida;

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
                    personajesPartida = crearPartidaNueva();
                } else {
                    System.out.println("Partida importada correctamente.");
                    var objetos = ImportarDatos.cargarObjetos();
                    if ( objetos != null ) {
                        InventarioGlobal.setInventarioGlobal(objetos);
                        System.out.println("Objetos importados correctamente.");
                    } else {
                        System.out.println("No se han podido importar los objetos. Se usarán los objetos iniciales.");
                    }
                }
            } else {
                personajesPartida = crearPartidaNueva();
            }
        } else {
            System.out.println("No se ha encontrado ninguna partida guardada. Se creará una nueva partida.");
            personajesPartida = crearPartidaNueva();
        }

        ArrayList<Objeto> inventarioGlobal = new ArrayList<>(InventarioGlobal.getInventarioGlobal());

        int ronda = 0;


        final int rondaFinal = 5; // o el número de la última ronda/jefe

        while (personajesPartida[0].isVivo() || personajesPartida[1].isVivo() || personajesPartida[2].isVivo() || personajesPartida[3].isVivo()) {
            Enemigos enemigo = Combates.combate(ronda);
            System.out.println("¡Un " + enemigo.getNombre() + " ha aparecido! Prepárate para la batalla.");
            while (enemigo.isVivo() && (personajesPartida[0].isVivo() || personajesPartida[1].isVivo() || personajesPartida[2].isVivo() || personajesPartida[3].isVivo())) {
                Juego.turnoActual(personajesPartida, enemigo);
            }

            if (!enemigo.isVivo()) {
                if (ronda == rondaFinal - 1) {
                    PantallasJuego.mostrarVictoria();
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
                PantallasJuego.mostrarDerrota();
                break;
            }
        }
    }

    private static Personaje[] crearPartidaNueva() {
        Personaje personaje1 = Personaje.CrearPersonaje();
        Personaje personaje2 = Personaje.CrearPersonaje();
        Personaje personaje3 = Personaje.CrearPersonaje();
        Personaje personaje4 = Personaje.CrearPersonaje();
        return new Personaje[]{personaje1, personaje2, personaje3, personaje4};
    }

    private static void guardadPartida(Personaje[] personajesPartida, ArrayList<Objeto> inventarioGlobal) {
        System.out.println("Guardando partida...");

        GuardarDatos guardarDatos = new GuardarDatos();
        GuardarDatos.guardarPersonajes(personajesPartida);
        GuardarDatos.guardarObjetos(InventarioGlobal.getInventarioGlobal());

        System.out.println("Partida guardada correctamente.");
    }
}



