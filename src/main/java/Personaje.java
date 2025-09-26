import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Personaje extends Entidad {


    private TiposClases idClase;
    private int nivel;
    private static List<Objeto> inventario = new ArrayList<>();
    private static List<Objeto> objetosEquipados = new ArrayList<>();
    private int vidaMaxima;


    Personaje(String nombre) {
        super(nombre);
    }


    //Esto es un metodo estatico que crea un personaje segun la clase que se le pase por parametro
    //El codigo de la clase es el identificador de la clase
    //El metodo devuelve un objeto de la clase Personaje con los atributos iniciales
    //Tambien crea las habilidades que tiene cada clase
    public static Personaje CrearPersonaje() {
        Scanner sc = new Scanner(System.in);

        //Le pide al usuario el nombre del personaje por consola
        System.out.println("Pon el nombre de tu personaje:");
        String nombre = sc.nextLine();


        //Menu de clases de personaje
        System.out.println("Elige una clase de personaje:");
        System.out.println("1. Guerrero");
        System.out.println("2. Mago");
        System.out.println("3. Arquero");
        System.out.println("4. Asesino");

        //Eliges la clase del personaje por consola
        int idClase = sc.nextInt();


        //Creas el objeto de personaje con los atributos segun la clase elegida
        Personaje personaje = new Personaje(nombre);

        switch (idClase) {
            //Guerrero
            case 1:
                personaje.setClase(TiposClases.GUERRERO);
                personaje.setVida(120);
                personaje.setMana(30);
                personaje.setVelocidad(25);
                personaje.setAtaque(40);
                personaje.setVivo(true);
                personaje.setNivel(1);
                personaje.setVida_maxima(200);
                personaje.agregarHabilidad(new Habilidad("Golpe Poderoso", 10, 50, "Consume 10 de maná y hace un golpe que hace +15 de daño "));
                personaje.agregarHabilidad(new Habilidad("Escudo de fuego", 15, TipoHabilidad.APOYO, "Consume 15 de maná y obtiene +10 de ataque durante el combate"));
                break;
            //Mago
            case 2:
                personaje.setClase(TiposClases.MAGO);
                personaje.setVida(70);
                personaje.setMana(100);
                personaje.setVelocidad(35);
                personaje.setAtaque(25);
                personaje.setVivo(true);
                personaje.setNivel(1);
                personaje.agregarHabilidad(new Habilidad("Bola de fuego", 20, 35, "Consume 20 de maná e inflinge +35 de daño"));
                personaje.agregarHabilidad(new Habilidad("Palabra curativa", 15, TipoHabilidad.APOYO, "Consume 15 de maná y se cura 20 de vida"));
                break;
            //Guerrero
            case 3:
                personaje.setClase(TiposClases.ARQUERO);
                personaje.setVida(90);
                personaje.setMana(100);
                personaje.setVelocidad(45);
                personaje.setAtaque(30);
                personaje.setVivo(true);
                personaje.setNivel(1);
                personaje.agregarHabilidad(new Habilidad("Flecha precisa", 15, 20, "Consume 15 de maná y lanza una flecha e inflinge +20 de daño"));
                personaje.agregarHabilidad(new Habilidad("Trozo de carne", 0, TipoHabilidad.APOYO, "Consume 10 de maná y restaura 20 de vida"));
                break;
            //Asesino
            case 4:
                personaje.setClase(TiposClases.ASESINO);
                personaje.setVida(60);
                personaje.setMana(30);
                personaje.setVelocidad(65);
                personaje.setAtaque(25);
                personaje.setVivo(true);
                personaje.setNivel(1);
                personaje.agregarHabilidad(new Habilidad("Ataque furtivo", 15, 25, "Consume 10 de maná y ataca por detras e inflinge +25 de daño"));
                personaje.agregarHabilidad(new Habilidad("Golpe venenoso", 10, TipoHabilidad.APOYO, "Consume 10 de maná e inflinge daño normal y aplica veneno (5 de daño extra por 3 turnos si quieres, o lo podemos dejar por 15 de daño mas)"));
                break;
        }
        return personaje;
    }


    @Override
    public String toString() {
        return "El personaje " + nombre + " de la clase " + idClase + " tiene " + vida + " de vida, " + ataque + " de ataque y " + velocidad + " de velocidad.";
    }


    public void setClase(TiposClases idClase) {
        this.idClase = idClase;
    }

    public TiposClases getClase() {
        return idClase;
    }

    public static void agregarAlInventario(Objeto objeto) {
        inventario.add(objeto);
    }

    public static List<Objeto> getInventario() {
        return inventario;
    }


    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getVida_maxima() {
        return vidaMaxima;
    }

    public void setVida_maxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }
}




