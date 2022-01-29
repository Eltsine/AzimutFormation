package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Apprenant} entity.
 */
public class ApprenantDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] photo;

    private String photoContentType;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    private String statut;

    @NotNull
    private String niveau;

    private Boolean etatStud;

    @NotNull
    private String contact;

    private String email;

    private String addParent;

    private InscriptionDTO inscription;

    private FormateurDTO formateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Boolean getEtatStud() {
        return etatStud;
    }

    public void setEtatStud(Boolean etatStud) {
        this.etatStud = etatStud;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddParent() {
        return addParent;
    }

    public void setAddParent(String addParent) {
        this.addParent = addParent;
    }

    public InscriptionDTO getInscription() {
        return inscription;
    }

    public void setInscription(InscriptionDTO inscription) {
        this.inscription = inscription;
    }

    public FormateurDTO getFormateur() {
        return formateur;
    }

    public void setFormateur(FormateurDTO formateur) {
        this.formateur = formateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApprenantDTO)) {
            return false;
        }

        ApprenantDTO apprenantDTO = (ApprenantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, apprenantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprenantDTO{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", statut='" + getStatut() + "'" +
            ", niveau='" + getNiveau() + "'" +
            ", etatStud='" + getEtatStud() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            ", addParent='" + getAddParent() + "'" +
            ", inscription=" + getInscription() +
            ", formateur=" + getFormateur() +
            "}";
    }
}
