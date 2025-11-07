package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Evento;
import Juego_nuevo.Objeto;
import Juego_nuevo.Personaje;

import java.util.ArrayList;
import java.util.List;

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
 * listas y, si se quiere pasar a {@link List} se hace con {@code Arrays.asList(array)} o si se quiere un
 * {@link ArrayList} se hace con {@code ArrayList<>(Arrays.asList(array))}, es decir, un parseo a ArrayList.
 * <p>
 * <strong>QUE ES {@link Record}</strong>
 * <p>
 * {@link Record} es una clase que te crea los getters y setters automáticamente para los atributos de la clase.
 */
public record DatosJuego(Personaje[] personajes, ArrayList<Objeto> objetos, int seed, Evento[][] eventos) {}
