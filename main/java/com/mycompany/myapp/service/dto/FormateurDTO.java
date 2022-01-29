package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Formateur} entity.
 */
public class FormateurDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] photo;

    private String photoContentType;

    @NotNull
    private String cnib;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    private String statut;

    @NotNull
    private String contact;

    private String email;

    private BigDecimal salaireHoraire;

    private BigDecimal salaireMensuel;

    private EtablissementDTO etablissement;

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

    public String getCnib() {
        return cnib;
    }

    public void setCnib(String cnib) {
        this.cnib = cnib;
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

    public BigDecimal getSalaireHoraire() {
        return salaireHoraire;
    }

    public void setSalaireHoraire(BigDecimal salaireHoraire) {
        this.salaireHoraire = salaireHoraire;
    }

    public BigDecimal getSalaireMensuel() {
        return salaireMensuel;
    }

    public void setSalaireMensuel(BigDecimal salaireMensuel) {
        this.salaireMensuel = salaireMensuel;
    }

    public EtablissementDTO getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementDTO etablissement) {
        this.etablissement = etablissement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormateurDTO)) {
            return false;
        }

        FormateurDTO formateurDTO = (FormateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, formateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormateurDTO{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", cnib='" + getCnib() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", statut='" + getStatut() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            ", salaireHoraire=" + getSalaireHoraire() +
            ", salaireMensuel=" + getSalaireMensuel() +
            ", etablissement=" + getEtablissement() +
            "}";
    }
}
