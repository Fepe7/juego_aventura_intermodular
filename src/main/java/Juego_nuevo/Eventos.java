package Juego_nuevo;

import Juego_nuevo.persistencia_datos_JSON.EstadoPartida;
import Juego_original.Enemigos;

import java.io.File;
import java.io.FileReader;
import java.util.*;

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



    //Devuelve un enemigo aleatorio
    public static Enemigo generarEnemigoAleatorio(){

        ArrayList<String> nombresEnemigos = new ArrayList<>();
        Collections.addAll(nombresEnemigos,
        "Orco Guerrero", "Goblin Pícaro", "Mago Oscuro", "Dragón Menor",
        "Esbirro Esqueleto", "Troll de Montaña", "Bandido Mercenario", "Bruja del Bosque",
        "Guardia de Hierro", "Cazador Vampiro", "Golem de Piedra", "Demonio Menor");

        String nombre = nombresEnemigos.get(random.nextInt(nombresEnemigos.size()));

        return EstadoPartida.crearEnemigo(nombre);

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


    public static Personaje generarProragonista(){

        try(FileReader leer = new FileReader("Personajes.json")) {
            //Leer el primer objeto del JSON (el protagonista)
            Personaje[] personajes = new com.google.gson.Gson().fromJson(leer, Personaje[].class);
            return personajes[0];

        }catch (Exception e) {
            System.out.println("Error al crear el personaje");
            return null;
        }

    }


    //Mete un personaje aleatorio en la party
    public static void meterParty(Personaje[] personajesParty){
        Personaje p = generarPersonajeAleatorio(personajesParty);
        Main.agregarPersonajeParty(p, personajesParty);
    }


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
            decision = scanner.nextInt();

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
        }while (decision < 0 || decision > 3);



    }

    //Te quita daño
    public static void trampaEnCamino(Personaje[] personajes) {
        System.out.println("\n=== ¡TRAMPA! ===");
        Personaje afectado = personajes[random.nextInt(personajes.length)];
        int danyo = 10 + random.nextInt(20);
        afectado.setVida(afectado.getVida() - danyo);
        System.out.println(afectado.getNombre() + " ha caído en una trampa y recibe " + danyo + " de daño");
    }


    //Objeto random
    public static void hallazgoTesoro() {
        System.out.println("\n=== TESORO ENCONTRADO ===");
        int random = new Random().nextInt(3)+1;
        switch (random) {
            case 1:
                Armas arma = generarArmaAleatoria();
                System.out.println("Has encontrado un arma: " + arma.getNombre());
                InventarioGlobal.agregarAlInventarioGlobal(arma);
                break;
            case 2:
                Armaduras armadura = generarArmaduraAleatoria();
                System.out.println("Has encontrado una armadura: " + armadura.getNombre());
                InventarioGlobal.agregarAlInventarioGlobal(armadura);
                break;
            case 3:
                Objeto objeto = generarObjetoAleatorio();
                System.out.println("Has encontrado un objeto: " + objeto.getNombre());
                InventarioGlobal.agregarAlInventarioGlobal(objeto);
                break;
        }
    }


    //Random si recibes daño o encuentras un objeto
    public static void ruinasAntiguas(Personaje[] personajes) {
        System.out.println("\n=== RUINAS ANTIGUAS ===");
        System.out.println("Encuentras ruinas de una civilización olvidada...");
        System.out.println("¿Investigas las ruinas? (1: Sí, 2: No)");

        Scanner scanner = new Scanner(System.in);
        int decision = scanner.nextInt();

        if (decision == 1) {
            int suerte = random.nextInt(2);
            if (suerte == 0) {
                System.out.println("¡Encuentras un artefacto antiguo!");
                InventarioGlobal.agregarAlInventarioGlobal(generarObjetoAleatorio());
            } else {
                System.out.println("¡Una trampa antigua se activa!");
                Personaje afectado = personajes[random.nextInt(personajes.length)];
                afectado.setVida(afectado.getVida() - 25);
                System.out.println(afectado.getNombre() + " recibe 25 de daño!");
            }
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