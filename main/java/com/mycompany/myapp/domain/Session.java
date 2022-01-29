package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Session.
 */
@Entity
@Table(name = "session")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_debut")
    private ZonedDateTime dateDebut;

    @Column(name = "date_fin")
    private ZonedDateTime dateFin;

    @NotNull
    @Column(name = "nbre_participant", nullable = false)
    private Integer nbreParticipant;

    @Column(name = "nbre_heure")
    private Integer nbreHeure;

    @Column(name = "heure_debut")
    private Instant heureDebut;

    @Column(name = "heure_fin")
    private Instant heureFin;

    @NotNull
    @Column(name = "contenu_formation", nullable = false)
    private String contenuFormation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "sessions", "formation" }, allowSetters = true)
    private Modules modules;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Session id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getDateDebut() {
        return this.dateDebut;
    }

    public Session dateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return this.dateFin;
    }

    public Session dateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getNbreParticipant() {
        return this.nbreParticipant;
    }

    public Session nbreParticipant(Integer nbreParticipant) {
        this.nbreParticipant = nbreParticipant;
        return this;
    }

    public void setNbreParticipant(Integer nbreParticipant) {
        this.nbreParticipant = nbreParticipant;
    }

    public Integer getNbreHeure() {
        return this.nbreHeure;
    }

    public Session nbreHeure(Integer nbreHeure) {
        this.nbreHeure = nbreHeure;
        return this;
    }

    public void setNbreHeure(Integer nbreHeure) {
        this.nbreHeure = nbreHeure;
    }

    public Instant getHeureDebut() {
        return this.heureDebut;
    }

    public Session heureDebut(Instant heureDebut) {
        this.heureDebut = heureDebut;
        return this;
    }

    public void setHeureDebut(Instant heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Instant getHeureFin() {
        return this.heureFin;
    }

    public Session heureFin(Instant heureFin) {
        this.heureFin = heureFin;
        return this;
    }

    public void setHeureFin(Instant heureFin) {
        this.heureFin = heureFin;
    }

    public String getContenuFormation() {
        return this.contenuFormation;
    }

    public Session contenuFormation(String contenuFormation) {
        this.contenuFormation = contenuFormation;
        return this;
    }

    public void setContenuFormation(String contenuFormation) {
        this.contenuFormation = contenuFormation;
    }

    public Modules getModules() {
        return this.modules;
    }

    public Session modules(Modules modules) {
        this.setModules(modules);
        return this;
    }

    public void setModules(Modules modules) {
        this.modules = modules;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        return id != null && id.equals(((Session) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", nbreParticipant=" + getNbreParticipant() +
            ", nbreHeure=" + getNbreHeure() +
            ", heureDebut='" + getHeureDebut() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            ", contenuFormation='" + getContenuFormation() + "'" +
            "}";
    }
}
