package Juego_nuevo.mapa;

import java.util.ArrayList;
import java.util.Scanner;

import Juego_nuevo.Eventos.Evento;
import Juego_nuevo.Eventos.Eventos;
import Juego_nuevo.Entidades.Personaje;

public class Room extends MapTile{

    private boolean visitada = false;

    ArrayList<Room> connections;

    static int totalRooms = 0;

    private Evento evento;

    public Room() {
        super("?", totalRooms);
        totalRooms++;
        //Agregar un evento aleatorio a la sala
        this.evento = Eventos.generarEventoAleatorio();

    }


    //Activa el evento de la sala
    public void activarEvento(Personaje[] personajes, Mapa mapa, Scanner scanner){
        this.visitada = true;
        if(evento != null){
            //La comprobacion de si el evento ya ha sido completado se hace dentro del metodo ejecutarEvento
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
        if(this.isVisitada() || this.getGeneratedOrder()== 0){
            simboloPrintear = "X";
        }else{
            simboloPrintear = "?";
        }
        return "["+simboloPrintear+"]";
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

