package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Objeto;
import Juego_nuevo.Personaje;

/**
 * La clase <strong>Partida</strong> es un <strong>contenedor</strong> a la hora de la persistencia de datos, para
 * centralizar el guardado y carga del archivo JSON.
 * <p>
 * Esta tiene como atributos 2 arrays uno de Personaje y otro de Objeto, donde estarán los personajes y
 * los objetos respectivamente.
 * <p>
 * Al guardar, se guarda la partida, para centralizarlo.
 * <p>
 * A la hora de cargar, devolverá una partida, por eso he hecho getters de ambos atributos, para que se obtenga las
 * listas y, si se quiere pasar a {@link java.util.List} se hace con {@code Arrays.asList(array)} o si se quiere un
 * {@link java.util.ArrayList} se hace con {@code ArrayList<>(Arrays.asList(array))}, es decir, un parseo a ArrayList.
 */
public class Partida {
    Personaje[] personajes;
    Objeto[] objetos;

    public Partida(Personaje[] personajes, Objeto[] objetos){
        this.personajes = personajes;
        this.objetos = objetos;
    }

    public Personaje[] getPersonajes() {
        return personajes;
    }

    public Objeto[] getObjetos() {
        return objetos;
    }
}
