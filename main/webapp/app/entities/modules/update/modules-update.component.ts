import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IModules, Modules } from '../modules.model';
import { ModulesService } from '../service/modules.service';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';

@Component({
  selector: 'jhi-modules-update',
  templateUrl: './modules-update.component.html',
})
export class ModulesUpdateComponent implements OnInit {
  isSaving = false;

  formationsSharedCollection: IFormation[] = [];

  editForm = this.fb.group({
    id: [],
    nomModule: [null, [Validators.required]],
    prix: [],
    formation: [],
  });

  constructor(
    protected modulesService: ModulesService,
    protected formationService: FormationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modules }) => {
      this.updateForm(modules);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modules = this.createFromForm();
    if (modules.id !== undefined) {
      this.subscribeToSaveResponse(this.modulesService.update(modules));
    } else {
      this.subscribeToSaveResponse(this.modulesService.create(modules));
    }
  }

  trackFormationById(index: number, item: IFormation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModules>>): void {
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

  protected updateForm(modules: IModules): void {
    this.editForm.patchValue({
      id: modules.id,
      nomModule: modules.nomModule,
      prix: modules.prix,
      formation: modules.formation,
    });

    this.formationsSharedCollection = this.formationService.addFormationToCollectionIfMissing(
      this.formationsSharedCollection,
      modules.formation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.formationService
      .query()
      .pipe(map((res: HttpResponse<IFormation[]>) => res.body ?? []))
      .pipe(
        map((formations: IFormation[]) =>
          this.formationService.addFormationToCollectionIfMissing(formations, this.editForm.get('formation')!.value)
        )
      )
      .subscribe((formations: IFormation[]) => (this.formationsSharedCollection = formations));
  }

  protected createFromForm(): IModules {
    return {
      ...new Modules(),
      id: this.editForm.get(['id'])!.value,
      nomModule: this.editForm.get(['nomModule'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      formation: this.editForm.get(['formation'])!.value,
    };
  }
}
