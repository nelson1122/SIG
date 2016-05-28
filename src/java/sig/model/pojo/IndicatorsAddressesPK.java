/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sig.model.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author nelson
 */
@Embeddable
public class IndicatorsAddressesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indicator_id")
    private int indicatorId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "record_id")
    private int recordId;

    public IndicatorsAddressesPK() {
    }

    public IndicatorsAddressesPK(int userId, int indicatorId, int recordId) {
        this.userId = userId;
        this.indicatorId = indicatorId;
        this.recordId = recordId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(int indicatorId) {
        this.indicatorId = indicatorId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) indicatorId;
        hash += (int) recordId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndicatorsAddressesPK)) {
            return false;
        }
        IndicatorsAddressesPK other = (IndicatorsAddressesPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.indicatorId != other.indicatorId) {
            return false;
        }
        if (this.recordId != other.recordId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "geocoder.model.dao.entityclasses.IndicatorsAddressesPK[ userId=" + userId + ", indicatorId=" + indicatorId + ", recordId=" + recordId + " ]";
    }
    
}
