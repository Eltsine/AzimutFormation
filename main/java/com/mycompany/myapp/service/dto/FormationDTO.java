package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.NomFormation;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Formation} entity.
 */
public class FormationDTO implements Serializable {

    private Long id;

    @NotNull
    private NomFormation nomFormation;

    private RapportDTO rapport;

    private InscriptionDTO inscription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NomFormation getNomFormation() {
        return nomFormation;
    }

    public void setNomFormation(NomFormation nomFormation) {
        this.nomFormation = nomFormation;
    }

    public RapportDTO getRapport() {
        return rapport;
    }

    public void setRapport(RapportDTO rapport) {
        this.rapport = rapport;
    }

    public InscriptionDTO getInscription() {
        return inscription;
    }

    public void setInscription(InscriptionDTO inscription) {
        this.inscription = inscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormationDTO)) {
            return false;
        }

        FormationDTO formationDTO = (FormationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, formationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormationDTO{" +
            "id=" + getId() +
            ", nomFormation='" + getNomFormation() + "'" +
            ", rapport=" + getRapport() +
            ", inscription=" + getInscription() +
            "}";
    }
}
