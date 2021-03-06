/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sig.model.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nelson
 */
@Entity
@Table(name = "indicators_configurations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IndicatorsConfigurations.findAll", query = "SELECT i FROM IndicatorsConfigurations i"),
    @NamedQuery(name = "IndicatorsConfigurations.findByConfigurationId", query = "SELECT i FROM IndicatorsConfigurations i WHERE i.configurationId = :configurationId"),
    @NamedQuery(name = "IndicatorsConfigurations.findByVariableName", query = "SELECT i FROM IndicatorsConfigurations i WHERE i.variableName = :variableName"),
    @NamedQuery(name = "IndicatorsConfigurations.findByConfiguredValues", query = "SELECT i FROM IndicatorsConfigurations i WHERE i.configuredValues = :configuredValues"),
    @NamedQuery(name = "IndicatorsConfigurations.findByConfigurationName", query = "SELECT i FROM IndicatorsConfigurations i WHERE i.configurationName = :configurationName")})
public class IndicatorsConfigurations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "configuration_id")
    private Integer configurationId;
    @Size(max = 2147483647)
    @Column(name = "variable_name")
    private String variableName;
    @Size(max = 2147483647)
    @Column(name = "configured_values")
    private String configuredValues;
    @Size(max = 2147483647)
    @Column(name = "configuration_name")
    private String configurationName;

    public IndicatorsConfigurations() {
    }

    public IndicatorsConfigurations(Integer configurationId) {
        this.configurationId = configurationId;
    }

    public Integer getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(Integer configurationId) {
        this.configurationId = configurationId;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getConfiguredValues() {
        return configuredValues;
    }

    public void setConfiguredValues(String configuredValues) {
        this.configuredValues = configuredValues;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configurationId != null ? configurationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndicatorsConfigurations)) {
            return false;
        }
        IndicatorsConfigurations other = (IndicatorsConfigurations) object;
        if ((this.configurationId == null && other.configurationId != null) || (this.configurationId != null && !this.configurationId.equals(other.configurationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.dao.entityclasses.IndicatorsConfigurations[ configurationId=" + configurationId + " ]";
    }
    
}
