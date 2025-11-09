package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Evento;

/**
 * El record <strong>{@link DatosMap}</strong> es el contenedor de los datos que contiene el mapa, tanto la seed
 */
public record DatosMap(int seed, Evento[][] eventos) {}