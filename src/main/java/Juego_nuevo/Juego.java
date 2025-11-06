package Juego_nuevo;

import java.util.*;

public class Juego {


    //Esto devuelve un array list con el orden de los turnos segun la velocidad de los personajes y enemigos
    //El el orden en el que estan en el array es el orden de los turnos
    //El primero ataca el primero etc...
    public static ArrayList obtenerOrdenTurnos(Personaje[] personajes, Enemigo enemigo) {

        ArrayList<Entidad> ordenTurnos = new ArrayList<>();

        ordenTurnos.addAll(Arrays.asList(personajes));
        ordenTurnos.addAll(Arrays.asList(enemigo));

        ordenTurnos.sort(Comparator.comparing(p -> Integer.valueOf(p instanceof Personaje ? ((Personaje) p).getVelocidad() : ((Enemigo) p).getVelocidad())).reversed());
        return ordenTurnos;
    }


    //Devuelve el metodo a usar dependiendo del nombre del objeto
    static String getString(Objeto objetoSeleccionado) {
        String nombreObjeto = objetoSeleccionado.getNombre().toLowerCase();
        String nombreMetodo = "";

        // Verifica si es una armadura
        if (objetoSeleccionado instanceof Armaduras) {
            if (nombreObjeto.contains("escudo perfeccionado")) {
                nombreMetodo = "escudoPerfeccionado";
            } else if (nombreObjeto.contains("armadura de placas") || nombreObjeto.contains("armadura placas")) {
                nombreMetodo = "armaduraPlacas";
            } else if (nombreObjeto.contains("túnica mágica") || nombreObjeto.contains("tunica magica")) {
                nombreMetodo = "tunicaMagica";
            } else if (nombreObjeto.contains("armadura ligera")) {
                nombreMetodo = "armaduraLigera";
            } else if (nombreObjeto.contains("escudo bendito")) {
                nombreMetodo = "escudoBendito";
            }
        } else if (objetoSeleccionado instanceof Armas) {
            if (nombreObjeto.contains("espada de hierro") || nombreObjeto.contains("espada hierro") ||
                    nombreObjeto.contains("espada hierro perfeccionada")) {
                nombreMetodo = "espadaHierroPerfeccionada";
            } else if (nombreObjeto.contains("espada dragon") || nombreObjeto.contains("espada de dragon")) {
                nombreMetodo = "espadaDragon";
            } else if (nombreObjeto.contains("hacha guerra") || nombreObjeto.contains("hacha de guerra")) {
                nombreMetodo = "hachaGuerra";
            } else if (nombreObjeto.contains("martillo runico") || nombreObjeto.contains("martillo de guerra")) {
                nombreMetodo = "martilloRunico";
            } else if (nombreObjeto.contains("bastón elemental") || nombreObjeto.contains("baston elemental")) {
                nombreMetodo = "bastonElemental";
            } else if (nombreObjeto.contains("orbe arcano")) {
                nombreMetodo = "orbeArcano";
            } else if (nombreObjeto.contains("tomo prohibido")) {
                nombreMetodo = "tomoProhibido";
            } else if (nombreObjeto.contains("arco largo") || nombreObjeto.contains("arco largo elfico")) {
                nombreMetodo = "arcoLargoElfico";
            } else if (nombreObjeto.contains("ballesta pesada")) {
                nombreMetodo = "ballestaPesada";
            } else if (nombreObjeto.contains("arco corto")) {
                nombreMetodo = "arcoCortoEncantado";
            } else if (nombreObjeto.contains("dagas") || nombreObjeto.contains("dagas envenenadas")) {
                nombreMetodo = "dagasEnvenenadas";
            } else if (nombreObjeto.contains("espadas gemelas")) {
                nombreMetodo = "espadasGemelas";
            } else if (nombreObjeto.contains("cuchilla sombra")) {
                nombreMetodo = "cuchillaSombra";
            }
        } else {
            // Objetos consumibles normales
            if (nombreObjeto.contains("vida")) {
                nombreMetodo = "pocionVida";
            } else if (nombreObjeto.contains("velocidad")) {
                nombreMetodo = "pocionVelocidad";
            } else if (nombreObjeto.contains("comida")) {
                nombreMetodo = "comidaCurativa";
            } else if (nombreObjeto.contains("maná") || nombreObjeto.contains("mana")) {
                nombreMetodo = "pocionMana";
            } else if (nombreObjeto.contains("carne seca")) {
                nombreMetodo = "carneSeca";
            } else if (nombreObjeto.contains("elixir")) {
                nombreMetodo = "elixirPoder";
            }
        }

        return nombreMetodo;
    }

    //Este metodo es el que gestiona las acciones de los personajes y enemigos en cada turno
    //Recibe como parametros los arrays de personajes y enemigos
    //El metodo recorre el array list del orden de los turnos y ejecuta la accion correspondiente
    //Si el personaje o enemigo esta vivo, va a atacar a un personaje random
    public static void turnoActual(Personaje[] personajes, Enemigo enemigo) {
        ArrayList<Object> ordenTurnos = obtenerOrdenTurnos(personajes, enemigo);

        //Recorre el array list
        for (Object combatiente : ordenTurnos) {
            //SI el objeto que se esta recorriendo es un personaje o un enemigo
            if (combatiente instanceof Personaje) {
                Personaje p = (Personaje) combatiente;
                //Si esta vivo hace la accion
                if (p.isVivo()) {
                    accionPersonaje(p, enemigo);
                }
                if (!enemigo.isVivo()) {
                    break;
                }
            } else if (combatiente instanceof Enemigo) {
                Enemigo e = (Enemigo) combatiente;
                if (e.isVivo()) {
                    //Accion del enemigo
                    accionEnemigo(e, personajes);
                }
            }
        }
    }


    //Verifica si tiene mana suficiente para usar la habilidad
    public static boolean tieneMana(Personaje p, Habilidad habilidad) {
        return p.getMana() >= habilidad.getCosteMana();
    }


    //Accion del enemigo, ataca a un personaje random, si el personaje muere se pone su estado a muerto
    public static void accionEnemigo(Enemigo e, Personaje[] personajes) {
        System.out.println("Es el turno del enemigo " + e.getNombre() + ", ataca a un personaje!");
        //Elige un personaje random para atacar, si no esta vivo vuelve a elegir otro personaje
        Random rand = new Random();
        Personaje objetivo;
        do {
            objetivo = personajes[rand.nextInt(personajes.length)];
        } while (!objetivo.isVivo());

        System.out.println(e.getNombre() + " ataca a " + objetivo.getNombre());
        int vida_quitar = e.getAtaque();
        System.out.println("Ha quitado " + vida_quitar + " de vida a " + objetivo.getNombre());
        //Quita la vida del personaje
        objetivo.setVida(objetivo.getVida() - vida_quitar);
        if (objetivo.getVida() <= 0) {
            objetivo.setVivo(false);
            System.out.println(objetivo.getNombre() + " ha muerto!");
        }
    }


    public static boolean comporbarMuerte(Enemigo e) {
        if (e.getVida() <= 0) {
            e.setVivo(false);
            System.out.println(e.getNombre() + " ha muerto!");
            return true;
        } else return false;
    }



    //Esto es el menu de acciones del personaje en  el combate contra enemigos
    public static void accionPersonaje(Personaje p, Enemigo enemigo) {


        int continuar = 0;
        do {
            System.out.println("Es el turno de " + p.getNombre() + ", ¿que desea hacer?");
            System.out.println("1. Atacar");
            System.out.println("2. Habilidades");
            System.out.println("3. Objetos");
            System.out.println("4. Informacion");
            TextConsoleWindow.printDivider();
            int decision = new Scanner(System.in).nextInt();
            switch (decision) {

                //Ataque normal sin maná,esto siempre estara disponible, y hace el daño basico del personaje, sin bonificaciones
                case 1:
                    System.out.println(p.getNombre() + " ataca!");
                    int vida_quitar = p.getAtaque();
                    System.out.println("Has quitado " + vida_quitar + " de vida al enemigo");
                    TextConsoleWindow.printDivider();
                    //Quita la vida del enemigo
                    enemigo.setVida(enemigo.getVida() - vida_quitar);
                    if (comporbarMuerte(enemigo)) {
                        continuar = 1;
                        break;
                    }
                    System.out.println("El enemigo tiene " + enemigo.getVida() + " de vida");
                    continuar = 1;
                    break;


                case 2:
                    // Menú de habilidades
                    List<Habilidad> habilidades = p.getHabilidades();
                    int habilidad_decision;
                    boolean habilidadValida = false;

                    do {
                        //Elige la habilidad que quieres usar
                        System.out.println("Elige una habilidad:");
                        for (int i = 0; i < habilidades.size(); i++) {
                            Habilidad habilidad = habilidades.get(i);
                            System.out.println((i + 1) + " - " + habilidad.getNombre() + " (Coste de maná: " + habilidad.getCosteMana() + ")" + "(Daño al enemigo: " + (habilidad.getDanyo() + p.getAtaque()) + ")");
                        }
                        System.out.println("0 - Cancelar y volver al menú principal");

                        Scanner sc = new Scanner(System.in);
                        if (sc.hasNextInt()) {
                            habilidad_decision = sc.nextInt();
                            if (habilidad_decision == 0) {
                                System.out.println("Has cancelado la seleccion de habilidad");
                                break;
                            }
                            habilidad_decision -= 1;


                            if (habilidad_decision >= 0 && habilidad_decision < habilidades.size()) {
                                Habilidad habilidadSeleccionada = habilidades.get(habilidad_decision);
                                //Comprueba si tienes mana suficiente para usar la habilidad
                                if (tieneMana(p, habilidadSeleccionada)) {

                                    // Pilla el nombre de la habilidad y lo formatea para que sea el nombre del metodo
                                    //Para que quede asi : Bola de fuego -> bolaDeFuego
                                    // o asi : Golpe poderoso -> golpePoderoso
                                    String[] palabras = habilidadSeleccionada.getNombre().toLowerCase().split(" ");
                                    StringBuilder nombreMetodoBuilder = new StringBuilder(palabras[0]);
                                    for (int i = 1; i < palabras.length; i++) {
                                        //Coge cada palabra y la pone en mayuscula la primera letra, menos la primera palabra
                                        nombreMetodoBuilder.append(Character.toUpperCase(palabras[i].charAt(0)))
                                                .append(palabras[i].substring(1));
                                    }

                                    String nombreMetodo = nombreMetodoBuilder.toString();
                                    try {
                                        //Obtiene un metodo de la clase habilidad por el nombre
                                        java.lang.reflect.Method metodo;

                                        metodo = Habilidad.class.getMethod(nombreMetodo, Personaje.class, Enemigo.class);
                                        //Se ejecuta el metodo que queriamos sobre el personaje y enemigo
                                        metodo.invoke(habilidadSeleccionada, p, enemigo);
                                        //Se resta el mana del personaje
                                        p.setMana(p.getMana() - habilidadSeleccionada.getCosteMana());
                                        continuar = 1;
                                        habilidadValida = true;
                                        if (comporbarMuerte(enemigo)) {
                                            continuar = 1;
                                            break;

                                        }
                                        System.out.println("El " + enemigo.getNombre() + " tiene " + enemigo.getVida() + " de vida");
                                    } catch (Exception e) {
                                        System.out.println("No se pudo ejecutar el método: " + nombreMetodo);
                                        System.out.println(e);
                                    }
                                } else {
                                    System.out.println("No tienes suficiente maná para usar esa habilidad. Elige otra.");
                                    System.out.println("Maná actual: " + p.getMana() + ", Maná necesario: " + habilidadSeleccionada.getCosteMana());

                                }
                            } else {
                                System.out.println("Selección inválida. Intenta de nuevo.");
                            }
                        } else {
                            System.out.println("Entrada inválida. Intenta de nuevo.");
                            sc.next(); // Limpia entrada incorrecta
                        }
                    } while (!habilidadValida);
                    break;


                case 3:
                    System.out.println("Menu de objetos");

                    List<Objeto> inventario = InventarioGlobal.getInventarioGlobal();
                    if (inventario.isEmpty()) {
                        System.out.println("El inventario esta vacio");
                        break;
                    } else {
                        System.out.println("Elige un objeto para usar");
                        for (int i = 0; i < inventario.size(); i++) {
                            Objeto objeto = inventario.get(i);
                            System.out.println((i + 1) + "- " + objeto.toString());
                        }
                        System.out.println("0 - Volver al menú anterior");
                        Scanner sc = new Scanner(System.in);
                        int decisionObjeto = sc.hasNextInt() ? sc.nextInt() : -1;
                        if (decisionObjeto == 0) {
                            System.out.println("Has vuelto al menú anterior.");
                            break; // Sale del menu de objetos y vuelve al menu principal del turno
                        }
                        decisionObjeto -= 1;
                        if (decisionObjeto >= 0 && decisionObjeto < inventario.size()) {
                            Objeto objetoSeleccionado = inventario.get(decisionObjeto);
                            String nombreMetodo = getString(objetoSeleccionado);
                            if (!nombreMetodo.isEmpty()) {
                                try {
                                    java.lang.reflect.Method metodo;
                                    if (objetoSeleccionado instanceof Armaduras) {
                                        metodo = Armaduras.class.getMethod(nombreMetodo, Personaje.class);
                                    } else if (objetoSeleccionado instanceof Armas) {
                                        metodo = Armas.class.getMethod(nombreMetodo, Personaje.class);
                                    } else {
                                        metodo = Objeto.class.getMethod(nombreMetodo, Personaje.class);
                                    }
                                    metodo.invoke(objetoSeleccionado, p);
                                    System.out.println("Has usado el objeto: " + objetoSeleccionado.getNombre());
                                    inventario.remove(decisionObjeto);
                                } catch (Exception e) {
                                    System.out.println("No se pudo ejecutar el método: " + nombreMetodo);
                                    System.out.println(e);
                                }
                            }
                            continuar = 1;
                            break;
                        } else {
                            System.out.println("Selección inválida.");
                            break;
                        }
                    }

                case 4:
                    System.out.println(p);
                    break;


                default:
                    System.out.println("Opcion no valida, elige otra");
                    break;
            }
        } while (continuar == 0);
    }


}