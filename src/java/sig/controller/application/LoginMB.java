/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sig.controller.application;

import sig.model.jdbc.ConnectionJdbcMB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import sig.model.pojo.Users;


/**
 *
 * @author nelson
 */
/**
 * This class is responsible to Manage the login and logout of the users, also
 * this method permitie access for guests.
 *
 */
@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB {

    private String idSession = "";//identificador de la session
    private String loginname = "";
    private String password = "";
    private String userLogin = "";
    private String userName = "";
    private String userJob = "";
    
    private Users currentUser;

    FacesContext context;
    ApplicationControlMB applicationControlMB;
    ConnectionJdbcMB connectionJdbcMB;
    private boolean autenticado = false;
    

    /**
     * This method is the class constructor.
     */
    public LoginMB() {
    }

    /**
     * This method is responsible to destroy an active session, first deletes
     * temporary user data, This method is called when the user has stopped
     * using the software for a specified time, has stopped the server or the
     * user has decided to close their session.
     */
    @PreDestroy
    public void destroySession() {
        /*
         * antes de destruir esta clase se eliminan datos temporales a el usuario 
         * que ingreso al sistema, esto ocurre si se para el servidor o la sesion es
         * destruida por inactividad
         */
        try {
            applicationControlMB.removeSession(idSession);//elimino de la lista de usuarios actuales en el sistema
            if (connectionJdbcMB.getConn() != null && !connectionJdbcMB.getConn().isClosed()) {
                connectionJdbcMB.non_query("DELETE FROM indicators_addresses WHERE user_id = " + currentUser.getUserId());//elimino datos que este usuario tenga por realizacion de indicadores
            }
        } catch (Exception e) {
            
        }
    }

    
    /**
     * This method ends a session when the user decides to close a current
     * session.
     */
    public void logout() {
        /*
         * fin de sesion dada por el usuario: botón "cerrar cesion"
         */
        applicationControlMB.removeSession(idSession);//System.out.println("Finaliza session "+loginname+" ID: "+idSession);
        connectionJdbcMB.non_query("DELETE FROM indicators_addresses WHERE user_id = " + currentUser.getUserId());//elimino datos que este usuario tenga por realizacion de indicadores
        try {
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
            ((HttpSession) ctx.getSession(false)).invalidate();//System.out.println("se redirecciona");
            ctx.redirect(ctxPath);
        } catch (Exception ex) {
            System.out.println("Excepcion cuando usuario cierra sesion sesion: " + ex.toString());
        }
    }

    /**
     * This method is responsible to initialize all variables used by the system
     * to function properly, as error control variables, variables used in the
     * creation of new forms, relations of variables, filters and sets the
     * current configuration the system has.
     *
     * @return
     */
    private String inicializeVariables() {
        if (connectionJdbcMB.connectToDb()) {
            connectionJdbcMB.setCurrentUser(currentUser);

            autenticado = true;

            ResultSet rs = connectionJdbcMB.consult("SELECT * FROM configurations");
            try {
                rs.next();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("db_user", rs.getString("user_db"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("db_pass", rs.getString("password_db"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("db_host", rs.getString("server_db"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("db_name", rs.getString("name_db"));
            } catch (SQLException ex) {
                Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
            }

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!!", "Se ha ingresado al sistema");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "homePage";
        } else {
            FacesMessage msg2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Se debe crear una conexión");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
            password = "";
            return "indexConfiguration";
        }
    }

    
    /**
     * This method checks if the user is registered in the database and if your
     * account is still active, for to allow access to the system.
     *
     * @return
     */
    public String checkGeocoderValidUser(){
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        applicationControlMB = (ApplicationControlMB) contexto.getApplicationMap().get("applicationControlMB");
        context = FacesContext.getCurrentInstance();
        connectionJdbcMB = (ConnectionJdbcMB) context.getApplication().evaluateExpressionGet(context, "#{connectionJdbcMB}", ConnectionJdbcMB.class);
        
        HttpSession session = (HttpSession) contexto.getSession(false);

        idSession = session.getId();

        if (applicationControlMB.findIdSession(idSession)) {//ya existe esta sesion solo se ingresa a la aplicacion
            return "geocoderHomePage";
        } else {//no existe sesion se debe crear el usuario temporal         
            int max = applicationControlMB.getMaxGeocoderUserId();
            if (max < 5001) {
                max = 5001;//le aumento mil para que no tenga un id de los usuarios registrados del sistema
            } else {
                max = max + 1;
            }
            currentUser = new Users(max, "Invitado " + String.valueOf(max - 5000), "123", "USUARIO");//nuevo ususario temporal
            currentUser.setActive(true);
            loginname = "Invitado: " + String.valueOf(max - 5000);//el id de los invitados inicia en 1000, se les rest mil para que aparezcan como 1,2...etc y no como 1001,1002....etc
            contexto.getSessionMap().put("username", loginname);
            userLogin = currentUser.getUserLogin();
            userName = currentUser.getUserName();
            userJob = "Analista";
            applicationControlMB.addSession(currentUser.getUserId(), idSession);
            
            String resultPage = inicializeVariables();
            //System.out.println("PAGINA:\n" + "geocoder" + (resultPage.substring(0, 1).toUpperCase()) + (resultPage.substring(1)));
            
            return "geocoder" + (resultPage.substring(0, 1).toUpperCase()) + (resultPage.substring(1));
        }
    }
    
    
    
    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

}
