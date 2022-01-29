package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnneeScolaire.
 */
@Entity
@Table(name = "annee_scolaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnneeScolaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle_annee")
    private String libelleAnnee;

    @OneToMany(mappedBy = "anneeScolaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "periode", "formations", "etablissement", "anneeScolaire" }, allowSetters = true)
    private Set<Inscription> inscriptions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnneeScolaire id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibelleAnnee() {
        return this.libelleAnnee;
    }

    public AnneeScolaire libelleAnnee(String libelleAnnee) {
        this.libelleAnnee = libelleAnnee;
        return this;
    }

    public void setLibelleAnnee(String libelleAnnee) {
        this.libelleAnnee = libelleAnnee;
    }

    public Set<Inscription> getInscriptions() {
        return this.inscriptions;
    }

    public AnneeScolaire inscriptions(Set<Inscription> inscriptions) {
        this.setInscriptions(inscriptions);
        return this;
    }

    public AnneeScolaire addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setAnneeScolaire(this);
        return this;
    }

    public AnneeScolaire removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setAnneeScolaire(null);
        return this;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        if (this.inscriptions != null) {
            this.inscriptions.forEach(i -> i.setAnneeScolaire(null));
        }
        if (inscriptions != null) {
            inscriptions.forEach(i -> i.setAnneeScolaire(this));
        }
        this.inscriptions = inscriptions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnneeScolaire)) {
            return false;
        }
        return id != null && id.equals(((AnneeScolaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnneeScolaire{" +
            "id=" + getId() +
            ", libelleAnnee='" + getLibelleAnnee() + "'" +
            "}";
    }
}
