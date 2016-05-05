/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geocoder.model.dao.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import geocoder.model.dao.entityclasses.Indicators;

/**
 *
 * @author nelson
 */
@Stateless
public class IndicatorsFacade extends AbstractFacade<Indicators> {

    @PersistenceContext(unitName = "SIGPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IndicatorsFacade() {
        super(Indicators.class);
    }
    
}
