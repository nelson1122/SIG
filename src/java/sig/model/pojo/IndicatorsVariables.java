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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "indicators_variables")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IndicatorsVariables.findAll", query = "SELECT i FROM IndicatorsVariables i"),
    @NamedQuery(name = "IndicatorsVariables.findByVariableName", query = "SELECT i FROM IndicatorsVariables i WHERE i.indicatorsVariablesPK.variableName = :variableName"),
    @NamedQuery(name = "IndicatorsVariables.findByAddValues", query = "SELECT i FROM IndicatorsVariables i WHERE i.addValues = :addValues"),
    @NamedQuery(name = "IndicatorsVariables.findByCategory", query = "SELECT i FROM IndicatorsVariables i WHERE i.category = :category"),
    @NamedQuery(name = "IndicatorsVariables.findByIndicatorId", query = "SELECT i FROM IndicatorsVariables i WHERE i.indicatorsVariablesPK.indicatorId = :indicatorId"),
    @NamedQuery(name = "IndicatorsVariables.findBySourceTable", query = "SELECT i FROM IndicatorsVariables i WHERE i.sourceTable = :sourceTable")})
public class IndicatorsVariables implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IndicatorsVariablesPK indicatorsVariablesPK;
    @Column(name = "add_values")
    private Boolean addValues;
    @Size(max = 2147483647)
    @Column(name = "category")
    private String category;
    @Size(max = 2147483647)
    @Column(name = "source_table")
    private String sourceTable;
    @JoinColumn(name = "indicator_id", referencedColumnName = "indicator_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Indicators indicators;

    public IndicatorsVariables() {
    }

    public IndicatorsVariables(IndicatorsVariablesPK indicatorsVariablesPK) {
        this.indicatorsVariablesPK = indicatorsVariablesPK;
    }

    public IndicatorsVariables(String variableName, int indicatorId) {
        this.indicatorsVariablesPK = new IndicatorsVariablesPK(variableName, indicatorId);
    }

    public IndicatorsVariablesPK getIndicatorsVariablesPK() {
        return indicatorsVariablesPK;
    }

    public void setIndicatorsVariablesPK(IndicatorsVariablesPK indicatorsVariablesPK) {
        this.indicatorsVariablesPK = indicatorsVariablesPK;
    }

    public Boolean getAddValues() {
        return addValues;
    }

    public void setAddValues(Boolean addValues) {
        this.addValues = addValues;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public Indicators getIndicators() {
        return indicators;
    }

    public void setIndicators(Indicators indicators) {
        this.indicators = indicators;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indicatorsVariablesPK != null ? indicatorsVariablesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndicatorsVariables)) {
            return false;
        }
        IndicatorsVariables other = (IndicatorsVariables) object;
        if ((this.indicatorsVariablesPK == null && other.indicatorsVariablesPK != null) || (this.indicatorsVariablesPK != null && !this.indicatorsVariablesPK.equals(other.indicatorsVariablesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.dao.entityclasses.IndicatorsVariables[ indicatorsVariablesPK=" + indicatorsVariablesPK + " ]";
    }
    
}
