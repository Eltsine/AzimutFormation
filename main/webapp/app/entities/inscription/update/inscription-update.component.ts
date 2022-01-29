import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IInscription, Inscription } from '../inscription.model';
import { InscriptionService } from '../service/inscription.service';
import { IPeriode } from 'app/entities/periode/periode.model';
import { PeriodeService } from 'app/entities/periode/service/periode.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { IAnneeScolaire } from 'app/entities/annee-scolaire/annee-scolaire.model';
import { AnneeScolaireService } from 'app/entities/annee-scolaire/service/annee-scolaire.service';

@Component({
  selector: 'jhi-inscription-update',
  templateUrl: './inscription-update.component.html',
})
export class InscriptionUpdateComponent implements OnInit {
  isSaving = false;

  periodesCollection: IPeriode[] = [];
  etablissementsSharedCollection: IEtablissement[] = [];
  anneeScolairesSharedCollection: IAnneeScolaire[] = [];

  editForm = this.fb.group({
    id: [],
    dateInscription: [null, [Validators.required]],
    montantApayer: [null, [Validators.required]],
    montantVerse: [null, [Validators.required]],
    resteApayer: [null, [Validators.required]],
    etatEtudiant: [null, [Validators.required]],
    periode: [],
    etablissement: [],
    anneeScolaire: [],
  });

  constructor(
    protected inscriptionService: InscriptionService,
    protected periodeService: PeriodeService,
    protected etablissementService: EtablissementService,
    protected anneeScolaireService: AnneeScolaireService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inscription }) => {
      this.updateForm(inscription);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inscription = this.createFromForm();
    if (inscription.id !== undefined) {
      this.subscribeToSaveResponse(this.inscriptionService.update(inscription));
    } else {
      this.subscribeToSaveResponse(this.inscriptionService.create(inscription));
    }
  }

  trackPeriodeById(index: number, item: IPeriode): number {
    return item.id!;
  }

  trackEtablissementById(index: number, item: IEtablissement): number {
    return item.id!;
  }

  trackAnneeScolaireById(index: number, item: IAnneeScolaire): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInscription>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(inscription: IInscription): void {
    this.editForm.patchValue({
      id: inscription.id,
      dateInscription: inscription.dateInscription,
      montantApayer: inscription.montantApayer,
      montantVerse: inscription.montantVerse,
      resteApayer: inscription.resteApayer,
      etatEtudiant: inscription.etatEtudiant,
      periode: inscription.periode,
      etablissement: inscription.etablissement,
      anneeScolaire: inscription.anneeScolaire,
    });

    this.periodesCollection = this.periodeService.addPeriodeToCollectionIfMissing(this.periodesCollection, inscription.periode);
    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing(
      this.etablissementsSharedCollection,
      inscription.etablissement
    );
    this.anneeScolairesSharedCollection = this.anneeScolaireService.addAnneeScolaireToCollectionIfMissing(
      this.anneeScolairesSharedCollection,
      inscription.anneeScolaire
    );
  }

  protected loadRelationshipsOptions(): void {
    this.periodeService
      .query({ filter: 'inscription-is-null' })
      .pipe(map((res: HttpResponse<IPeriode[]>) => res.body ?? []))
      .pipe(
        map((periodes: IPeriode[]) => this.periodeService.addPeriodeToCollectionIfMissing(periodes, this.editForm.get('periode')!.value))
      )
      .subscribe((periodes: IPeriode[]) => (this.periodesCollection = periodes));

    this.etablissementService
      .query()
      .pipe(map((res: HttpResponse<IEtablissement[]>) => res.body ?? []))
      .pipe(
        map((etablissements: IEtablissement[]) =>
          this.etablissementService.addEtablissementToCollectionIfMissing(etablissements, this.editForm.get('etablissement')!.value)
        )
      )
      .subscribe((etablissements: IEtablissement[]) => (this.etablissementsSharedCollection = etablissements));

    this.anneeScolaireService
      .query()
      .pipe(map((res: HttpResponse<IAnneeScolaire[]>) => res.body ?? []))
      .pipe(
        map((anneeScolaires: IAnneeScolaire[]) =>
          this.anneeScolaireService.addAnneeScolaireToCollectionIfMissing(anneeScolaires, this.editForm.get('anneeScolaire')!.value)
        )
      )
      .subscribe((anneeScolaires: IAnneeScolaire[]) => (this.anneeScolairesSharedCollection = anneeScolaires));
  }

  protected createFromForm(): IInscription {
    return {
      ...new Inscription(),
      id: this.editForm.get(['id'])!.value,
      dateInscription: this.editForm.get(['dateInscription'])!.value,
      montantApayer: this.editForm.get(['montantApayer'])!.value,
      montantVerse: this.editForm.get(['montantVerse'])!.value,
      resteApayer: this.editForm.get(['resteApayer'])!.value,
      etatEtudiant: this.editForm.get(['etatEtudiant'])!.value,
      periode: this.editForm.get(['periode'])!.value,
      etablissement: this.editForm.get(['etablissement'])!.value,
      anneeScolaire: this.editForm.get(['anneeScolaire'])!.value,
    };
  }
}
