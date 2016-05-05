/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geocoder.model.dao.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import geocoder.model.dao.entityclasses.Users;

/**
 *
 * @author nelson
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "SIGPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    public int findMax() {
        try {
            String hql = "Select MAX(x.userId) from Users x";
            return em.createQuery(hql, Integer.class).getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    public Users findByLogin(String newLogin) {
        List<Users> usersList = this.findAll();
        for (int i = 0; i < usersList.size(); i++) {
            if ( usersList.get(i).getUserLogin().compareTo(newLogin) == 0) {
                return usersList.get(i);
            }
        }
        return null;
    }

    public Users findUser(String loginname, String password) {
        List<Users> usersList = this.findAll();
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUserPassword().compareTo(password) == 0
                    && usersList.get(i).getUserLogin().compareTo(loginname) == 0) {
                return usersList.get(i);
            }
        }

        return null;

    }
    
}
