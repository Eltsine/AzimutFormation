package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Session} entity.
 */
public class SessionDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateDebut;

    private ZonedDateTime dateFin;

    @NotNull
    private Integer nbreParticipant;

    private Integer nbreHeure;

    private Instant heureDebut;

    private Instant heureFin;

    @NotNull
    private String contenuFormation;

    private ModulesDTO modules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getNbreParticipant() {
        return nbreParticipant;
    }

    public void setNbreParticipant(Integer nbreParticipant) {
        this.nbreParticipant = nbreParticipant;
    }

    public Integer getNbreHeure() {
        return nbreHeure;
    }

    public void setNbreHeure(Integer nbreHeure) {
        this.nbreHeure = nbreHeure;
    }

    public Instant getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Instant heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Instant getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Instant heureFin) {
        this.heureFin = heureFin;
    }

    public String getContenuFormation() {
        return contenuFormation;
    }

    public void setContenuFormation(String contenuFormation) {
        this.contenuFormation = contenuFormation;
    }

    public ModulesDTO getModules() {
        return modules;
    }

    public void setModules(ModulesDTO modules) {
        this.modules = modules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionDTO)) {
            return false;
        }

        SessionDTO sessionDTO = (SessionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sessionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SessionDTO{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", nbreParticipant=" + getNbreParticipant() +
            ", nbreHeure=" + getNbreHeure() +
            ", heureDebut='" + getHeureDebut() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            ", contenuFormation='" + getContenuFormation() + "'" +
            ", modules=" + getModules() +
            "}";
    }
}
