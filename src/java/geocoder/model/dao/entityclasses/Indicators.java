/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geocoder.model.dao.entityclasses;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nelson
 */
@Entity
@Table(name = "indicators")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Indicators.findAll", query = "SELECT i FROM Indicators i"),
    @NamedQuery(name = "Indicators.findByIndicatorId", query = "SELECT i FROM Indicators i WHERE i.indicatorId = :indicatorId"),
    @NamedQuery(name = "Indicators.findByIndicatorName", query = "SELECT i FROM Indicators i WHERE i.indicatorName = :indicatorName"),
    @NamedQuery(name = "Indicators.findByIndicatorGroup", query = "SELECT i FROM Indicators i WHERE i.indicatorGroup = :indicatorGroup"),
    @NamedQuery(name = "Indicators.findByGraphType", query = "SELECT i FROM Indicators i WHERE i.graphType = :graphType"),
    @NamedQuery(name = "Indicators.findByNumberCross", query = "SELECT i FROM Indicators i WHERE i.numberCross = :numberCross"),
    @NamedQuery(name = "Indicators.findByInjuryType", query = "SELECT i FROM Indicators i WHERE i.injuryType = :injuryType"),
    @NamedQuery(name = "Indicators.findByIndicatorType", query = "SELECT i FROM Indicators i WHERE i.indicatorType = :indicatorType"),
    @NamedQuery(name = "Indicators.findByInjuryId", query = "SELECT i FROM Indicators i WHERE i.injuryId = :injuryId")})
public class Indicators implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "indicator_id")
    private Integer indicatorId;
    @Size(max = 2147483647)
    @Column(name = "indicator_name")
    private String indicatorName;
    @Size(max = 2147483647)
    @Column(name = "indicator_group")
    private String indicatorGroup;
    @Size(max = 2147483647)
    @Column(name = "graph_type")
    private String graphType;
    @Column(name = "number_cross")
    private Integer numberCross;
    @Size(max = 2147483647)
    @Column(name = "injury_type")
    private String injuryType;
    @Size(max = 30)
    @Column(name = "indicator_type")
    private String indicatorType;
    @Column(name = "injury_id")
    private Short injuryId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "indicators")
    private List<IndicatorsVariables> indicatorsVariablesList;

    public Indicators() {
    }

    public Indicators(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getIndicatorGroup() {
        return indicatorGroup;
    }

    public void setIndicatorGroup(String indicatorGroup) {
        this.indicatorGroup = indicatorGroup;
    }

    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    public Integer getNumberCross() {
        return numberCross;
    }

    public void setNumberCross(Integer numberCross) {
        this.numberCross = numberCross;
    }

    public String getInjuryType() {
        return injuryType;
    }

    public void setInjuryType(String injuryType) {
        this.injuryType = injuryType;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public Short getInjuryId() {
        return injuryId;
    }

    public void setInjuryId(Short injuryId) {
        this.injuryId = injuryId;
    }

     @XmlTransient
    public List<IndicatorsVariables> getIndicatorsVariablesList() {
        return indicatorsVariablesList;
    }

    public void setIndicatorsVariablesList(List<IndicatorsVariables> indicatorsVariablesList) {
        this.indicatorsVariablesList = indicatorsVariablesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indicatorId != null ? indicatorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Indicators)) {
            return false;
        }
        Indicators other = (Indicators) object;
        if ((this.indicatorId == null && other.indicatorId != null) || (this.indicatorId != null && !this.indicatorId.equals(other.indicatorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "newpackage.Indicators[ indicatorId=" + indicatorId + " ]";
    }
    
}
