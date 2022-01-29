package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.NomModules;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Modules.
 */
@Entity
@Table(name = "modules")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Modules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nom_module", nullable = false)
    private NomModules nomModule;

    @Column(name = "prix", precision = 21, scale = 2)
    private BigDecimal prix;

    @OneToMany(mappedBy = "modules")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "modules" }, allowSetters = true)
    private Set<Session> sessions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "rapport", "modules", "inscription" }, allowSetters = true)
    private Formation formation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Modules id(Long id) {
        this.id = id;
        return this;
    }

    public NomModules getNomModule() {
        return this.nomModule;
    }

    public Modules nomModule(NomModules nomModule) {
        this.nomModule = nomModule;
        return this;
    }

    public void setNomModule(NomModules nomModule) {
        this.nomModule = nomModule;
    }

    public BigDecimal getPrix() {
        return this.prix;
    }

    public Modules prix(BigDecimal prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public Set<Session> getSessions() {
        return this.sessions;
    }

    public Modules sessions(Set<Session> sessions) {
        this.setSessions(sessions);
        return this;
    }

    public Modules addSession(Session session) {
        this.sessions.add(session);
        session.setModules(this);
        return this;
    }

    public Modules removeSession(Session session) {
        this.sessions.remove(session);
        session.setModules(null);
        return this;
    }

    public void setSessions(Set<Session> sessions) {
        if (this.sessions != null) {
            this.sessions.forEach(i -> i.setModules(null));
        }
        if (sessions != null) {
            sessions.forEach(i -> i.setModules(this));
        }
        this.sessions = sessions;
    }

    public Formation getFormation() {
        return this.formation;
    }

    public Modules formation(Formation formation) {
        this.setFormation(formation);
        return this;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Modules)) {
            return false;
        }
        return id != null && id.equals(((Modules) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Modules{" +
            "id=" + getId() +
            ", nomModule='" + getNomModule() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
