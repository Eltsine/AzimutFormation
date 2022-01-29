import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISession, Session } from '../session.model';
import { SessionService } from '../service/session.service';
import { IModules } from 'app/entities/modules/modules.model';
import { ModulesService } from 'app/entities/modules/service/modules.service';

@Component({
  selector: 'jhi-session-update',
  templateUrl: './session-update.component.html',
})
export class SessionUpdateComponent implements OnInit {
  isSaving = false;

  modulesSharedCollection: IModules[] = [];

  editForm = this.fb.group({
    id: [],
    dateDebut: [],
    dateFin: [],
    nbreParticipant: [null, [Validators.required]],
    nbreHeure: [],
    heureDebut: [],
    heureFin: [],
    contenuFormation: [null, [Validators.required]],
    modules: [],
  });

  constructor(
    protected sessionService: SessionService,
    protected modulesService: ModulesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ session }) => {
      if (session.id === undefined) {
        const today = dayjs().startOf('day');
        session.dateDebut = today;
        session.dateFin = today;
        session.heureDebut = today;
        session.heureFin = today;
      }

      this.updateForm(session);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const session = this.createFromForm();
    if (session.id !== undefined) {
      this.subscribeToSaveResponse(this.sessionService.update(session));
    } else {
      this.subscribeToSaveResponse(this.sessionService.create(session));
    }
  }

  trackModulesById(index: number, item: IModules): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISession>>): void {
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

  protected updateForm(session: ISession): void {
    this.editForm.patchValue({
      id: session.id,
      dateDebut: session.dateDebut ? session.dateDebut.format(DATE_TIME_FORMAT) : null,
      dateFin: session.dateFin ? session.dateFin.format(DATE_TIME_FORMAT) : null,
      nbreParticipant: session.nbreParticipant,
      nbreHeure: session.nbreHeure,
      heureDebut: session.heureDebut ? session.heureDebut.format(DATE_TIME_FORMAT) : null,
      heureFin: session.heureFin ? session.heureFin.format(DATE_TIME_FORMAT) : null,
      contenuFormation: session.contenuFormation,
      modules: session.modules,
    });

    this.modulesSharedCollection = this.modulesService.addModulesToCollectionIfMissing(this.modulesSharedCollection, session.modules);
  }

  protected loadRelationshipsOptions(): void {
    this.modulesService
      .query()
      .pipe(map((res: HttpResponse<IModules[]>) => res.body ?? []))
      .pipe(map((modules: IModules[]) => this.modulesService.addModulesToCollectionIfMissing(modules, this.editForm.get('modules')!.value)))
      .subscribe((modules: IModules[]) => (this.modulesSharedCollection = modules));
  }

  protected createFromForm(): ISession {
    return {
      ...new Session(),
      id: this.editForm.get(['id'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value ? dayjs(this.editForm.get(['dateDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      dateFin: this.editForm.get(['dateFin'])!.value ? dayjs(this.editForm.get(['dateFin'])!.value, DATE_TIME_FORMAT) : undefined,
      nbreParticipant: this.editForm.get(['nbreParticipant'])!.value,
      nbreHeure: this.editForm.get(['nbreHeure'])!.value,
      heureDebut: this.editForm.get(['heureDebut'])!.value ? dayjs(this.editForm.get(['heureDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      heureFin: this.editForm.get(['heureFin'])!.value ? dayjs(this.editForm.get(['heureFin'])!.value, DATE_TIME_FORMAT) : undefined,
      contenuFormation: this.editForm.get(['contenuFormation'])!.value,
      modules: this.editForm.get(['modules'])!.value,
    };
  }
}
