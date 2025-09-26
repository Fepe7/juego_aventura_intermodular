import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GestorPersonajesJSON {

    private static final String FILE_NAME = "personajes.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public GestorPersonajesJSON() {
        // Configurar ObjectMapper para que el JSON sea legible (indentado)
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }



    // Método para guardar una lista de personajes en un archivo JSON
    public void guardarPersonajes(List<PersonajeRPG> personajes) {
        try {
            objectMapper.writeValue(new File(FILE_NAME), personajes);
            System.out.println("Personajes guardados en " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error al guardar personajes: " + e.getMessage());
        }
    }

    // Método para cargar una lista de personajes desde un archivo JSON
    public List<PersonajeRPG> cargarPersonajes() {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Archivo de personajes no encontrado o vacío. Creando lista vacía.");
            return new ArrayList<>();
        }
        try {
            // Lee un array de objetos JSON y lo convierte en una lista de PersonajeRPG
            return Arrays.asList(objectMapper.readValue(file, PersonajeRPG[].class));
        } catch (IOException e) {
            System.err.println("Error al cargar personajes: " + e.getMessage());
            return new ArrayList<>(); // Retorna una lista vacía en caso de error
        }
    }

    public static void main(String[] args) {
        GestorPersonajesJSON gestor = new GestorPersonajesJSON();
        Scanner scanner = new Scanner(System.in);
        List<PersonajeRPG> personajes = gestor.cargarPersonajes();

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú de Personajes RPG ---");
            System.out.println("1. Crear nuevo personaje");
            System.out.println("2. Ver todos los personajes");
            System.out.println("3. Guardar y Salir");
            System.out.print("Elige una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Nivel: ");
                    int nivel = Integer.parseInt(scanner.nextLine());
                    System.out.print("Salud: ");
                    int salud = Integer.parseInt(scanner.nextLine());
                    System.out.print("Clase: ");
                    String clase = scanner.nextLine();
                    personajes.add(new PersonajeRPG(nombre, nivel, salud, clase));
                    System.out.println("Personaje añadido.");
                    break;
                case "2":
                    if (personajes.isEmpty()) {
                        System.out.println("No hay personajes guardados.");
                    } else {
                        System.out.println("\n--- Lista de Personajes ---");
                        personajes.forEach(System.out::println);
                    }
                    break;
                case "3":
                    gestor.guardarPersonajes(personajes);
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
        scanner.close();
    }
}