
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


        for (int i = 0; i < personajesPartida.length; i++) {
            System.out.println(personajesPartida[i]);
        }

        GuardarDatos guardarDatos = new GuardarDatos();
        GuardarDatos.guardarPersonajes(personajesPartida);

    }
}



