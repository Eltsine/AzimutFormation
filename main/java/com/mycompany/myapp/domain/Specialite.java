package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Specialite.
 */
@Entity
@Table(name = "specialite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Specialite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "lib_specialite", nullable = false)
    private String libSpecialite;

    @ManyToOne
    @JsonIgnoreProperties(value = { "specialites", "personnes", "etablissement" }, allowSetters = true)
    private Formateur formateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Specialite id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibSpecialite() {
        return this.libSpecialite;
    }

    public Specialite libSpecialite(String libSpecialite) {
        this.libSpecialite = libSpecialite;
        return this;
    }

    public void setLibSpecialite(String libSpecialite) {
        this.libSpecialite = libSpecialite;
    }

    public Formateur getFormateur() {
        return this.formateur;
    }

    public Specialite formateur(Formateur formateur) {
        this.setFormateur(formateur);
        return this;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Specialite)) {
            return false;
        }
        return id != null && id.equals(((Specialite) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Specialite{" +
            "id=" + getId() +
            ", libSpecialite='" + getLibSpecialite() + "'" +
            "}";
    }
}
