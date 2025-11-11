package Juego_nuevo.mapa;

import java.util.ArrayList;
import java.util.Scanner;

import Juego_nuevo.Eventos.Evento;
import Juego_nuevo.Eventos.Eventos;
import Juego_nuevo.Entidades.Personaje;
import Juego_nuevo.Main;

public class Room extends MapTile {

    private boolean visitada = false;

    ArrayList<Room> connections;

    static int totalRooms = 0;

    private Evento evento;

    public Room() {
        super("?", totalRooms);
        totalRooms++;
        //Se agrega un evento a la habitacion
        this.evento = Eventos.generarEventoAleatorio();
    }

    //Activa el evento de la sala
    public void activarEvento(Personaje[] personajes, Mapa mapa, Scanner scanner) {
        this.visitada = true;

        // Si es la habitacion !, hacer que tenga un enemigo
        if (this.getGeneratedOrder() == getTotalRooms() - 1) {
            this.evento = new Evento("generarEnemigoAleatorio");
        }

        if (evento != null) {
            // La comprobacion de completado se hace dentro de ejecutarEvento
            Evento.ejecutarEvento(evento, personajes, mapa, scanner);
        }
    }


    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }


    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }


    @Override
    public String toString() {
        String simboloPrintear;
        if (this.isVisitada() || this.getGeneratedOrder() == 0) {
            simboloPrintear = "X";
            //La ultima sala siempre sera la de boss y tiene el simbolo de calavera
        } else if (this.getGeneratedOrder() == getTotalRooms() - 1) {
            simboloPrintear = "!";

        } else {
            simboloPrintear = "?";
        }
        return "[" + simboloPrintear + "]";
    }


    public ArrayList<Room> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<Room> connections) {
        this.connections = connections;
    }

    static public int getTotalRooms() {
        return totalRooms;
    }
}

