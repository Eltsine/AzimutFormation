import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISalle, Salle } from '../salle.model';
import { SalleService } from '../service/salle.service';
import { ISession } from 'app/entities/session/session.model';
import { SessionService } from 'app/entities/session/service/session.service';

@Component({
  selector: 'jhi-salle-update',
  templateUrl: './salle-update.component.html',
})
export class SalleUpdateComponent implements OnInit {
  isSaving = false;

  sessionsSharedCollection: ISession[] = [];

  editForm = this.fb.group({
    id: [],
    nomSalle: [null, [Validators.required]],
    capacite: [],
    session: [],
  });

  constructor(
    protected salleService: SalleService,
    protected sessionService: SessionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salle }) => {
      this.updateForm(salle);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salle = this.createFromForm();
    if (salle.id !== undefined) {
      this.subscribeToSaveResponse(this.salleService.update(salle));
    } else {
      this.subscribeToSaveResponse(this.salleService.create(salle));
    }
  }

  trackSessionById(index: number, item: ISession): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalle>>): void {
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

  protected updateForm(salle: ISalle): void {
    this.editForm.patchValue({
      id: salle.id,
      nomSalle: salle.nomSalle,
      capacite: salle.capacite,
      session: salle.session,
    });

    this.sessionsSharedCollection = this.sessionService.addSessionToCollectionIfMissing(this.sessionsSharedCollection, salle.session);
  }

  protected loadRelationshipsOptions(): void {
    this.sessionService
      .query()
      .pipe(map((res: HttpResponse<ISession[]>) => res.body ?? []))
      .pipe(
        map((sessions: ISession[]) => this.sessionService.addSessionToCollectionIfMissing(sessions, this.editForm.get('session')!.value))
      )
      .subscribe((sessions: ISession[]) => (this.sessionsSharedCollection = sessions));
  }

  protected createFromForm(): ISalle {
    return {
      ...new Salle(),
      id: this.editForm.get(['id'])!.value,
      nomSalle: this.editForm.get(['nomSalle'])!.value,
      capacite: this.editForm.get(['capacite'])!.value,
      session: this.editForm.get(['session'])!.value,
    };
  }
}
