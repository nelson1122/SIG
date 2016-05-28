/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sig.model.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nelson
 */
@Entity
@Table(name = "indicators_addresses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IndicatorsAddresses.findAll", query = "SELECT i FROM IndicatorsAddresses i"),
    @NamedQuery(name = "IndicatorsAddresses.findByUserId", query = "SELECT i FROM IndicatorsAddresses i WHERE i.indicatorsAddressesPK.userId = :userId"),
    @NamedQuery(name = "IndicatorsAddresses.findByIndicatorId", query = "SELECT i FROM IndicatorsAddresses i WHERE i.indicatorsAddressesPK.indicatorId = :indicatorId"),
    @NamedQuery(name = "IndicatorsAddresses.findByRecordId", query = "SELECT i FROM IndicatorsAddresses i WHERE i.indicatorsAddressesPK.recordId = :recordId"),
    @NamedQuery(name = "IndicatorsAddresses.findByColumn1", query = "SELECT i FROM IndicatorsAddresses i WHERE i.column1 = :column1"),
    @NamedQuery(name = "IndicatorsAddresses.findByColumn2", query = "SELECT i FROM IndicatorsAddresses i WHERE i.column2 = :column2"),
    @NamedQuery(name = "IndicatorsAddresses.findByColumn3", query = "SELECT i FROM IndicatorsAddresses i WHERE i.column3 = :column3"),
    @NamedQuery(name = "IndicatorsAddresses.findByCount", query = "SELECT i FROM IndicatorsAddresses i WHERE i.count = :count"),
    @NamedQuery(name = "IndicatorsAddresses.findByInjuryId", query = "SELECT i FROM IndicatorsAddresses i WHERE i.injuryId = :injuryId")})
public class IndicatorsAddresses implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IndicatorsAddressesPK indicatorsAddressesPK;
    @Size(max = 2147483647)
    @Column(name = "column_1")
    private String column1;
    @Size(max = 2147483647)
    @Column(name = "column_2")
    private String column2;
    @Size(max = 2147483647)
    @Column(name = "column_3")
    private String column3;
    @Column(name = "count")
    private Integer count;
    @Column(name = "injury_id")
    private Integer injuryId;

    public IndicatorsAddresses() {
    }

    public IndicatorsAddresses(IndicatorsAddressesPK indicatorsAddressesPK) {
        this.indicatorsAddressesPK = indicatorsAddressesPK;
    }

    public IndicatorsAddresses(int userId, int indicatorId, int recordId) {
        this.indicatorsAddressesPK = new IndicatorsAddressesPK(userId, indicatorId, recordId);
    }

    public IndicatorsAddressesPK getIndicatorsAddressesPK() {
        return indicatorsAddressesPK;
    }

    public void setIndicatorsAddressesPK(IndicatorsAddressesPK indicatorsAddressesPK) {
        this.indicatorsAddressesPK = indicatorsAddressesPK;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getInjuryId() {
        return injuryId;
    }

    public void setInjuryId(Integer injuryId) {
        this.injuryId = injuryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indicatorsAddressesPK != null ? indicatorsAddressesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndicatorsAddresses)) {
            return false;
        }
        IndicatorsAddresses other = (IndicatorsAddresses) object;
        if ((this.indicatorsAddressesPK == null && other.indicatorsAddressesPK != null) || (this.indicatorsAddressesPK != null && !this.indicatorsAddressesPK.equals(other.indicatorsAddressesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "geocoder.model.dao.entityclasses.IndicatorsAddresses[ indicatorsAddressesPK=" + indicatorsAddressesPK + " ]";
    }
    
}
