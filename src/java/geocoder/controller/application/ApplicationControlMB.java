/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geocoder.controller.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import geocoder.model.dao.sessionbeans.ConfigurationsFacade;
import geocoder.model.dao.entityclasses.Configurations;

/**
 *
 * @author nelson
 */
/**
 * This class is responsible for identifying the server path, set different
 * record identifiers to prevent that two users use the same login ID and add a
 * session to the list of active sessions.
 *
 */
@ManagedBean(name = "applicationControlMB")
@ApplicationScoped
public class ApplicationControlMB {

//    @Resource(name = "jdbc/od")
//    private DataSource ds;//fuente de datos(es configurada por glassfish)  
    @EJB
    ConfigurationsFacade configurationsFacade;
    private ResultSet rs;
    private Statement st;
    private String user;
    private String db;
    private String password;
    private String server;
    private String url = "";
    private ArrayList<String> currentIdSessions = new ArrayList<>();//lista de identificadores de sesiones
    private ArrayList<Integer> currentUserIdSessions = new ArrayList<>();//lista de id de usuarios logeados
    public Connection conn;

    /**
     * This method is the class constuctor, this method is responsible to gets
     * the actual path of the server and starts a timer that is called every
     * hour.
     */
    public ApplicationControlMB() {
        
    }

    public void reset() {//funcion llamada en la pagina de login para que la instancia de esta clase sea visible en el contexto
    }

    @PostConstruct
    private void event() {
        Configurations dbConfiguration = configurationsFacade.findAll().get(0);
        user = dbConfiguration.getUserDb();
        db = dbConfiguration.getNameDb();
        password = dbConfiguration.getPasswordDb();
        server = dbConfiguration.getServerDb();
        connectToDb();
    }

    /**
     * This method is responsable to destroy a session, this is done when the
     * system is not connected to the database or the connection is closed.
     */
    @PreDestroy
    private void destroySession() {
        try {
            if (conn != null && !conn.isClosed()) {
                disconnect();
            }
        } catch (Exception e) {
            //System.out.println("Termina session por inactividad 003 " + e.toString());
        }
    }

    /**
     * This method allows the system to disconnect of database.
     */
    public void disconnect() {
        try {
            if (!conn.isClosed()) {
                conn.close();
                System.out.println("Cerrada conexion a base de datos " + url + " ... OK  " + this.getClass().getName());
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar conexion a base de datos " + url + " " + this.getClass().getName() + " ... " + e.toString());
        }
    }
    

    /**
     * This method allows the system to connect to the database via a datasource
     * (Connection configured by GlassFish), and generate a normal connection by
     * JDBC.
     *
     * @return
     */
    public final boolean connectToDb() {
        /*
         * Nos conectamos a la base de datos atraves 
         * de un DataSource = (conexion configurada por glassFish)
         * y generamos una conexion normal por JDBC
         */
        boolean returnValue = true;
        if (conn == null) {
            returnValue = false;
            try {
                url = "jdbc:postgresql://" + server + "/" + db;
                try {
                    Class.forName("org.postgresql.Driver").newInstance();// seleccionar SGBD
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    System.out.println("Error 1 en " + this.getClass().getName() + ":" + e.getMessage());
                }
                //conn.close();
                conn = DriverManager.getConnection(url, user, password);// Realizar la conexion
                if (conn != null) {
                    System.out.println("Conexion a base de datos " + url + " " + this.getClass().getName());
                    returnValue = true;
                } else {
                    System.out.println("No se pudo conectar a base de datos " + url + " " + this.getClass().getName() + " ... FAIL");
                }
            } catch (Exception e) {
                System.out.println("Error 2 en " + this.getClass().getName() + ":" + e.toString());
            }
        }
        return returnValue;
    }

    /**
     * It is responsible to process a query that returns one or more rows of
     * data base on a ResultSet.
     *
     * @param query
     * @return
     */
    public ResultSet consult(String query) {
        /*
         * se encarga de procesar una consulta que retorne una o varias tuplas
         * de la base de datos retornandolas en un ResultSet
         */
        //msj = "";
        try {
            if (conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                //System.out.println("---- CONSULTA: " + query);
                return rs;
            } else {
                System.out.println("There don't exist connection");
                return null;
            }
        } catch (SQLException e) {
            //System.out.println("Error 3 en " + this.getClass().getName() + ":" + e.getMessage() + "---- CONSULTA:" + query);
            return null;
        }
    }

    /**
     * This method is responsible to process a query that does not return tuples
     * for example: INSERT, UPDATE, DELETE ...
     *
     * @param query
     * @return
     */
    public int non_query(String query) {
        /*
         * se encarga de procesar una consulta que no retorne tuplas
         * ejemplo: INSERT, UPDATE, DELETE...
         * retorna 0 si se realizo correctamente
         * retorna 1 cuando la instuccion no pudo ejecutarse
         */
        int reg;
        reg = 0;
        try {
            if (conn != null) {
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    reg = stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error 4 en " + this.getClass().getName() + ":" + e.getMessage());
        }
        return reg;
    }

    /**
     * This method is responsible to determine if a user is logged on.
     *
     * @param idUser
     * @return
     */
    public boolean hasLogged(int idUser) {
        /*
         * determina si un usario tiene una sesion iniciada 
         * idUser= identificador del usuario en la base de datos
         */
        boolean foundIdUser = false;
        //determinar si el usuario ya tiene iniciada una sesion
        for (int i = 0; i < currentUserIdSessions.size(); i++) {
            if (currentUserIdSessions.get(i) == idUser) {
                foundIdUser = true;
                break;
            }
        }
        return foundIdUser;
    }

    /**
     * This method is responsible to add a session to the list of active
     * sessions.
     *
     * @param idUser
     * @param idSession
     */
    public void addSession(int idUser, String idSession) {
        /*
         * adicionar a la lista de sesiones activas
         */
        currentIdSessions.add(idSession);
        currentUserIdSessions.add(idUser);//System.out.println("Agregada Nueva sesion: " + idSession + "  usuario: " + idUser);
    }

    /**
     * This method allows the system to remove a session from the list of active
     * sessions depending of the user id.
     *
     * @param idUser
     */
    public void removeSession(int idUser) {
        /*
         * eliminar de la lista de sesiones activas dependiento del id del usuario
         */
        try {
            for (int i = 0; i < currentUserIdSessions.size(); i++) {
                if (currentUserIdSessions.get(i) == idUser) {
                    currentUserIdSessions.remove(i);
                    currentIdSessions.remove(i);//System.out.println("Session eliminada usuario: " + idUser);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error 9 en " + this.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * This method allows the system to delete a session from the list of active
     * sessions depending of the session id.
     *
     * @param idSession
     */
    public void removeSession(String idSession) {
        /*
         * eliminar de la lista de sesiones actuales dependiento del id de la sesion
         */
        try {
            for (int i = 0; i < currentUserIdSessions.size(); i++) {
                if (currentIdSessions.get(i).compareTo(idSession) == 0) {
                    currentUserIdSessions.remove(i);
                    currentIdSessions.remove(i);//System.out.println("Session eliminada sesion: " + idSession);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error 10 en " + this.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * This method allows the system to search for a session according to its
     * ID.
     *
     * @param idSessionSearch
     * @return
     */
    public boolean findIdSession(String idSessionSearch) {
        /*
         * buscar una session segun su id
         */
        boolean booleanReturn = false;
        for (int i = 0; i < currentIdSessions.size(); i++) {
            if (currentIdSessions.get(i).compareTo(idSessionSearch) == 0) {
                booleanReturn = true;
                break;
            }
        }
        return booleanReturn;
    }

    /**
     * This method determines what the maximum identifier of the Geocoder users who are
     * in the system
     *
     * @return
     */
    public int getMaxGeocoderUserId() {
        //deteminar cual es el maximo identificador de los usuarios que esten en el sistema
        //los invitados inician en 1000
        if (currentUserIdSessions != null && !currentUserIdSessions.isEmpty()) {
            int max = 5000;
            for (int i = 0; i < currentUserIdSessions.size(); i++) {
                if (currentUserIdSessions.get(i) > max) {
                    max = currentUserIdSessions.get(i);
                }
            }
            return max;
        } else {
            return 5000;
        }
    }

}
