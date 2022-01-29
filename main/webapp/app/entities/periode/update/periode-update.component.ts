import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPeriode, Periode } from '../periode.model';
import { PeriodeService } from '../service/periode.service';

@Component({
  selector: 'jhi-periode-update',
  templateUrl: './periode-update.component.html',
})
export class PeriodeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    duree: [],
    dateDebut: [null, [Validators.required]],
    dateFin: [null, [Validators.required]],
  });

  constructor(protected periodeService: PeriodeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periode }) => {
      if (periode.id === undefined) {
        const today = dayjs().startOf('day');
        periode.dateDebut = today;
        periode.dateFin = today;
      }

      this.updateForm(periode);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const periode = this.createFromForm();
    if (periode.id !== undefined) {
      this.subscribeToSaveResponse(this.periodeService.update(periode));
    } else {
      this.subscribeToSaveResponse(this.periodeService.create(periode));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriode>>): void {
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

  protected updateForm(periode: IPeriode): void {
    this.editForm.patchValue({
      id: periode.id,
      duree: periode.duree,
      dateDebut: periode.dateDebut ? periode.dateDebut.format(DATE_TIME_FORMAT) : null,
      dateFin: periode.dateFin ? periode.dateFin.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPeriode {
    return {
      ...new Periode(),
      id: this.editForm.get(['id'])!.value,
      duree: this.editForm.get(['duree'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value ? dayjs(this.editForm.get(['dateDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      dateFin: this.editForm.get(['dateFin'])!.value ? dayjs(this.editForm.get(['dateFin'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
