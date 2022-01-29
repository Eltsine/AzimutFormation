import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFormation, Formation } from '../formation.model';
import { FormationService } from '../service/formation.service';
import { IRapport } from 'app/entities/rapport/rapport.model';
import { RapportService } from 'app/entities/rapport/service/rapport.service';
import { IInscription } from 'app/entities/inscription/inscription.model';
import { InscriptionService } from 'app/entities/inscription/service/inscription.service';

@Component({
  selector: 'jhi-formation-update',
  templateUrl: './formation-update.component.html',
})
export class FormationUpdateComponent implements OnInit {
  isSaving = false;

  rapportsCollection: IRapport[] = [];
  inscriptionsSharedCollection: IInscription[] = [];

  editForm = this.fb.group({
    id: [],
    nomFormation: [null, [Validators.required]],
    rapport: [],
    inscription: [],
  });

  constructor(
    protected formationService: FormationService,
    protected rapportService: RapportService,
    protected inscriptionService: InscriptionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formation }) => {
      this.updateForm(formation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formation = this.createFromForm();
    if (formation.id !== undefined) {
      this.subscribeToSaveResponse(this.formationService.update(formation));
    } else {
      this.subscribeToSaveResponse(this.formationService.create(formation));
    }
  }

  trackRapportById(index: number, item: IRapport): number {
    return item.id!;
  }

  trackInscriptionById(index: number, item: IInscription): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormation>>): void {
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

  protected updateForm(formation: IFormation): void {
    this.editForm.patchValue({
      id: formation.id,
      nomFormation: formation.nomFormation,
      rapport: formation.rapport,
      inscription: formation.inscription,
    });

    this.rapportsCollection = this.rapportService.addRapportToCollectionIfMissing(this.rapportsCollection, formation.rapport);
    this.inscriptionsSharedCollection = this.inscriptionService.addInscriptionToCollectionIfMissing(
      this.inscriptionsSharedCollection,
      formation.inscription
    );
  }

  protected loadRelationshipsOptions(): void {
    this.rapportService
      .query({ filter: 'formation-is-null' })
      .pipe(map((res: HttpResponse<IRapport[]>) => res.body ?? []))
      .pipe(
        map((rapports: IRapport[]) => this.rapportService.addRapportToCollectionIfMissing(rapports, this.editForm.get('rapport')!.value))
      )
      .subscribe((rapports: IRapport[]) => (this.rapportsCollection = rapports));

    this.inscriptionService
      .query()
      .pipe(map((res: HttpResponse<IInscription[]>) => res.body ?? []))
      .pipe(
        map((inscriptions: IInscription[]) =>
          this.inscriptionService.addInscriptionToCollectionIfMissing(inscriptions, this.editForm.get('inscription')!.value)
        )
      )
      .subscribe((inscriptions: IInscription[]) => (this.inscriptionsSharedCollection = inscriptions));
  }

  protected createFromForm(): IFormation {
    return {
      ...new Formation(),
      id: this.editForm.get(['id'])!.value,
      nomFormation: this.editForm.get(['nomFormation'])!.value,
      rapport: this.editForm.get(['rapport'])!.value,
      inscription: this.editForm.get(['inscription'])!.value,
    };
  }
}
