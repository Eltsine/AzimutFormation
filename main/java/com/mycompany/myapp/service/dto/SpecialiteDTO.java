package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Specialite} entity.
 */
public class SpecialiteDTO implements Serializable {

    private Long id;

    @NotNull
    private String libSpecialite;

    private FormateurDTO formateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibSpecialite() {
        return libSpecialite;
    }

    public void setLibSpecialite(String libSpecialite) {
        this.libSpecialite = libSpecialite;
    }

    public FormateurDTO getFormateur() {
        return formateur;
    }

    public void setFormateur(FormateurDTO formateur) {
        this.formateur = formateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpecialiteDTO)) {
            return false;
        }

        SpecialiteDTO specialiteDTO = (SpecialiteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, specialiteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecialiteDTO{" +
            "id=" + getId() +
            ", libSpecialite='" + getLibSpecialite() + "'" +
            ", formateur=" + getFormateur() +
            "}";
    }
}
