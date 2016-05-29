/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sig.model.jdbc;

import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sig.model.pojo.Configurations;
import sig.model.pojo.Users;
import sig.model.dao.ConfigurationsFacade;


/**
 * This class is responsible for the management connection to the database, also
 * this class is responsible to make the necessary queries for the system to
 * function properly.
 *
 * @author nelson
 */
@ManagedBean(name = "connectionJdbcMB")
@SessionScoped
/**
 *
 */
public class ConnectionJdbcMB implements Serializable {

//    @Resource(name = "jdbc/od")
//    private DataSource ds;
    @EJB
    ConfigurationsFacade configurationsFacade;
    
    private String hours = "";
    private String minutes = "";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    public Connection conn;
    private String tableName = "temp";//nombre de la tabla temp para carga masiva
    private Statement st;
    private ResultSet rs;
    private String msj;
    private String user;
    private String db;
    private String db_dwh;
    private String password;
    private String server;
    private String url = "";
    private boolean connectionIsConfigured = true;
    private boolean connectionNotConfigured = false;
    private Users currentUser;
    private boolean showMessages = true;//determinar si mostrar o no los mensajes de error

    @PostConstruct
    private void event() {
        Configurations dbConfiguration = configurationsFacade.findAll().get(0);
        user = dbConfiguration.getUserDb();
        db = dbConfiguration.getNameDb();
        db_dwh = dbConfiguration.getNameDbDwh();
        password = dbConfiguration.getPasswordDb();
        server = dbConfiguration.getServerDb();
    }

    /**
     * This method is responsible to creates a new instance of ConnectionJdbcMB.
     */
    public ConnectionJdbcMB() {
    }

    /**
     * This method is responsible for closing the connection established to the
     * database
     */
    @PreDestroy
    public synchronized void disconnect() {
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
     * This method is responsible for checking if the system can to connect to
     * the data warehouse and database, and stores this data in the table
     * settings
     *
     * @return
     */
    public String checkConnection() {
        /*
         * verifica si se puede conectar a la bodega y db observatorio y almacena
         * estos datos en la tabla configuraciones
         * si se conecta retorna "index" que es la pagina de inicio de la aplicacion
         */
        boolean continueProcess = true;
        try {
            if (continueProcess) {
                try {
                    Class.forName("org.postgresql.Driver").newInstance();// seleccionar SGBD
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    msj = e.toString() + " --- Clase: " + this.getClass().getName();
                    continueProcess = false;
                }
            }
            if (continueProcess) {

                boolean correctConnection = true;

                try {
                    url = "jdbc:postgresql://" + server + "/" + db;
                    conn = DriverManager.getConnection(url, user, password);//conectarse db del observatorio
                    if (conn != null && !conn.isClosed()) {
                        msj = msj + " Conexión a base de datos observatorio Correcta.";
                    } else {
                        msj = msj + " Conexión a base de datos observatorio Fallida.";
                        correctConnection = false;
                    }
                } catch (Exception e) {
                    msj = msj + " Conexión a base de datos observatorio Fallida.";
                    correctConnection = false;
                    conn = null;
                }
                if (correctConnection) {//se realizaron las dos conexiones(observatorio y bodega)                    
                    non_query("delete from configurations");
                    insert("configurations", "user_db,password_db,name_db,server_db,name_db_dwh",
                            "'" + user + "','" + password + "','" + db + "','" + server + "','" + db_dwh + "'");
                    //conn.close();
                    return "index";//se redirirecciona a la pagina principal
                }
            }
        } catch (Exception e) {
            msj = e.toString();
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "La conexion no pudo ser creada con los datos suministrados. " + msj));
        return "";
    }

    /**
     * This method is responsible for connecting to the database, if there is an
     * error, it is shown in the screen
     *
     * @return
     */
    public final boolean connectToDb() {
        /*
         * conectarse a la base datos observatorio y bodega
         * y retornar true si se realizo la conexion
         */
        boolean continueProcess = true;
        try {
            if (conn == null || conn.isClosed()) {
                if (continueProcess) {
                    try {
                        Class.forName("org.postgresql.Driver").newInstance();// seleccionar SGBD
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        System.out.println("Error1: " + e.toString() + " --- Clase: " + this.getClass().getName());
                        continueProcess = false;
                    }
                }
                if (continueProcess) {
                    boolean correctConnection = true;
                    try {
                        url = "jdbc:postgresql://" + server + "/" + db;
                        conn = DriverManager.getConnection(url, user, password);//conectarse db del observatorio
                        if (conn != null && !conn.isClosed()) {
                            msj = msj + " Conexión a base de datos observatorio Correcta.";
                        } else {
                            msj = msj + " Conexión a base de datos observatorio Fallida.";
                            correctConnection = false;
                        }
                    } catch (Exception e) {
                        msj = msj + " Conexión a base de datos observatorio Fallida.";
                        correctConnection = false;
                        conn = null;
                    }
                    if (correctConnection) {//se realizaron las dos conexiones(observatorio y bodega)                    
                        System.out.println("Conexion a base de datos " + url + " ... OK  " + this.getClass().getName());
                        return true;
                    } else {
                        System.out.println("No se pudo conectar a base de datos " + url + " " + this.getClass().getName() + " ... FAIL");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * this method is used to query to database.
     *
     * @param query
     * @return
     */
    public ResultSet consult(String query) {
        msj = "";
        try {
            if (conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                //System.out.println("---- CONSULTA: " + query);
                return rs;
            } else {
                msj = "There don't exist connection";
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString() + " - Clase: " + this.getClass().getName());
            msj = "ERROR: " + e.getMessage() + "---- CONSULTA:" + query;
            return null;
        }
    }

    /**
     * this method is used to query to database that do not return any value as
     * INSERT, UPDATE or DELETE.
     *
     * @param query
     * @return
     */
    public int non_query(String query) {
        msj = "";
        PreparedStatement stmt;
        int reg;
        reg = 0;
        try {
            if (conn != null) {
                stmt = conn.prepareStatement(query);
                reg = stmt.executeUpdate();
                stmt.close();
            }

        } catch (SQLException e) {
            if (showMessages) {
                System.out.println("Error: " + e.toString() + " -- Clase: " + this.getClass().getName() + " -  " + query);
            }
            msj = "ERROR: " + e.getMessage();
        }
        return reg;
    }

    /**
     * this method is used to insert data into a specific table in the database.
     *
     * @param Tabla
     * @param elementos
     * @param registro
     * @return
     */
    public String insert(String Tabla, String elementos, String registro) {
        msj = "";
        int reg = 1;
        String success;
        try {
            if (conn != null) {
                st = conn.createStatement();
                st.execute("INSERT INTO " + Tabla + " (" + elementos + ") VALUES (" + registro + ")");
                if (reg > 0) {
                    success = "true";
                } else {
                    success = "false";
                }
                st.close();
            } else {
                success = "false";
                msj = "ERROR: There don't exist connection...";
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString() + " --- Clase: " + this.getClass().getName());
            System.out.println("numero: " + e.getErrorCode());
            success = e.getMessage();
            msj = "ERROR: " + e.getMessage();
        }
        return success;
    }

    /**
     * this method is used to remove data from a specified table in the
     * database.
     *
     * @param Tabla
     * @param condicion
     */
    public void remove(String Tabla, String condicion) {
        msj = "";

        int reg;
        try {
            if (conn != null) {
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + Tabla + " WHERE " + condicion)) {
                    reg = stmt.executeUpdate();
                    if (reg > 0) {
                    } else {
                    }
                }
            } else {
                msj = "ERROR: There don't exist connection";
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString() + " ---- Clase: " + this.getClass().getName());
            msj = "ERROR: " + e.getMessage();
        }
    }

    /**
     * this method is used to update data into a specified table in the
     * database.
     *
     * @param Tabla
     * @param campos
     * @param donde
     */
    public void update(String Tabla, String campos, String donde) {
        msj = "";
        int reg;
        try {
            if (conn != null) {
                try (PreparedStatement stmt = conn.prepareStatement("UPDATE " + Tabla + " SET " + campos + " WHERE " + donde)) {
                    reg = stmt.executeUpdate();
                    if (reg > 0) {
                    } else {
                    }
                }
            } else {
                msj = "ERROR: There don't exist connection";
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString() + " ----- Clase: " + this.getClass().getName());
            msj = "ERROR: " + e.getMessage();
        }
    }

    // --------------------------    
    // -- METODOS GET Y SET -----
    // --------------------------    
    public String getMsj() {
        return msj;
    }

    public void setMsj(String mens) {
        msj = mens;
    }
    
    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection con) {
        this.conn = con;
    }
    
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isConnectionIsConfigured() {
        return connectionIsConfigured;
    }

    public void setConnectionIsConfigured(boolean connectionIsConfigured) {
        this.connectionIsConfigured = connectionIsConfigured;
    }

    public boolean isConnectionNotConfigured() {
        return connectionNotConfigured;
    }

    public void setConnectionNotConfigured(boolean connectionNotConfigured) {
        this.connectionNotConfigured = connectionNotConfigured;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isShowMessages() {
        return showMessages;
    }

    public void setShowMessages(boolean showMessages) {
        this.showMessages = showMessages;
    }

    public String getDb_dwh() {
        return db_dwh;
    }

    public void setDb_dwh(String db_dwh) {
        this.db_dwh = db_dwh;
    }
}
