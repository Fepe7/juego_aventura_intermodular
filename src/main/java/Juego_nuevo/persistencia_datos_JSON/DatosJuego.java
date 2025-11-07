package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Evento;
import Juego_nuevo.Objeto;
import Juego_nuevo.Personaje;

import java.util.ArrayList;

/**
 * La clase <strong>Partida</strong> es un <strong>contenedor</strong> a la hora de la persistencia de datos, para
 * centralizar el guardado y carga del archivo JSON.
 * <p>
 * Esta tiene como atributos 2 arrays y un entero.
 * <p>
 * Los arrays son uno de Personaje y otro de Objeto, donde estarán los personajes y los objetos respectivamente.
 * <p>
 * El entero es la semilla que se usa para generar el mapa.
 * <p>
 * Al guardar, se guarda la partida, para centralizarlo.
 * <p>
 * A la hora de cargar, devolverá una partida, por eso he hecho getters de ambos atributos, para que se obtenga las
 * listas y, si se quiere pasar a {@link java.util.List} se hace con {@code Arrays.asList(array)} o si se quiere un
 * {@link java.util.ArrayList} se hace con {@code ArrayList<>(Arrays.asList(array))}, es decir, un parseo a ArrayList.
 */
public class DatosJuego {
    private Personaje[] personajes;
    private ArrayList<Objeto> objetos;
    private int seed;
    private Evento[][] eventos;

    public DatosJuego(Personaje[] personajes, ArrayList<Objeto> objetos, int seed, Evento[][] eventos) {
        this.personajes = personajes;
        this.objetos = objetos;
        this.seed = seed;
        this.eventos = eventos;
    }

    public Personaje[] getPersonajes() {
        return personajes;
    }

    public ArrayList<Objeto> getObjetos() {
        return objetos;
    }

    public int getSeed() {
        return seed;
    }

    public Evento[][] getEventos() {
        return eventos;
    }
}
