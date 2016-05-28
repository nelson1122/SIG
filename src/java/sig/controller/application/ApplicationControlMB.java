/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sig.controller.application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import sig.model.dao.ConfigurationsFacade;
import sig.model.pojo.Configurations;

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
