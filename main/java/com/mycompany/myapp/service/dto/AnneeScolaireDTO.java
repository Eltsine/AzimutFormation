package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.AnneeScolaire} entity.
 */
public class AnneeScolaireDTO implements Serializable {

    private Long id;

    private String libelleAnnee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleAnnee() {
        return libelleAnnee;
    }

    public void setLibelleAnnee(String libelleAnnee) {
        this.libelleAnnee = libelleAnnee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnneeScolaireDTO)) {
            return false;
        }

        AnneeScolaireDTO anneeScolaireDTO = (AnneeScolaireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anneeScolaireDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnneeScolaireDTO{" +
            "id=" + getId() +
            ", libelleAnnee='" + getLibelleAnnee() + "'" +
            "}";
    }
}
