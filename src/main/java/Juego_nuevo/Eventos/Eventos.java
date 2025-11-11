package Juego_nuevo.Eventos;

import Juego_nuevo.*;
import Juego_nuevo.Entidades.Enemigo;
import Juego_nuevo.Entidades.Personaje;
import Juego_nuevo.Objetos.Armaduras;
import Juego_nuevo.Objetos.Armas;
import Juego_nuevo.Objetos.Objeto;
import Juego_nuevo.persistencia_datos_JSON.EstadoPartida;

import java.io.FileReader;
import java.util.*;

public class Eventos {


    static Random random = new Random();


    public static Evento generarEventoAleatorio() {

        int eventoRandom = random.nextInt(6);
        String nombre = "";

        switch (eventoRandom) {
            case 0:
                nombre = "generarEnemigoAleatorio";
                break;
            case 1:
                nombre = "meterParty";
                break;
            case 2:
                nombre = "trampaEnCamino";
                break;
            case 3:
                nombre = "hallazgoTesoro";
                break;
            case 4:
                nombre = "ruinasAntiguas";
                break;
            case 5:
                nombre = "encuentroMercader";
                break;

        }

        //Genera un objeto Evento con el nombre del evento, para despues llamorlo por reflexion
        return new Evento(nombre);
    }


    //Devuelve un enemigo aleatorio
    public static void generarEnemigoAleatorio(Personaje[] personajes, Scanner sc) {

        ArrayList<String> nombresEnemigos = new ArrayList<>();
        Collections.addAll(nombresEnemigos,
                "Orco Guerrero", "Goblin Pícaro", "Mago Oscuro", "Dragón Menor",
                "Esbirro Esqueleto", "Troll de Montaña", "Bandido Mercenario", "Bruja del Bosque",
                "Guardia de Hierro", "Cazador Vampiro", "Golem de Piedra", "Demonio Menor");

        String nombre = nombresEnemigos.get(random.nextInt(nombresEnemigos.size()));
        //Se crea el enemigo a partir del nombre
        Enemigo enemigo = EstadoPartida.crearEnemigo(nombre);


        // Asegurar que el enemigo empieza vivo
        // Sin esto, el combate no se inicia correcamente, no se porque porque el costructor ya pone vivo a true
        //Tengo que mirar por que pasa esto luego
        enemigo.setVivo(true);

        System.out.println(nombre);
        // Pasar el scanner para reutilizar la entrada y permitir que todos los personajes actúen
        Juego.combateEnemigo(personajes, enemigo, sc);

    }


    //Genera un personaje aleatorio
    public static Personaje generarPersonajeAleatorio(Personaje[] personajesParty) {


        //Todos los personajes de posibles
        ArrayList<String> nombresPersonajes = new ArrayList<>();
        Collections.addAll(nombresPersonajes, "Aragorn", "Merlin", "Thorin", "Elara", "Kael");

        //Crea arraylist de personajes disponibles para meter en un party
        ArrayList<String> personajesDisponibles = new ArrayList<>(nombresPersonajes);

        //Elimina los personajes que ya estan en la party
        personajesDisponibles.removeAll(Arrays.stream(personajesParty).toList());

        //Saca un personaje aleatorio de los disponibles
        String nombre = personajesDisponibles.get(random.nextInt(nombresPersonajes.size()));

        return EstadoPartida.crearPersonaje(nombre);
    }


    //Genera el protagonista (el primer personaje del JSON)
    public static Personaje generarProragonista() {

        try (FileReader leer = new FileReader("Personajes.json")) {
            //Leer el primer objeto del JSON (el protagonista)
            Personaje[] personajes = new com.google.gson.Gson().fromJson(leer, Personaje[].class);
            return personajes[0];

        } catch (Exception e) {
            System.out.println("Error al crear el personaje");
            return null;
        }

    }


    //Mete un personaje aleatorio en la party
    //ESTE ES EL QUE SE USA PARA METER PERSONAJES EN LA PARTY
    public static void meterParty(Personaje[] personajesParty) {
        int tamanyoParty = personajesParty.length;

        if (tamanyoParty < 4) {
            // Crea un nuevo array con espacio para un personaje mas
            Personaje[] personajes_nuevo = new Personaje[tamanyoParty + 1];

            // Copia todos los personajes antiguos al nuevo array
            System.arraycopy(personajesParty, 0, personajes_nuevo, 0, tamanyoParty);

            // Genera un personaje aleatorio y lo añade al nuevo array
            Personaje p = generarPersonajeAleatorio(personajesParty);
            personajes_nuevo[tamanyoParty] = p;

            System.out.println("Personaje " + p.getNombre() + " se ha unido a la party");

            // Asigna el nuevo array a la variable statica del Main
            Main.setPersonajesPartida(personajes_nuevo);

            //Se suma 1 al contador de personajes metidos, para las estadisticas
            Main.setnPersonajesMeter(Main.getnPersonajesMeter()+1);
            Main.setnHabitacionesMeter(Main.getnHabitacionesMeter()+1);

        } else {
            System.out.println("La party ya tiene 4 personajes. No se puede agregar más.");
        }
    }


    //NO BORRAR, SE LLAMA MEDANTE REFLEXION, APARECE QUE NO SE USA PERO SI SE USA
    //Te ofrece un objeto gratis
    public static void encuentroMercader() {
        System.out.println("\n=== MERCADER AMBULANTE ===");
        System.out.println("Te encuentras con un hombre misterioso en el camino...");
        System.out.println("Ofrece:");
        Armas arma = generarArmaAleatoria();
        Armaduras armadura = generarArmaduraAleatoria();
        Objeto objeto = generarObjetoAleatorio();

        System.out.println("1. Arma: " + arma.getNombre());
        System.out.println("2. Armadura: " + armadura.getNombre());
        System.out.println("3. Objeto: " + objeto.getNombre());

        int decision;

        do {
            System.out.println("¿Qué deseas coger? (1-3)");
            Scanner scanner = new Scanner(System.in);
            try {
                decision = scanner.nextInt();

            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Caracter invalido. Usa un número (1-3).");
                decision = -1;
                continue;
            }

            switch (decision) {
                case 1:
                    System.out.println("Has cogido: " + arma.getNombre());
                    InventarioGlobal.agregarAlInventarioGlobal(arma);
                    break;
                case 2:
                    System.out.println("Has cogido: " + armadura.getNombre());
                    InventarioGlobal.agregarAlInventarioGlobal(armadura);
                    break;
                case 3:
                    System.out.println("Has cogido: " + objeto.getNombre());
                    InventarioGlobal.agregarAlInventarioGlobal(objeto);
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (decision < 0 || decision > 3);

        //Se suma 1 al contador de objetos recogidos, para las estadisticas
        Main.setnObjetosRecogidosMeter(Main.getnObjetosRecogidosMeter()+1);

        Main.setnHabitacionesMeter(Main.getnHabitacionesMeter()+1);
    }


    //NO BORRAR, SE LLAMA MEDANTE REFLEXION, APARECE QUE NO SE USA PERO SI SE USA
    //Te quita daño
    public static void trampaEnCamino(Personaje[] personajes) {
        System.out.println("\n=== ¡TRAMPA! ===");
        Personaje afectado = personajes[random.nextInt(personajes.length)];
        int danyo = 10 + random.nextInt(20);
        afectado.setVida(afectado.getVida() - danyo);
        System.out.println(afectado.getNombre() + " ha caído en una trampa y recibe " + danyo + " de daño");
        Main.setnHabitacionesMeter(Main.getnHabitacionesMeter()+1);
    }

    //NO BORRAR, SE LLAMA MEDANTE REFLEXION, APARECE QUE NO SE USA PERO SI SE USA
    //Objeto random
    public static void hallazgoTesoro() {
        System.out.println("\n=== TESORO ENCONTRADO ===");
        int random = new Random().nextInt(3) + 1;
        switch (random) {
            case 1:
                Armas arma = generarArmaAleatoria();
                System.out.println("Has encontrado un arma: " + arma.getNombre());
                InventarioGlobal.agregarAlInventarioGlobal(arma);
                System.out.println(arma);
                break;
            case 2:
                Armaduras armadura = generarArmaduraAleatoria();
                System.out.println("Has encontrado una armadura: " + armadura.getNombre());
                InventarioGlobal.agregarAlInventarioGlobal(armadura);
                System.out.println(armadura);
                break;
            case 3:
                Objeto objeto = generarObjetoAleatorio();
                System.out.println("Has encontrado un objeto: " + objeto.getNombre());
                InventarioGlobal.agregarAlInventarioGlobal(objeto);
                System.out.println(objeto);
                break;
        }
        Main.setnObjetosRecogidosMeter(Main.getnObjetosRecogidosMeter()+1);
        Main.setnHabitacionesMeter(Main.getnHabitacionesMeter()+1);

    }

    //NO BORRAR, SE LLAMA MEDANTE REFLEXION, APARECE QUE NO SE USA PERO SI SE USA
    //Random si recibes daño o encuentras un objeto
    public static void ruinasAntiguas(Personaje[] personajes) {
        System.out.println("\n=== RUINAS ANTIGUAS ===");
        System.out.println("Encuentras ruinas de una civilización olvidada...");
        System.out.println("¿Investigas las ruinas? (1: Sí, 2: No)");

        Scanner scanner = new Scanner(System.in);

        int decision = 0;

        //Para que no metas un caractor no valido
        while (decision < 1 || decision > 2) {
            try {
                decision = scanner.nextInt();
                if (decision < 1 || decision > 2) {
                    System.out.println("Opción no válida. Intenta de nuevo.");
                    decision = -1;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Caracter invalido. Usa un número (1-2).");
                decision = -1;
            }
        }


        if (decision == 1) {
            int suerte = random.nextInt(2);
            if (suerte == 0) {
                System.out.println("¡Encuentras un artefacto antiguo!");
                Objeto objeto = generarObjetoAleatorio();
                InventarioGlobal.agregarAlInventarioGlobal(objeto);
                System.out.println("Has encontrado: " + objeto.getNombre());
                Main.setnObjetosRecogidosMeter(Main.getnObjetosRecogidosMeter()+1);
                Main.setnHabitacionesMeter(Main.getnHabitacionesMeter()+1);
            } else {
                System.out.println("¡Una trampa antigua se activa!");
                Personaje afectado = personajes[random.nextInt(personajes.length)];
                afectado.setVida(afectado.getVida() - 25);
                System.out.println(afectado.getNombre() + " recibe 25 de daño!");
                Main.setnHabitacionesMeter(Main.getnHabitacionesMeter()+1);
            }
        } else {
            System.out.println("No has investigado las ruinas.");
            System.out.println("Te quedas sin saber que ahi...");
        }
    }


    private static Armas generarArmaAleatoria() {
        String[][] armas = {
                {"Espada de Hierro Perfeccionada", "Aumenta el ataque en 10 puntos "},
                {"Bastón Elemental", "Aumenta el ataque en 20 puntos "},
                {"Arco Largo Élfico", "Aumenta el ataque en 20 puntos "},
                {"Dagas Envenenadas", "Aumenta el ataque en 10 puntos "}
        };

        int indice = random.nextInt(armas.length);
        return new Armas(armas[indice][0], armas[indice][1]);
    }

    private static Armaduras generarArmaduraAleatoria() {
        String[][] armaduras = {
                {"Escudo Perfeccionado", "Aumenta la vida máxima en 10 puntos"},
                {"Armadura de Placas", "Aumenta la vida máxima en 30 "},
                {"Túnica Mágica", "Aumenta la vida maxíma en 15 puntos"},
                {"Armadura Ligera", "Aumenta vida y velocidad en 10 "},
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