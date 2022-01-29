import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAnneeScolaire, AnneeScolaire } from '../annee-scolaire.model';
import { AnneeScolaireService } from '../service/annee-scolaire.service';

@Component({
  selector: 'jhi-annee-scolaire-update',
  templateUrl: './annee-scolaire-update.component.html',
})
export class AnneeScolaireUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelleAnnee: [],
  });

  constructor(protected anneeScolaireService: AnneeScolaireService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anneeScolaire }) => {
      this.updateForm(anneeScolaire);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anneeScolaire = this.createFromForm();
    if (anneeScolaire.id !== undefined) {
      this.subscribeToSaveResponse(this.anneeScolaireService.update(anneeScolaire));
    } else {
      this.subscribeToSaveResponse(this.anneeScolaireService.create(anneeScolaire));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnneeScolaire>>): void {
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

  protected updateForm(anneeScolaire: IAnneeScolaire): void {
    this.editForm.patchValue({
      id: anneeScolaire.id,
      libelleAnnee: anneeScolaire.libelleAnnee,
    });
  }

  protected createFromForm(): IAnneeScolaire {
    return {
      ...new AnneeScolaire(),
      id: this.editForm.get(['id'])!.value,
      libelleAnnee: this.editForm.get(['libelleAnnee'])!.value,
    };
  }
}
