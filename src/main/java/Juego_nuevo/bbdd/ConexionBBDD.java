package Juego_nuevo.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase <strong>{@link ConexionBBDD}</strong> es una clase para manejar la conexión de base de datos.
 * <p>
 * La clase coge 4 parámetros: <strong>Una conexión, una url, un usuario y una contraseña</strong>. Estos 5 parámetros
 * son los que después se usarán para obtener la conexión. Después hacemos un constructor privado para evitar el
 * instanciado de la clase. Después hay 2 métodos <strong>{@code getConnection()}</strong> y
 * <strong>{@code closeConnection()}</strong> los cuales se encargan de obtener y cerrar la conexión respectivamente
 */
public class ConexionBBDD {
    private static Connection conn;
    private static final String URL = "jdbc:mysql://localhost:33006/bbdd";
    private static final String USER = "root";
    private static final String PASS = "dbrootpass";

    private ConexionBBDD() {}

    /**
     * La función <strong>{@code getConnection()}</strong> es una función la cual comprueba que, si la conexión es nula
     * o la conexión está cerrada, que intente conectarse mediante el {@link DriverManager}, pasándole los atributos
     * estáticos de la clase. Si no se puede, lanzaría una excepción
     * @return Devuelve la conexión cuando se conecte
     */
    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASS);
            }
        } catch (SQLException e) {
            System.out.println("Error al crear la conexión con la base de datos\n" + e.getMessage());
        }

        return conn;
    }

    /**
     * La función <strong>{@code closeConnection()}</strong> comprueba que la conexión no sea nula. Si no es nula, la
     * cierra. Si existe algún error al cerrarla, lanza una excepción con un error.
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión a la base de datos\n" + e.getMessage());
            }
        }
    }
}
