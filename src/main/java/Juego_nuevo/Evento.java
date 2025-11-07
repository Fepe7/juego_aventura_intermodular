package Juego_nuevo;


import java.lang.reflect.Method;

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
    public static void ejecutarEvento(Evento evento){

        if (evento.isCompletado()){
            System.out.println("El evento ya ha sido completado.");
            System.out.println("La sala esta vacia ahora.");
        }else {
            try {
                Method metodoEjecuta = Eventos.class.getMethod(evento.getEventoNombre());
                metodoEjecuta.invoke(null);
                evento.setCompletado(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}