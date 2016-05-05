/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geocoder.model.dao.sessionbeans;

import geocoder.model.dao.entityclasses.IndicatorsAddresses;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author nelson
 */
@Stateless
public class IndicatorsAddressesFacade extends AbstractFacade<IndicatorsAddresses> {

    @PersistenceContext(unitName = "SIGPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IndicatorsAddressesFacade() {
        super(IndicatorsAddresses.class);
    }
    
    public int nonQuery(String sql){
        Query q = getEntityManager().createNativeQuery(sql);
        return q.executeUpdate();
    }
    
}
