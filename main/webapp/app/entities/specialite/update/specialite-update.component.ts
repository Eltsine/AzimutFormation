import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISpecialite, Specialite } from '../specialite.model';
import { SpecialiteService } from '../service/specialite.service';
import { IFormateur } from 'app/entities/formateur/formateur.model';
import { FormateurService } from 'app/entities/formateur/service/formateur.service';

@Component({
  selector: 'jhi-specialite-update',
  templateUrl: './specialite-update.component.html',
})
export class SpecialiteUpdateComponent implements OnInit {
  isSaving = false;

  formateursSharedCollection: IFormateur[] = [];

  editForm = this.fb.group({
    id: [],
    libSpecialite: [null, [Validators.required]],
    formateur: [],
  });

  constructor(
    protected specialiteService: SpecialiteService,
    protected formateurService: FormateurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialite }) => {
      this.updateForm(specialite);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const specialite = this.createFromForm();
    if (specialite.id !== undefined) {
      this.subscribeToSaveResponse(this.specialiteService.update(specialite));
    } else {
      this.subscribeToSaveResponse(this.specialiteService.create(specialite));
    }
  }

  trackFormateurById(index: number, item: IFormateur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialite>>): void {
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

  protected updateForm(specialite: ISpecialite): void {
    this.editForm.patchValue({
      id: specialite.id,
      libSpecialite: specialite.libSpecialite,
      formateur: specialite.formateur,
    });

    this.formateursSharedCollection = this.formateurService.addFormateurToCollectionIfMissing(
      this.formateursSharedCollection,
      specialite.formateur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.formateurService
      .query()
      .pipe(map((res: HttpResponse<IFormateur[]>) => res.body ?? []))
      .pipe(
        map((formateurs: IFormateur[]) =>
          this.formateurService.addFormateurToCollectionIfMissing(formateurs, this.editForm.get('formateur')!.value)
        )
      )
      .subscribe((formateurs: IFormateur[]) => (this.formateursSharedCollection = formateurs));
  }

  protected createFromForm(): ISpecialite {
    return {
      ...new Specialite(),
      id: this.editForm.get(['id'])!.value,
      libSpecialite: this.editForm.get(['libSpecialite'])!.value,
      formateur: this.editForm.get(['formateur'])!.value,
    };
  }
}
