package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Apprenant.
 */
@Entity
@Table(name = "apprenant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Apprenant implements Serializable {

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
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "statut", nullable = false)
    private String statut;

    @NotNull
    @Column(name = "niveau", nullable = false)
    private String niveau;

    @Column(name = "etat_stud")
    private Boolean etatStud;

    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "email")
    private String email;

    @Column(name = "add_parent")
    private String addParent;

    @ManyToOne
    @JsonIgnoreProperties(value = { "periode", "formations", "etablissement", "anneeScolaire" }, allowSetters = true)
    private Inscription inscription;

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

    public Apprenant id(Long id) {
        this.id = id;
        return this;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Apprenant photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Apprenant photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getNom() {
        return this.nom;
    }

    public Apprenant nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Apprenant prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getStatut() {
        return this.statut;
    }

    public Apprenant statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getNiveau() {
        return this.niveau;
    }

    public Apprenant niveau(String niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Boolean getEtatStud() {
        return this.etatStud;
    }

    public Apprenant etatStud(Boolean etatStud) {
        this.etatStud = etatStud;
        return this;
    }

    public void setEtatStud(Boolean etatStud) {
        this.etatStud = etatStud;
    }

    public String getContact() {
        return this.contact;
    }

    public Apprenant contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return this.email;
    }

    public Apprenant email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddParent() {
        return this.addParent;
    }

    public Apprenant addParent(String addParent) {
        this.addParent = addParent;
        return this;
    }

    public void setAddParent(String addParent) {
        this.addParent = addParent;
    }

    public Inscription getInscription() {
        return this.inscription;
    }

    public Apprenant inscription(Inscription inscription) {
        this.setInscription(inscription);
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Formateur getFormateur() {
        return this.formateur;
    }

    public Apprenant formateur(Formateur formateur) {
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
        if (!(o instanceof Apprenant)) {
            return false;
        }
        return id != null && id.equals(((Apprenant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apprenant{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", statut='" + getStatut() + "'" +
            ", niveau='" + getNiveau() + "'" +
            ", etatStud='" + getEtatStud() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            ", addParent='" + getAddParent() + "'" +
            "}";
    }
}
