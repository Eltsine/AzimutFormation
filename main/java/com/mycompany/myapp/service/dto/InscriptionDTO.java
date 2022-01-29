package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EtatEtudiant;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Inscription} entity.
 */
public class InscriptionDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateInscription;

    @NotNull
    private BigDecimal montantApayer;

    @NotNull
    private BigDecimal montantVerse;

    @NotNull
    private BigDecimal resteApayer;

    @NotNull
    private EtatEtudiant etatEtudiant;

    private PeriodeDTO periode;

    private EtablissementDTO etablissement;

    private AnneeScolaireDTO anneeScolaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public BigDecimal getMontantApayer() {
        return montantApayer;
    }

    public void setMontantApayer(BigDecimal montantApayer) {
        this.montantApayer = montantApayer;
    }

    public BigDecimal getMontantVerse() {
        return montantVerse;
    }

    public void setMontantVerse(BigDecimal montantVerse) {
        this.montantVerse = montantVerse;
    }

    public BigDecimal getResteApayer() {
        return resteApayer;
    }

    public void setResteApayer(BigDecimal resteApayer) {
        this.resteApayer = resteApayer;
    }

    public EtatEtudiant getEtatEtudiant() {
        return etatEtudiant;
    }

    public void setEtatEtudiant(EtatEtudiant etatEtudiant) {
        this.etatEtudiant = etatEtudiant;
    }

    public PeriodeDTO getPeriode() {
        return periode;
    }

    public void setPeriode(PeriodeDTO periode) {
        this.periode = periode;
    }

    public EtablissementDTO getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementDTO etablissement) {
        this.etablissement = etablissement;
    }

    public AnneeScolaireDTO getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(AnneeScolaireDTO anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InscriptionDTO)) {
            return false;
        }

        InscriptionDTO inscriptionDTO = (InscriptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inscriptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InscriptionDTO{" +
            "id=" + getId() +
            ", dateInscription='" + getDateInscription() + "'" +
            ", montantApayer=" + getMontantApayer() +
            ", montantVerse=" + getMontantVerse() +
            ", resteApayer=" + getResteApayer() +
            ", etatEtudiant='" + getEtatEtudiant() + "'" +
            ", periode=" + getPeriode() +
            ", etablissement=" + getEtablissement() +
            ", anneeScolaire=" + getAnneeScolaire() +
            "}";
    }
}
