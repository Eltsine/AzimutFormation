package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Formateur.
 */
@Entity
@Table(name = "formateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Formateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Column(name = "cnib", nullable = false, unique = true)
    private String cnib;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "statut")
    private String statut;

    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "email")
    private String email;

    @Column(name = "salaire_horaire", precision = 21, scale = 2)
    private BigDecimal salaireHoraire;

    @Column(name = "salaire_mensuel", precision = 21, scale = 2)
    private BigDecimal salaireMensuel;

    @OneToMany(mappedBy = "formateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "formateur" }, allowSetters = true)
    private Set<Specialite> specialites = new HashSet<>();

    @OneToMany(mappedBy = "formateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inscription", "formateur" }, allowSetters = true)
    private Set<Apprenant> personnes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "inscriptions", "formateurs" }, allowSetters = true)
    private Etablissement etablissement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Formateur id(Long id) {
        this.id = id;
        return this;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Formateur photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Formateur photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getCnib() {
        return this.cnib;
    }

    public Formateur cnib(String cnib) {
        this.cnib = cnib;
        return this;
    }

    public void setCnib(String cnib) {
        this.cnib = cnib;
    }

    public String getNom() {
        return this.nom;
    }

    public Formateur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Formateur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getStatut() {
        return this.statut;
    }

    public Formateur statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getContact() {
        return this.contact;
    }

    public Formateur contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return this.email;
    }

    public Formateur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalaireHoraire() {
        return this.salaireHoraire;
    }

    public Formateur salaireHoraire(BigDecimal salaireHoraire) {
        this.salaireHoraire = salaireHoraire;
        return this;
    }

    public void setSalaireHoraire(BigDecimal salaireHoraire) {
        this.salaireHoraire = salaireHoraire;
    }

    public BigDecimal getSalaireMensuel() {
        return this.salaireMensuel;
    }

    public Formateur salaireMensuel(BigDecimal salaireMensuel) {
        this.salaireMensuel = salaireMensuel;
        return this;
    }

    public void setSalaireMensuel(BigDecimal salaireMensuel) {
        this.salaireMensuel = salaireMensuel;
    }

    public Set<Specialite> getSpecialites() {
        return this.specialites;
    }

    public Formateur specialites(Set<Specialite> specialites) {
        this.setSpecialites(specialites);
        return this;
    }

    public Formateur addSpecialite(Specialite specialite) {
        this.specialites.add(specialite);
        specialite.setFormateur(this);
        return this;
    }

    public Formateur removeSpecialite(Specialite specialite) {
        this.specialites.remove(specialite);
        specialite.setFormateur(null);
        return this;
    }

    public void setSpecialites(Set<Specialite> specialites) {
        if (this.specialites != null) {
            this.specialites.forEach(i -> i.setFormateur(null));
        }
        if (specialites != null) {
            specialites.forEach(i -> i.setFormateur(this));
        }
        this.specialites = specialites;
    }

    public Set<Apprenant> getPersonnes() {
        return this.personnes;
    }

    public Formateur personnes(Set<Apprenant> apprenants) {
        this.setPersonnes(apprenants);
        return this;
    }

    public Formateur addPersonne(Apprenant apprenant) {
        this.personnes.add(apprenant);
        apprenant.setFormateur(this);
        return this;
    }

    public Formateur removePersonne(Apprenant apprenant) {
        this.personnes.remove(apprenant);
        apprenant.setFormateur(null);
        return this;
    }

    public void setPersonnes(Set<Apprenant> apprenants) {
        if (this.personnes != null) {
            this.personnes.forEach(i -> i.setFormateur(null));
        }
        if (apprenants != null) {
            apprenants.forEach(i -> i.setFormateur(this));
        }
        this.personnes = apprenants;
    }

    public Etablissement getEtablissement() {
        return this.etablissement;
    }

    public Formateur etablissement(Etablissement etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formateur)) {
            return false;
        }
        return id != null && id.equals(((Formateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formateur{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", cnib='" + getCnib() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", statut='" + getStatut() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            ", salaireHoraire=" + getSalaireHoraire() +
            ", salaireMensuel=" + getSalaireMensuel() +
            "}";
    }
}
