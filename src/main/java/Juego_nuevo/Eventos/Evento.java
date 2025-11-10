package Juego_nuevo.Eventos;


import Juego_nuevo.Entidades.Personaje;
import Juego_nuevo.InventarioGlobal;
import Juego_nuevo.Main;
import Juego_nuevo.mapa.Mapa;

import java.lang.reflect.Method;
import java.util.Scanner;

//Se crea la clase Evento para representar eventos en el juego, esto genera un objeto para ver si esta completado o no
public class Evento {
    private final String eventoNombre;
    private boolean completado;


    //Coge el nombre del evento para identificarlo y llamarlo despues mediante reflexion
    public String getEventoNombre() {
        return eventoNombre;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }


    public Evento(String eventoNombre) {
        this.eventoNombre = eventoNombre;
        this.completado = false;
    }


    //Ejecuta el evento llamando al metodo correspondiente en la clase Eventos mediante reflexion
    public static void ejecutarEvento(Evento evento, Personaje[] personajes, Mapa mapa, Scanner scanner) {

        if (evento.isCompletado()) {
            System.out.println("El evento ya ha sido completado.");
            System.out.println("La sala esta vacia ahora.");
        } else {
            try {
                if ("meterParty".equals(evento.getEventoNombre())) {
                    Eventos.meterParty(personajes);
                    //Actualiza el array
                    personajes = Main.personajesPartida;

                    evento.setCompletado(true);
                    Main.guardadPartida(personajes, InventarioGlobal.getInventarioGlobal(), mapa.getSeed());
                    return;
                }

                //Caso especial para generarEnemigoAleatorio que requiere Personaje[]
                if ("generarEnemigoAleatorio".equals(evento.getEventoNombre())) {
                    java.lang.reflect.Method metodo = Eventos.class.getMethod(evento.getEventoNombre(), Personaje[].class, Scanner.class);
                    metodo.invoke(null, (Object) personajes, scanner);
                    evento.setCompletado(true);
                    Main.guardadPartida(personajes, InventarioGlobal.getInventarioGlobal(), mapa.getSeed());
                    return;
                }

                //Primero intenta ejecutar el evento sin parametros
                try {
                    java.lang.reflect.Method metodo = Eventos.class.getMethod(evento.getEventoNombre());
                    metodo.invoke(null);
                } catch (NoSuchMethodException e1) {
                    // Si falla, intentar con parámetros de Personaje[]
                    try {
                        java.lang.reflect.Method metodo = Eventos.class.getMethod(evento.getEventoNombre(), Personaje[].class);
                        metodo.invoke(null, (Object) personajes);
                    } catch (NoSuchMethodException e2) {
                        // Si tampoco existe, lanzar excepción
                        throw new NoSuchMethodException("No se encontró el método: " + evento.getEventoNombre());
                    }
                }

                evento.setCompletado(true);
                Main.guardadPartida(personajes, InventarioGlobal.getInventarioGlobal(), mapa.getSeed());

            } catch (Exception e) {
                System.out.println("Error al ejecutar el evento: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
