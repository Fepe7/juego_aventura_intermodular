package Juego_nuevo.bbdd;

import Juego_nuevo.persistencia_datos_JSON.EstadoPartida;

import java.sql.SQLException;

/**
 * La clase <strong>{@link BBDD}</strong> es una clase que maneja lo relacionado con la BBDD.
 * <p>
 * Contiene 2 funciones esenciales, <strong>{@code crearUsuario(String nombreUsuario)}</strong> y
 * <strong>{@code actualizarBBDD(...)}</strong> la cual registra el usuario nuevo y actualiza sus estadísticas, evaluando
 * que sea el usuario correcto al que le asigne las mismas.
 */
public class BBDD {
    /**
     * La función <strong>{@code acutalizarBBDD}</strong> es la que actualiza todos los datos del usuario en cuestión.
     * <p>
     * Esta función crea obtiene la conexión y precompila el SQL en la base de datos, pasándole el String con el SQL, a
     * esta clase se le llama {@link java.sql.Statement}. Después, insertamos los valores que nos asignan al SQL y
     * ejecuta la consulta. Si diese algún fallo, véase de sintáxis u otro, tiraría una excepción {@link SQLException}
     * @param nombreUsuario El nombre del usuario que queremos actualizar
     * @param nHabitaciones El número de habitaciones por las cuales ha pasado en esa partida
     * @param nPersonajes El número de {@link Juego_nuevo.Entidades.Personaje} que hay en la party
     * @param nEnemigosMatados El número de {@link Juego_nuevo.Entidades.Enemigo} que ha matado en esa partida
     * @param nObjetosRecogidos El número de objetos que ha recogido en la partida
     */
    public static void actualizarBBDD(
        String nombreUsuario,
        int nHabitaciones,
        int nPersonajes,
        int nEnemigosMatados,
        int nObjetosRecogidos
    ) {

        final var conn = ConexionBBDD.getConnection();
        
        final var sql = "UPDATE usuarios " +
            "SET numero_habitaciones = ?, " +
            "numero_personajes = ?, " +
            "numero_enemigos_matados = ?, " +
            "numero_objetos_recogidos = ? " +
            "WHERE nombre = ?";
        
        try (final var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nHabitaciones);
            stmt.setInt(2, nPersonajes);
            stmt.setInt(3, nEnemigosMatados);
            stmt.setInt(4, nObjetosRecogidos);
            stmt.setString(5, nombreUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar el usuario.\n" + e.getMessage());
        }
    }

    /**
     * La función <strong>{@code crearUsuario()}</strong> es una función que registra al usuario en la BBDD.
     * <p>
     * Esta función obtiene una conexión y y prepara el precompilado ({@link java.sql.Statement}) para después ejecutar
     * el SQL. Después pone el nombre de usuario en el SQL y lo ejecuta.
     * @param nombreUsuario El nombre del usuario que se quiere añadir
     */
    public static void crearUsuario(String nombreUsuario) {
        final var conn = ConexionBBDD.getConnection();

        // Usamos INSERT IGNORE para evitar errores si el usuario ya existe
        final var sql = "INSERT IGNORE INTO usuarios (nombre) " +
                        "VALUES (?)";

        try (final var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar el usuario en la BBDD.\n" + e.getMessage());
        }
    }
}
