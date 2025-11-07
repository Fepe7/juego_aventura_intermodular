package Juego_nuevo.persistencia_datos_JSON;

import Juego_nuevo.Evento;

public class DatosMap {
    private int seed;
    private Evento[][] eventos;

    public DatosMap(int seed, Evento[][] eventos) {
        this.seed = seed;
        this.eventos = eventos;
    }

    public int getSeed() {
        return seed;
    }

    public Evento[][] getEventos() {
        return eventos;
    }
}