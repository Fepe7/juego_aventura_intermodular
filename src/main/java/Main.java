import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        //Creas los 4 personajes iniciales
        Personaje personaje1 = Personaje.CrearPersonaje();
        Personaje personaje2 = Personaje.CrearPersonaje();
        Personaje personaje3 = Personaje.CrearPersonaje();
        Personaje personaje4 = Personaje.CrearPersonaje();


        //Los metes en un array, quiero que cuando se enfrente a un enemigo, elija elija una posicion del array aleatoriamente
        //dando una sensacion de combate
        Personaje[] personajesPartida = {personaje1, personaje2, personaje3, personaje4};

        //Creo el inventario global
        ArrayList<Objeto> inventarioGlobal = new ArrayList<>();

        //Añado objetos al inventario global, son los objetos iniciales
        InventarioGlobal.agregarAlInventarioGlobal(new Objeto("Pocion de vida", "Restaura 50 puntos de vida"), inventarioGlobal);
        InventarioGlobal.agregarAlInventarioGlobal(new Objeto("Pocion de mana", "Restaura 30 puntos de mana"), inventarioGlobal);



        while (personajesPartida[0].isVivo() || personajesPartida[1].isVivo() || personajesPartida[2].isVivo() || personajesPartida[3].isVivo()) {
            Enemigos enemigo = new Enemigos("Goblin", 100, 15, 10, "Un goblin salvaje chill");
            System.out.println("Un " + enemigo.getNombre() + " ha aparecido!");
            while (enemigo.isVivo() && (personajesPartida[0].isVivo() || personajesPartida[1].isVivo() || personajesPartida[2].isVivo() || personajesPartida[3].isVivo())) {
                Juego.turnoActual(personajesPartida, enemigo);
            }
            if (!enemigo.isVivo()) {
                System.out.println("Has derrotado al " + enemigo.getNombre() + "!");
                //Aqui puedes añadir logica para que el enemigo suelte objetos o experiencia
            } else {
                System.out.println("Todos tus personajes han muerto. Fin del juego.");
                break;
            }
        }



        for (
                int i = 0;
                i < personajesPartida.length; i++) {
            System.out.println(personajesPartida[i]);
        }

        GuardarDatos guardarDatos = new GuardarDatos();
        GuardarDatos.guardarPersonajes(personajesPartida);


    }
}



