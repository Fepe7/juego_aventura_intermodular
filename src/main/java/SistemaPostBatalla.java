import java.util.*;

public class SistemaPostBatalla {

    private static final Random random = new Random();

    public static void opcionesPostBatalla(Personaje[] personajes, ArrayList<Objeto> inventarioGlobal) {
        Scanner scanner = new Scanner(System.in);
        boolean opcionValida = false;

        while (!opcionValida) {
            System.out.println("Has derrotado al enemigo. ¿Qué deseas hacer?");
            System.out.println("1. Ir a la HOGUERA (Restaura toda la vida de tus personajes)");
            System.out.println("2. Explorar el CAMPAMENTO (Puedes encontrar objetos útiles o eventos)");

            int eleccion = scanner.nextInt();

            switch (eleccion) {
                case 1:
                    irAHoguera(personajes);
                    opcionValida = true;
                    break;
                case 2:
                    explorarCampamento(personajes, inventarioGlobal);
                    opcionValida = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elige 1 o 2.");
            }
        }
    }

    private static void irAHoguera(Personaje[] personajes) {
        System.out.println("\n=== HOGUERA ===");
        System.out.println("Te acercas a la hoguera reconfortante...");
        System.out.println("El calor de las llamas restaura las fuerzas de tu equipo.");

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

    private static void explorarCampamento(Personaje[] personajes, ArrayList<Objeto> inventarioGlobal) {
        System.out.println("\n=== CAMPAMENTO ===");
        System.out.println("Decides explorar el campamento en busca de recursos...");

        int resultado = random.nextInt(100);

        if (resultado < 20) {
            eventoLibreria(personajes);
        } else if (resultado < 35) {
            eventoAlmacen(inventarioGlobal);
        } else if (resultado < 45) {
            eventoCueva(personajes, inventarioGlobal);
        } else if (resultado < 65) {
            eventoCampamentoViajeros(inventarioGlobal);
        } else if (resultado < 75) {
            eventoBosque(personajes, inventarioGlobal);
        } else if (resultado < 85) {
            eventoLago(inventarioGlobal);
        } else {
            System.out.println("Exploras el campamento pero no encuentras nada de valor.");
            System.out.println("Al menos el lugar parece seguro para descansar un poco.");
        }
    }

    private static void eventoLibreria(Personaje[] personajes) {
        System.out.println("\nLIBRERÍA ABANDONADA");
        System.out.println("Entre los escombros encuentras una pequeña librería...");
        System.out.println("Un libro antiguo brilla con energía mágica.");
        System.out.println("Al leerlo, todos tus magos sienten un aumento en su poder mágico!");

        for (Personaje p : personajes) {
            if (p.isVivo()) {
                p.setMana(p.getMana() + 20);
                System.out.println(p.getNombre() + " gana +20 de maná!");
            }
        }
    }

    private static void eventoAlmacen(ArrayList<Objeto> inventarioGlobal) {
        System.out.println("\nALMACÉN ABANDONADO");
        System.out.println("Encuentras un viejo almacén con cajas polvorientas...");

        int tipoPocion = random.nextInt(3);
        Objeto pocion;

        switch (tipoPocion) {
            case 0:
                pocion = new Objeto("Poción de vida", "Restaura 50 puntos de vida");
                System.out.println("¡Encuentras una Poción de vida!");
                break;
            case 1:
                pocion = new Objeto("Poción de maná", "Restaura 30 puntos de maná");
                System.out.println("¡Encuentras una Poción de maná!");
                break;
            default:
                pocion = new Objeto("Poción de velocidad", "Aumenta la velocidad en 20 puntos");
                System.out.println("¡Encuentras una Poción de velocidad!");
                break;
        }

        InventarioGlobal.agregarAlInventarioGlobal(pocion);
        System.out.println("La poción ha sido añadida a tu inventario.");
    }

    private static void eventoCueva(Personaje[] personajes, ArrayList<Objeto> inventarioGlobal) {
        System.out.println("\n CUEVA MISTERIOSA");
        System.out.println("Una cueva oscura se abre ante ti...");

        if (random.nextBoolean()) {
            System.out.println("¡La cueva parece segura!");
            System.out.println("En su interior encuentras un arma antigua...");

            Armas armaEncontrada = generarArmaAleatoria();
            InventarioGlobal.agregarAlInventarioGlobal(armaEncontrada);
            System.out.println("¡Has encontrado: " + armaEncontrada.getNombre() + "!");
        } else {
            System.out.println("¡Un monstruo salta desde las sombras!");
            System.out.println("¡Es un Ogro de las Cavernas!");

            Enemigos ogro = new Enemigos("Ogro de las Cavernas", 150, 25, 15, "Un ogro furioso que protege la cueva");

            System.out.println("\n¡COMBATE SORPRESA!");
            while (ogro.isVivo() && algunPersonajeVivo(personajes)) {
                Juego.turnoActual(personajes, ogro);
            }

            if (!ogro.isVivo()) {
                System.out.println("¡Has derrotado al Ogro! Como recompensa encuentras su tesoro...");
                Objeto tesoro = new Objeto("Elixir de poder", "Aumenta el ataque en 10 puntos");
                InventarioGlobal.agregarAlInventarioGlobal(tesoro);
            }
        }
    }

    private static void eventoCampamentoViajeros(ArrayList<Objeto> inventarioGlobal) {
        System.out.println("\n CAMPAMENTO DE VIAJEROS");
        System.out.println("Encuentras un grupo de viajeros amistosos...");
        System.out.println("Un viajero veterano se acerca: '¡Hola aventurero! Tengo algo que podría serte útil.'");

        Armaduras armaduraPrestada = generarArmaduraAleatoria();
        InventarioGlobal.agregarAlInventarioGlobal(armaduraPrestada);
        System.out.println("El viajero te presta: " + armaduraPrestada.getNombre());
        System.out.println("'¡Úsala sabiamente en tu aventura!'");
    }

    private static void eventoBosque(Personaje[] personajes, ArrayList<Objeto> inventarioGlobal) {
        System.out.println("\n BOSQUE ENCANTADO");
        System.out.println("El bosque brilla con una luz mágica...");

        if (random.nextBoolean()) {
            System.out.println("Un hada del bosque aparece y te bendice con su magia curativa.");
            System.out.println("¡Te obsequia 2 pociones de vida!");

            for (int i = 0; i < 2; i++) {
                Objeto pocion = new Objeto("Poción de vida", "Restaura 50 puntos de vida");
                InventarioGlobal.agregarAlInventarioGlobal(pocion);
            }
        } else {
            System.out.println("¡La magia del bosque ha atraído a un Ogro Místico!");

            Enemigos ogroMistico = new Enemigos("Ogro Místico", 120, 20, 20, "Un ogro imbuido con magia del bosque");

            System.out.println("\n¡COMBATE!");
            while (ogroMistico.isVivo() && algunPersonajeVivo(personajes)) {
                Juego.turnoActual(personajes, ogroMistico);
            }

            if (!ogroMistico.isVivo()) {
                System.out.println("El Ogro Místico se desvanece dejando energía mágica...");
                System.out.println("¡Todos tus personajes ganan +10 de maná!");
                for (Personaje p : personajes) {
                    if (p.isVivo()) {
                        p.setMana(p.getMana() + 10);
                    }
                }
            }
        }
    }

    private static void eventoLago(ArrayList<Objeto> inventarioGlobal) {
        System.out.println("\n LAGO SECRETO");
        System.out.println("Descubres un hermoso lago cristalino...");
        System.out.println("En el fondo brilla algo... ¡Es un tesoro!");

        Objeto tesoro = generarObjetoAleatorio();
        InventarioGlobal.agregarAlInventarioGlobal(tesoro);
        System.out.println("¡Has encontrado: " + tesoro.getNombre() + "!");
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
                {"Túnica Mágica", "Aumenta vida y maná en 15 (Solo Mago)"},
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

    private static boolean algunPersonajeVivo(Personaje[] personajes) {
        for (Personaje p : personajes) {
            if (p.isVivo()) {
                return true;
            }
        }
        return false;
    }
}