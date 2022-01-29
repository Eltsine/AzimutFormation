package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.NomFormation;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Formation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nom_formation", nullable = false)
    private NomFormation nomFormation;

    @JsonIgnoreProperties(value = { "formation" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Rapport rapport;

    @OneToMany(mappedBy = "formation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sessions", "formation" }, allowSetters = true)
    private Set<Modules> modules = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "periode", "formations", "etablissement", "anneeScolaire" }, allowSetters = true)
    private Inscription inscription;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Formation id(Long id) {
        this.id = id;
        return this;
    }

    public NomFormation getNomFormation() {
        return this.nomFormation;
    }

    public Formation nomFormation(NomFormation nomFormation) {
        this.nomFormation = nomFormation;
        return this;
    }

    public void setNomFormation(NomFormation nomFormation) {
        this.nomFormation = nomFormation;
    }

    public Rapport getRapport() {
        return this.rapport;
    }

    public Formation rapport(Rapport rapport) {
        this.setRapport(rapport);
        return this;
    }

    public void setRapport(Rapport rapport) {
        this.rapport = rapport;
    }

    public Set<Modules> getModules() {
        return this.modules;
    }

    public Formation modules(Set<Modules> modules) {
        this.setModules(modules);
        return this;
    }

    public Formation addModules(Modules modules) {
        this.modules.add(modules);
        modules.setFormation(this);
        return this;
    }

    public Formation removeModules(Modules modules) {
        this.modules.remove(modules);
        modules.setFormation(null);
        return this;
    }

    public void setModules(Set<Modules> modules) {
        if (this.modules != null) {
            this.modules.forEach(i -> i.setFormation(null));
        }
        if (modules != null) {
            modules.forEach(i -> i.setFormation(this));
        }
        this.modules = modules;
    }

    public Inscription getInscription() {
        return this.inscription;
    }

    public Formation inscription(Inscription inscription) {
        this.setInscription(inscription);
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formation)) {
            return false;
        }
        return id != null && id.equals(((Formation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formation{" +
            "id=" + getId() +
            ", nomFormation='" + getNomFormation() + "'" +
            "}";
    }
}
