/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sig.model.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sig.model.pojo.IndicatorsConfigurations;

/**
 *
 * @author nelson
 */
@Stateless
public class IndicatorsConfigurationsFacade extends AbstractFacade<IndicatorsConfigurations> {

    @PersistenceContext(unitName = "SIGPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IndicatorsConfigurationsFacade() {
        super(IndicatorsConfigurations.class);
    }
    
     public int findMax() {
        try {
            String hql = "Select MAX(x.configurationId) from IndicatorsConfigurations x";
            return em.createQuery(hql, Integer.class).getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
    public IndicatorsConfigurations findByName(String name) {
        try {
            String hql = "Select x from IndicatorsConfigurations x where x.configurationName=:name";
            return (IndicatorsConfigurations) em.createQuery(hql).setParameter("name", name).getSingleResult();
        } catch (Exception e) {
            System.out.print("Error: " + e.toString() + "------------------------");
            return null;
        }
    }
    
}
