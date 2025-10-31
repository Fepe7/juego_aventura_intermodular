package Juego_nuevo;

import java.util.ArrayList;
import java.util.List;

public class Entidad {


    protected int vida;
    protected int ataque;
    protected int velocidad;
    protected String nombre;
    private boolean vivo;
    private int mana;


    //Constructor para los enemigos
    Entidad(String nombre, int vida, int ataque, int velocidad) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.velocidad = velocidad;
        this.vivo = true;
    }

    //Para los personajes
    Entidad (String nombre, int vida, int ataque, int velocidad, int mana, List<Habilidad> habilidades) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.velocidad = velocidad;
        this.vivo = true;
        this.mana = mana;
    }




    public  int getVida() {
        return vida;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }



}