package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Eventos.Evento;

/**
 * El record <strong>{@link DatosMap}</strong> es el contenedor de los datos que contiene el mapa, tanto la seed como la
 * matriz de {@link Evento}
 */
public record DatosMap(int seed, Evento[][] eventos) {}