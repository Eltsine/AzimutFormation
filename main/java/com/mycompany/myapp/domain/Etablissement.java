package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Etablissement.
 */
@Entity
@Table(name = "etablissement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Etablissement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "adresse")
    private String adresse;

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "periode", "formations", "etablissement", "anneeScolaire" }, allowSetters = true)
    private Set<Inscription> inscriptions = new HashSet<>();

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "specialites", "personnes", "etablissement" }, allowSetters = true)
    private Set<Formateur> formateurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Etablissement id(Long id) {
        this.id = id;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public Etablissement nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Etablissement adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Set<Inscription> getInscriptions() {
        return this.inscriptions;
    }

    public Etablissement inscriptions(Set<Inscription> inscriptions) {
        this.setInscriptions(inscriptions);
        return this;
    }

    public Etablissement addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setEtablissement(this);
        return this;
    }

    public Etablissement removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setEtablissement(null);
        return this;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        if (this.inscriptions != null) {
            this.inscriptions.forEach(i -> i.setEtablissement(null));
        }
        if (inscriptions != null) {
            inscriptions.forEach(i -> i.setEtablissement(this));
        }
        this.inscriptions = inscriptions;
    }

    public Set<Formateur> getFormateurs() {
        return this.formateurs;
    }

    public Etablissement formateurs(Set<Formateur> formateurs) {
        this.setFormateurs(formateurs);
        return this;
    }

    public Etablissement addFormateur(Formateur formateur) {
        this.formateurs.add(formateur);
        formateur.setEtablissement(this);
        return this;
    }

    public Etablissement removeFormateur(Formateur formateur) {
        this.formateurs.remove(formateur);
        formateur.setEtablissement(null);
        return this;
    }

    public void setFormateurs(Set<Formateur> formateurs) {
        if (this.formateurs != null) {
            this.formateurs.forEach(i -> i.setEtablissement(null));
        }
        if (formateurs != null) {
            formateurs.forEach(i -> i.setEtablissement(this));
        }
        this.formateurs = formateurs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etablissement)) {
            return false;
        }
        return id != null && id.equals(((Etablissement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etablissement{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
