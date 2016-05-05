/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geocoder.model.dao.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import geocoder.model.dao.entityclasses.Configurations;

/**
 *
 * @author nelson
 */
@Stateless
public class ConfigurationsFacade extends AbstractFacade<Configurations> {

    @PersistenceContext(unitName = "SIGPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfigurationsFacade() {
        super(Configurations.class);
    }
    
}
