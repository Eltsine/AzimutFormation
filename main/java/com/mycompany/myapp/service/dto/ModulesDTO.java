package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.NomModules;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Modules} entity.
 */
public class ModulesDTO implements Serializable {

    private Long id;

    @NotNull
    private NomModules nomModule;

    private BigDecimal prix;

    private FormationDTO formation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NomModules getNomModule() {
        return nomModule;
    }

    public void setNomModule(NomModules nomModule) {
        this.nomModule = nomModule;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public FormationDTO getFormation() {
        return formation;
    }

    public void setFormation(FormationDTO formation) {
        this.formation = formation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModulesDTO)) {
            return false;
        }

        ModulesDTO modulesDTO = (ModulesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, modulesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModulesDTO{" +
            "id=" + getId() +
            ", nomModule='" + getNomModule() + "'" +
            ", prix=" + getPrix() +
            ", formation=" + getFormation() +
            "}";
    }
}
