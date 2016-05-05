/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geocoder.controller.application;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import geocoder.model.dao.sessionbeans.UsersFacade;
import geocoder.model.dao.entityclasses.Users;
import geocoder.model.dao.connection.ConnectionJdbcMB;
/**
 *
 * @author nelson
 */
/**
 * This class is responsible to controll the user permissions, in addition to
 * create a connection to the database.
 *
 */
@ManagedBean(name = "usersMB")
@SessionScoped
public class UsersMB {


    private int currentSearchCriteria = 0;
    private String currentSearchValue = "";
    @EJB
    UsersFacade usersFacade;
    private String stateUser = "Activa";
    private String newStateUser = "Activa";
    private Users currentUser;
    private String name = "";
    private String newName = "";
    private String job = "";
    private String newJob = "";
    private String institution = "";
    private String newInstitution = "";
    private String telephone = "";
    private String newtelephone = "";
    private String email = "";
    private String newEmail = "";
    private String password = "";
    private String passwordTmp = "";//pasword temporal
    private String newPasword = "";
    private String confirmPassword = "";
    private String newConfirmPasword = "";
    private String address = "";
    private String newAddress = "";
    private String login = "";
    private String newLogin = "";
    private boolean btnEditDisabled = true;
    private boolean btnRemoveDisabled = true;
    private boolean permission1 = true;
    private boolean permission2 = true;
    private boolean permission3 = true;
    private boolean permission4 = true;
    private boolean permission5 = true;
    private boolean newPermission1 = true;
    private boolean newPermission2 = true;
    private boolean newPermission3 = true;
    private boolean newPermission4 = true;
    private boolean newPermission5 = true;
    private ConnectionJdbcMB connectionJdbcMB;


    /**
     * This method is responsible to Create a connection to the database.
     */
    public UsersMB() {
        connectionJdbcMB = (ConnectionJdbcMB) FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{connectionJdbcMB}", ConnectionJdbcMB.class);
    }

    

   
    /**
     * This method is used to display the registration form completely empty to
     * register a new user.
     */
    public void newRegistry() {
        name = "";
        newName = "";
        job = "";
        newJob = "";
        institution = "";
        newInstitution = "";
        telephone = "";
        newtelephone = "";
        email = "";
        newEmail = "";
        password = "";
        newPasword = "";
        address = "";
        newAddress = "";
        login = "";
        newLogin = "";
        permission1 = true;
        permission2 = true;
        permission3 = true;
        permission4 = true;
        permission5 = true;
        stateUser = "Activa";
        newStateUser = "Activa";
    }


    /**
     * This method automatically determines the permissions accepted when the
     * user select or deselect a checkbox permission.
     */
    public void changePermission1() {
        /*
         * determinar automaticamente permisos aceptados seleccione o desseleccione
         * las casillas de permisos 
         */
        if (permission1 == false) {
            permission5 = false;
        } else if (permission1 && permission2 && permission3 && permission4) {
            permission5 = true;
        }
    }

    /**
     * This method automatically determines the permissions accepted when the
     * user select or deselect a checkbox permission.
     */
    public void changePermission2() {
        /*
         * determinar automaticamente permisos aceptados seleccione o desseleccione
         * las casillas de permisos 
         */
        if (permission2 == false) {
            permission5 = false;
        } else if (permission1 && permission2 && permission3 && permission4) {
            permission5 = true;
        }
    }

    /**
     * This method automatically determines the permissions accepted when the
     * user select or deselect a checkbox permission.
     */
    public void changePermission3() {
        if (permission3 == false) {
            permission5 = false;
        } else if (permission1 && permission2 && permission3 && permission4) {
            permission5 = true;
        }
    }

    /**
     * This method automatically determines the permissions accepted when the
     * user select or deselect a checkbox permission.
     */
    public void changePermission4() {
        if (permission4 == false) {
            permission5 = false;
        } else if (permission1 && permission2 && permission3 && permission4) {
            permission5 = true;
        }
    }

    /**
     * This method automatically determines the permissions accepted when the
     * user select or deselect a checkbox permission.
     */
    public void changePermission5() {
        if (permission5 == true) {
            permission1 = true;
            permission2 = true;
            permission3 = true;
            permission4 = true;
            permission5 = true;
        } else if (permission1 && permission2 && permission3 && permission4) {
            permission5 = true;
        }
    }

    /**
     * This method automatically determines the permissions accepted when the
     * user select or deselect a checkbox permission.
     */
    public void changeNewPermission1() {
        if (newPermission1 == false) {
            newPermission5 = false;
        } else if (newPermission1 && newPermission2 && newPermission3 && newPermission4) {
            newPermission5 = true;
        }
    }

    /**
     * This method automatically determines the permissions accepted when the
     * user select or deselect a checkbox permission.
     */
    public void changeNewPermission2() {
        if (newPermission2 == false) {
            newPermission5 = false;
        } else if (newPermission1 && newPermission2 && newPermission3 && newPermission4) {
            newPermission5 = true;
        }
    }

    /**
     * This method automatically determines the permissions accepted when the
     * user select or deselect a checkbox permission.
     */
    public void changeNewPermission3() {
        if (newPermission3 == false) {
            newPermission5 = false;
        } else if (newPermission1 && newPermission2 && newPermission3 && newPermission4) {
            newPermission5 = true;
        }
    }

    /**
     * This method automatically determines the permissions accepted when the
     * user select or deselect a checkbox permission.
     */
    public void changeNewPermission4() {
        if (newPermission4 == false) {
            newPermission5 = false;
        } else if (newPermission1 && newPermission2 && newPermission3 && newPermission4) {
            newPermission5 = true;
        }
    }

    /**
     * This method automatically determines the permissions accepted when the
     * user select or deselect a checkbox permission.
     */
    public void changeNewPermission5() {
        if (newPermission5 == true) {
            newPermission1 = true;
            newPermission2 = true;
            newPermission3 = true;
            newPermission4 = true;
            newPermission5 = true;
        } else if (newPermission1 && newPermission2 && newPermission3 && newPermission4) {
            newPermission5 = true;
        }
    }


    public int getCurrentSearchCriteria() {
        return currentSearchCriteria;
    }

    public void setCurrentSearchCriteria(int currentSearchCriteria) {
        this.currentSearchCriteria = currentSearchCriteria;
    }

    public String getCurrentSearchValue() {
        return currentSearchValue;
    }

    public void setCurrentSearchValue(String currentSearchValue) {
        this.currentSearchValue = currentSearchValue;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentActivitiy(Users currentUser) {
        this.currentUser = currentUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewInstitution() {
        return newInstitution;
    }

    public void setNewInstitution(String newInstitution) {
        this.newInstitution = newInstitution;
    }

    public String getNewJob() {
        return newJob;
    }

    public void setNewJob(String newJob) {
        this.newJob = newJob;
    }

    public String getNewPasword() {
        return newPasword;
    }

    public void setNewPasword(String newPasword) {
        this.newPasword = newPasword;
    }

    public String getNewtelephone() {
        return newtelephone;
    }

    public void setNewtelephone(String newtelephone) {
        this.newtelephone = newtelephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isBtnEditDisabled() {
        return btnEditDisabled;
    }

    public void setBtnEditDisabled(boolean btnEditDisabled) {
        this.btnEditDisabled = btnEditDisabled;
    }

    public boolean isBtnRemoveDisabled() {
        return btnRemoveDisabled;
    }

    public void setBtnRemoveDisabled(boolean btnRemoveDisabled) {
        this.btnRemoveDisabled = btnRemoveDisabled;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNewLogin() {
        return newLogin;
    }

    public void setNewLogin(String newLogin) {
        this.newLogin = newLogin;
    }

    public boolean isPermission1() {
        return permission1;
    }

    public void setPermission1(boolean permission1) {
        this.permission1 = permission1;
    }

    public boolean isPermission2() {
        return permission2;
    }

    public void setPermission2(boolean permission2) {
        this.permission2 = permission2;
    }

    public boolean isPermission3() {
        return permission3;
    }

    public void setPermission3(boolean permission3) {
        this.permission3 = permission3;
    }

    public boolean isPermission4() {
        return permission4;
    }

    public void setPermission4(boolean permission4) {
        this.permission4 = permission4;
    }

    public boolean isPermission5() {
        return permission5;
    }

    public void setPermission5(boolean permission5) {
        this.permission5 = permission5;
    }

    public boolean isNewPermission1() {
        return newPermission1;
    }

    public void setNewPermission1(boolean newPermission1) {
        this.newPermission1 = newPermission1;
    }

    public boolean isNewPermission2() {
        return newPermission2;
    }

    public void setNewPermission2(boolean newPermission2) {
        this.newPermission2 = newPermission2;
    }

    public boolean isNewPermission3() {
        return newPermission3;
    }

    public void setNewPermission3(boolean newPermission3) {
        this.newPermission3 = newPermission3;
    }

    public boolean isNewPermission4() {
        return newPermission4;
    }

    public void setNewPermission4(boolean newPermission4) {
        this.newPermission4 = newPermission4;
    }

    public boolean isNewPermission5() {
        return newPermission5;
    }

    public void setNewPermission5(boolean newPermission5) {
        this.newPermission5 = newPermission5;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewConfirmPasword() {
        return newConfirmPasword;
    }

    public void setNewConfirmPasword(String newConfirmPasword) {
        this.newConfirmPasword = newConfirmPasword;
    }

    public String getStateUser() {
        return stateUser;
    }

    public void setStateUser(String stateUser) {
        this.stateUser = stateUser;
    }

    public String getNewStateUser() {
        return newStateUser;
    }

    public void setNewStateUser(String newStateUser) {
        this.newStateUser = newStateUser;
    }
}
