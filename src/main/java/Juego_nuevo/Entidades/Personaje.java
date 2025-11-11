package Juego_nuevo.Entidades;

import java.util.ArrayList;
import java.util.List;

import Juego_nuevo.Habilidad;
import Juego_nuevo.InventarioGlobal;
import Juego_nuevo.Juego;
import Juego_nuevo.Objetos.Armaduras;
import Juego_nuevo.Objetos.Armas;
import Juego_nuevo.Objetos.Objeto;
import Juego_nuevo.mapa.Mapa;
import Juego_nuevo.Juego;


public class Personaje extends Entidad {

    private static final List<Objeto> objetosEquipados = new ArrayList<>();
    private int vidaMaxima;
    private List<String> dialogos = new ArrayList<>();
    private final List<Habilidad> habilidades = new ArrayList<>();
    private int[] posicion;


    //Constructor de los personajes
    Personaje(String nombre, int vida, int ataque, int velocidad, int mana, List<Habilidad> habilidades, List<String> dialogos) {
        super(nombre, vida, ataque, velocidad, mana, habilidades);
    }


    @Override
    public String toString() {
        return "El personaje " + nombre + " tiene " + vida + " de vida, " + getMana() + " de mana, " + ataque + " de ataque y " + velocidad + " de velocidad.";
    }


    public int getVida_maxima() {
        return vidaMaxima;
    }

    public void setVida_maxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }


    // metodos para el inventario individual
    public void equiparObjeto(Objeto objeto) {
        String nombreObjetoMetodo = Juego.getString(objeto);

        // Verificar si se encontró un método válido
        if (nombreObjetoMetodo.isEmpty()) {
            System.out.println("No se reconoce el objeto: " + objeto.getNombre());
            return;
        }

        objetosEquipados.add(objeto);
        InventarioGlobal.eliminarDelInventarioGlobal(objeto);

        try {
            if (objeto instanceof Armaduras) {
                // Los métodos en JUego_originak.Armaduras son métodos de instancia
                Armaduras.class.getMethod(nombreObjetoMetodo, Personaje.class).invoke(objeto, this);
            } else if (objeto instanceof Armas) {
                // Los métodos en JUego_originak.Armas son estáticos
                Armas.class.getMethod(nombreObjetoMetodo, Personaje.class).invoke(null, this);
            } else {
                // Otros objetos consumibles
                Objeto.class.getMethod(nombreObjetoMetodo, Personaje.class).invoke(objeto, this);
            }
            System.out.println("Has equipado: " + objeto.getNombre());
        } catch (Exception e) {
            System.out.println("Error al equipar: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void desequiparObjeto(Objeto objeto, Personaje p) {
        if (objetosEquipados.contains(objeto)) {
            objetosEquipados.remove(objeto);

            InventarioGlobal.agregarAlInventarioGlobal(objeto);

            //Pilla el nombre del metodo que revierte los efectos del objeto
            //El nombre del metodo es "quitar" + nombre del objeto con la primera letra en mayusculas
            //Ejemplo: quitarEscudoPerfeccionado
            String nombreMetodoQuitar = "quitar" + formateoQuitarObjetos(Juego.getString(objeto));
            try {
                Objeto.class.getMethod(nombreMetodoQuitar, Personaje.class).invoke(objeto, p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //Divide el nombre del objeto en partes y le pone la primera letra de cada parte en mayusculas
    //Ejemplo: escudo perfeccionado -> EscudoPerfeccionado
    //Esto es para crear el nombre del metodo que quita los efectos del objeto
    //Se usa en el metodo desequiparObjeto
    private static String formateoQuitarObjetos(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            //Divide el string en partes usando espacios como separadores
            String[] partes = str.split("[\\s-]+");
            StringBuilder resultado = new StringBuilder();
            for (String parte : partes) {
                if (!parte.isEmpty()) {
                    resultado.append(parte.substring(0, 1).toUpperCase());
                    if (parte.length() > 1) {
                        resultado.append(parte.substring(1));
                    }
                }
            }
            return resultado.toString();
        }
    }


    // Directions: 0 ^, 1 >, 2 v, 3 <
    public void mover(Mapa mapa, int direccion) {
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        int nuevaFila = this.posicion[0] + directions[direccion][0];
        int nuevaCol = this.posicion[1] + directions[direccion][1];

        if (mapa.esMovimientoValido(nuevaFila, nuevaCol)) {
            this.posicion[0] = nuevaFila;
            this.posicion[1] = nuevaCol;
            System.out.println("Te has movido a: [" + nuevaFila + ", " + nuevaCol + "]");
        } else {
            System.out.println("Movimiento inválido. No puedes ir hacia ese lado.");
        }
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    //MEte la habilidad en la lista de habilidades del personaje
    public void agregarHabilidad(Habilidad h) {
        habilidades.add(h);
    }

    public List<String> getDialogos() {
        return dialogos;
    }

    public void setDialogos(List<String> dialogos) {
        this.dialogos = dialogos;
    }

    public List<Objeto> getObjetosEquipados() {
        return objetosEquipados;
    }

    public void setPosicion(int fila, int columna) {
        this.posicion = new int[]{fila, columna};
    }

    public int[] getPosicion() {
        return posicion;
    }
}








