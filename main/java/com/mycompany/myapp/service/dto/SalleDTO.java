package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Salle} entity.
 */
public class SalleDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomSalle;

    private Integer capacite;

    private SessionDTO session;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public SessionDTO getSession() {
        return session;
    }

    public void setSession(SessionDTO session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalleDTO)) {
            return false;
        }

        SalleDTO salleDTO = (SalleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, salleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalleDTO{" +
            "id=" + getId() +
            ", nomSalle='" + getNomSalle() + "'" +
            ", capacite=" + getCapacite() +
            ", session=" + getSession() +
            "}";
    }
}
