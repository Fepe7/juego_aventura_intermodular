package Juego_nuevo;

import Juego_nuevo.Entidades.Personaje;
import Juego_nuevo.Eventos.Eventos;
import Juego_nuevo.Objetos.Objeto;
import Juego_nuevo.mapa.Room;
import Juego_nuevo.persistencia_datos_JSON.EstadoPartida;

import Juego_nuevo.mapa.Mapa;

import java.io.File;
import java.util.ArrayList;


import java.util.Scanner;

public class Main {


    private static Mapa mapa = new Mapa();

    //Pilla la ubicacion inicial de la habitacion
    private static int[] h_inicial;


    //NO BORRAR, SE LLAMA MEDANTE REFLEXION, APARECE QUE NO SE USA PERO SI SE USA
    //Coger los personajes de la party
    public static Personaje[] personajesPartida(Personaje[] personajesPartida) {
        return personajesPartida;
    }

    //NO BORRAR, SE LLAMA MEDANTE REFLEXION, APARECE QUE NO SE USA PERO SI SE USA
    public static void agregarPersonajeParty(Personaje p, Personaje[] personajesPartida) {
        for (int i = 0; i < personajesPartida.length; i++) {
            if (personajesPartida[i] == null) {
                personajesPartida[i] = p;
                System.out.println("Personaje " + p.getNombre() + " agregado a la party.");
                return;
            }
        }
        System.out.println("La party ya está llena. No se puede agregar más personajes.");
    }


    public static Personaje[] personajesPartida;



    public static void main(String[] args) {

        //Este escaner se pasa a todos los metodos que lo necesiten
        //Para no estar creado un objeto scanner en cada metodo
        //Creo que es lo mas optiomo
        Scanner scanner = new Scanner(System.in);

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
                var datosPartida = EstadoPartida.cargarPartida();
                personajesPartida = datosPartida.personajes();

                if (personajesPartida == null || personajesPartida.length == 0) {
                    System.out.println("No se ha podido importar la partida. Se creará una nueva partida.");

                    personajesPartida = new Personaje[1];
                    personajesPartida[0] = Eventos.generarProragonista();

                    mapa = new Mapa();
                    mapa.generateLayout();

                    EstadoPartida.guardarPartida(personajesPartida, inventarioGlobal, mapa.getSeed(), mapa.extraerEventos());
                } else {
                    System.out.println("Partida importada correctamente.");

                    var objetos = datosPartida.objetos();
                    if (objetos != null) {
                        InventarioGlobal.setInventarioGlobal(objetos);
                        System.out.println("Objetos importados correctamente.");
                    }

                    int seedCargada = datosPartida.seed();
                    System.out.println("Seed importada correctamente: " + seedCargada);

                    // Generar el mapa con la seed guardada
                    mapa = new Mapa(seedCargada);
                    mapa.generateLayout();

                    // Restaurar los eventos del mapa guardado
                    var eventosGuardados = datosPartida.eventos();
                    if (eventosGuardados != null) {
                        mapa.restaurarEventos(eventosGuardados);
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
                mapa = new Mapa();
                mapa.generateLayout();
                int seed = mapa.getSeed();
                EstadoPartida.guardarPartida(personajesPartida, inventarioGlobal, seed, mapa.extraerEventos());


            }
        } else {
            System.out.println("No se ha encontrado ninguna partida guardada. Se creará una nueva partida.");
            //Array de los personajes de la party
            personajesPartida = new Personaje[1];

            System.out.println("Nueva partida creada.");
            //Genera el protagonista y lo mete en la party
            personajesPartida[0] = Eventos.generarProragonista();

            //Genera el mapa y guarda la seed
            mapa = new Mapa();
            mapa.generateLayout();
            int seed = mapa.getSeed();
            EstadoPartida.guardarPartida(personajesPartida, inventarioGlobal, seed, mapa.extraerEventos());


        }


        System.out.println("Estas solo. Rodeado de puertas, hacia que habitacion quieres ir?");

        // Posiciona al jugador en la primera hab generada
        h_inicial = mapa.getRoomCoords(0);
        personajesPartida[0].setPosicion(h_inicial[0], h_inicial[1]);

        while (algunPersonajeVivo(personajesPartida)) {
            System.out.println(mapa.toString(personajesPartida[0]));

            //Se pasa scanner para leer la direccion
            pedirDireccionMover(personajesPartida, mapa, scanner);

            //Se pasa scanner para eventos que lo necesiten
            procesarEventoHab(personajesPartida, mapa, scanner);


        }


    }


    //Comprueba si algun personaje esta vivo, si no hay ninguno vivo devuelve false
    public static boolean algunPersonajeVivo(Personaje[] personajesPartida) {
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


    //Se pasa scanner para eventos que lo necesiten
    public static void procesarEventoHab(Personaje[] personajesPartida, Mapa mapa, Scanner scanner) {
        //Recoge la posicon actual del personaje
        int[] posicion = personajesPartida[0].getPosicion();

        //Coge la matriz del mapa y coge la posicion en la que esta el personaje
        Room h = (Room) mapa.getMap()[posicion[0]][posicion[1]];

        // Solo activa evento si no es la habitación inicial (generatedOrder != 0)
        if (h.getGeneratedOrder() != 0) {
            h.activarEvento(personajesPartida, mapa, scanner);
        }


    }


    //Para simplificar las direcciones en el bucle principal
    //Se pasa scanner para leer la direccion
    public static void pedirDireccionMover(Personaje personajes[], Mapa mapa, Scanner scanner) {
        System.out.println("1 - Arriba");
        System.out.println("2 - Derecha");
        System.out.println("3 - Abajo");
        System.out.println("4 - Izquierda");
        System.out.println("5 - Informacion de la party");
        int direccion = scanner.nextInt() - 1;

        if (direccion >= 0 && direccion <= 3) {
            personajes[0].mover(mapa, direccion);
        } else if (direccion == 4) {
            infoParty(personajes);
        }


    }


    //Se guarda la partida cada vez que se completa un evento
    public static void guardadPartida(Personaje[] personajesPartida, ArrayList<Objeto> inventarioGlobal, int seed) {
        System.out.println("Guardando partida...");
        EstadoPartida.guardarPartida(personajesPartida, inventarioGlobal, seed, mapa.extraerEventos());
        System.out.println("Partida guardada correctamente.");
    }


    public static void infoParty(Personaje[] personajesPartida) {
        System.out.println("La party tiene:");
        for (Personaje p : personajesPartida) {
            if (p != null) {
                System.out.println(p);
            }
        }
    }

    public static void setPersonajesPartida(Personaje[] personajesPartida) {
        Main.personajesPartida = personajesPartida;
    }

    
}