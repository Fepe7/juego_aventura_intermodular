import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Personaje extends Entidad {

    private TiposClases idClase;
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
        String nombre;

        do {
            System.out.println("Pon el nombre de tu personaje:");
            nombre = sc.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Inténtalo de nuevo.");
            }
        } while (nombre.isEmpty());

        int idClase;

        do {
            //Menu de clases de personaje
            System.out.println("Elige una clase de personaje:");
            System.out.println("1. Guerrero");
            System.out.println("2. Mago");
            System.out.println("3. Arquero");
            System.out.println("4. Asesino");

            while (!sc.hasNextInt()) {
                System.out.println("Por favor, introduce un número válido.");
                sc.next();
            }

            idClase = sc.nextInt();

            if (idClase < 1 || idClase > 4) {
                System.out.println("Clase inválida. Elige un número entre 1 y 4.");
            }
        } while (idClase < 1 || idClase > 4);

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
                personaje.setVida_maxima(120);
                personaje.agregarHabilidad(new Habilidad("Golpe Poderoso", 10, 50, "Consume 10 de maná y hace un golpe que hace +15 de daño "));
                personaje.agregarHabilidad(new Habilidad("Rugido Batalla", 15, TipoHabilidad.APOYO, "Consume 15 de maná y obtiene +10 de ataque durante el combate"));
                break;
            //Mago
            case 2:
                personaje.setClase(TiposClases.MAGO);
                personaje.setVida(70);
                personaje.setMana(100);
                personaje.setVelocidad(35);
                personaje.setAtaque(25);
                personaje.setVivo(true);
                personaje.setVida_maxima(70);
                personaje.agregarHabilidad(new Habilidad("Bola de fuego", 20, 35, "Consume 20 de maná e inflinge +35 de daño"));
                personaje.agregarHabilidad(new Habilidad("Palabra curativa", 15, TipoHabilidad.APOYO, "Consume 15 de maná y se cura 20 de vida"));
                break;
            //Arquero
            case 3:
                personaje.setClase(TiposClases.ARQUERO);
                personaje.setVida(90);
                personaje.setMana(100);
                personaje.setVelocidad(45);
                personaje.setAtaque(30);
                personaje.setVivo(true);
                personaje.setVida_maxima(90);
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
                personaje.setVida_maxima(60);
                personaje.agregarHabilidad(new Habilidad("Ataque furtivo", 15, 25, "Consume 10 de maná y ataca por detras e inflinge +25 de daño"));
                personaje.agregarHabilidad(new Habilidad("Golpe venenoso", 10, TipoHabilidad.DANYO, "Consume 10 de maná e inflinge daño normal y aplica veneno (5 de daño extra por 3 turnos si quieres, o lo podemos dejar por 15 de daño mas)"));
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

    public int getVida_maxima() {
        return vidaMaxima;
    }

    public void setVida_maxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }


    // Métodos para el inventario individual
    public void equiparObjeto(Objeto objeto) {
        objetosEquipados.add(objeto);
        InventarioGlobal inventarioGlobal = new InventarioGlobal();

        // Elimina el objeto del inventario global
        InventarioGlobal.eliminarDelInventarioGlobal(objeto);

        // Llama al método correspondiente del objeto usando reflexión
        String nombreObjetoMetodo = Juego.getString(objeto);
        try {
            // Invoca el método del objeto que recibe un Personaje como parámetro
            //Esto para que se ejecute el metodo del objeto que hemos equipado
            Objeto.class.getMethod(nombreObjetoMetodo, Personaje.class).invoke(objeto, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void desequiparObjeto(Objeto objeto, Personaje p, ArrayList<Objeto> inventarioGlobal) {
        if (objetosEquipados.contains(objeto)) {
            objetosEquipados.remove(objeto);

            //Añade el objeto al inventario global
            InventarioGlobal.agregarAlInventarioGlobal(objeto, inventarioGlobal);

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


    public List<Objeto> getObjetosEquipados() {
        return objetosEquipados;
    }

}








