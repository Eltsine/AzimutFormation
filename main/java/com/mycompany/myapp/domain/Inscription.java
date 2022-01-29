package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EtatEtudiant;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Inscription.
 */
@Entity
@Table(name = "inscription")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_inscription", nullable = false)
    private LocalDate dateInscription;

    @NotNull
    @Column(name = "montant_apayer", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantApayer;

    @NotNull
    @Column(name = "montant_verse", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantVerse;

    @NotNull
    @Column(name = "reste_apayer", precision = 21, scale = 2, nullable = false)
    private BigDecimal resteApayer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat_etudiant", nullable = false)
    private EtatEtudiant etatEtudiant;

    @OneToOne
    @JoinColumn(unique = true)
    private Periode periode;

    @OneToMany(mappedBy = "inscription")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rapport", "modules", "inscription" }, allowSetters = true)
    private Set<Formation> formations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "inscriptions", "formateurs" }, allowSetters = true)
    private Etablissement etablissement;

    @ManyToOne
    @JsonIgnoreProperties(value = { "inscriptions" }, allowSetters = true)
    private AnneeScolaire anneeScolaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inscription id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDateInscription() {
        return this.dateInscription;
    }

    public Inscription dateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
        return this;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public BigDecimal getMontantApayer() {
        return this.montantApayer;
    }

    public Inscription montantApayer(BigDecimal montantApayer) {
        this.montantApayer = montantApayer;
        return this;
    }

    public void setMontantApayer(BigDecimal montantApayer) {
        this.montantApayer = montantApayer;
    }

    public BigDecimal getMontantVerse() {
        return this.montantVerse;
    }

    public Inscription montantVerse(BigDecimal montantVerse) {
        this.montantVerse = montantVerse;
        return this;
    }

    public void setMontantVerse(BigDecimal montantVerse) {
        this.montantVerse = montantVerse;
    }

    public BigDecimal getResteApayer() {
        return this.resteApayer;
    }

    public Inscription resteApayer(BigDecimal resteApayer) {
        this.resteApayer = resteApayer;
        return this;
    }

    public void setResteApayer(BigDecimal resteApayer) {
        this.resteApayer = resteApayer;
    }

    public EtatEtudiant getEtatEtudiant() {
        return this.etatEtudiant;
    }

    public Inscription etatEtudiant(EtatEtudiant etatEtudiant) {
        this.etatEtudiant = etatEtudiant;
        return this;
    }

    public void setEtatEtudiant(EtatEtudiant etatEtudiant) {
        this.etatEtudiant = etatEtudiant;
    }

    public Periode getPeriode() {
        return this.periode;
    }

    public Inscription periode(Periode periode) {
        this.setPeriode(periode);
        return this;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public Set<Formation> getFormations() {
        return this.formations;
    }

    public Inscription formations(Set<Formation> formations) {
        this.setFormations(formations);
        return this;
    }

    public Inscription addFormation(Formation formation) {
        this.formations.add(formation);
        formation.setInscription(this);
        return this;
    }

    public Inscription removeFormation(Formation formation) {
        this.formations.remove(formation);
        formation.setInscription(null);
        return this;
    }

    public void setFormations(Set<Formation> formations) {
        if (this.formations != null) {
            this.formations.forEach(i -> i.setInscription(null));
        }
        if (formations != null) {
            formations.forEach(i -> i.setInscription(this));
        }
        this.formations = formations;
    }

    public Etablissement getEtablissement() {
        return this.etablissement;
    }

    public Inscription etablissement(Etablissement etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public AnneeScolaire getAnneeScolaire() {
        return this.anneeScolaire;
    }

    public Inscription anneeScolaire(AnneeScolaire anneeScolaire) {
        this.setAnneeScolaire(anneeScolaire);
        return this;
    }

    public void setAnneeScolaire(AnneeScolaire anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inscription)) {
            return false;
        }
        return id != null && id.equals(((Inscription) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inscription{" +
            "id=" + getId() +
            ", dateInscription='" + getDateInscription() + "'" +
            ", montantApayer=" + getMontantApayer() +
            ", montantVerse=" + getMontantVerse() +
            ", resteApayer=" + getResteApayer() +
            ", etatEtudiant='" + getEtatEtudiant() + "'" +
            "}";
    }
}
