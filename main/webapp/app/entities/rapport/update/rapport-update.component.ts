import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRapport, Rapport } from '../rapport.model';
import { RapportService } from '../service/rapport.service';

@Component({
  selector: 'jhi-rapport-update',
  templateUrl: './rapport-update.component.html',
})
export class RapportUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected rapportService: RapportService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rapport }) => {
      this.updateForm(rapport);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rapport = this.createFromForm();
    if (rapport.id !== undefined) {
      this.subscribeToSaveResponse(this.rapportService.update(rapport));
    } else {
      this.subscribeToSaveResponse(this.rapportService.create(rapport));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRapport>>): void {
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

  protected updateForm(rapport: IRapport): void {
    this.editForm.patchValue({
      id: rapport.id,
    });
  }

  protected createFromForm(): IRapport {
    return {
      ...new Rapport(),
      id: this.editForm.get(['id'])!.value,
    };
  }
}
