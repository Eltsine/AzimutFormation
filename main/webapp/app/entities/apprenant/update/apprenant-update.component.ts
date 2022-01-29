import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IApprenant, Apprenant } from '../apprenant.model';
import { ApprenantService } from '../service/apprenant.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IInscription } from 'app/entities/inscription/inscription.model';
import { InscriptionService } from 'app/entities/inscription/service/inscription.service';
import { IFormateur } from 'app/entities/formateur/formateur.model';
import { FormateurService } from 'app/entities/formateur/service/formateur.service';

@Component({
  selector: 'jhi-apprenant-update',
  templateUrl: './apprenant-update.component.html',
})
export class ApprenantUpdateComponent implements OnInit {
  isSaving = false;

  inscriptionsSharedCollection: IInscription[] = [];
  formateursSharedCollection: IFormateur[] = [];

  editForm = this.fb.group({
    id: [],
    photo: [],
    photoContentType: [],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    niveau: [null, [Validators.required]],
    etatStud: [],
    contact: [null, [Validators.required]],
    email: [],
    addParent: [],
    inscription: [],
    formateur: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected apprenantService: ApprenantService,
    protected inscriptionService: InscriptionService,
    protected formateurService: FormateurService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apprenant }) => {
      this.updateForm(apprenant);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('azimutApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apprenant = this.createFromForm();
    if (apprenant.id !== undefined) {
      this.subscribeToSaveResponse(this.apprenantService.update(apprenant));
    } else {
      this.subscribeToSaveResponse(this.apprenantService.create(apprenant));
    }
  }

  trackInscriptionById(index: number, item: IInscription): number {
    return item.id!;
  }

  trackFormateurById(index: number, item: IFormateur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApprenant>>): void {
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

  protected updateForm(apprenant: IApprenant): void {
    this.editForm.patchValue({
      id: apprenant.id,
      photo: apprenant.photo,
      photoContentType: apprenant.photoContentType,
      nom: apprenant.nom,
      prenom: apprenant.prenom,
      statut: apprenant.statut,
      niveau: apprenant.niveau,
      etatStud: apprenant.etatStud,
      contact: apprenant.contact,
      email: apprenant.email,
      addParent: apprenant.addParent,
      inscription: apprenant.inscription,
      formateur: apprenant.formateur,
    });

    this.inscriptionsSharedCollection = this.inscriptionService.addInscriptionToCollectionIfMissing(
      this.inscriptionsSharedCollection,
      apprenant.inscription
    );
    this.formateursSharedCollection = this.formateurService.addFormateurToCollectionIfMissing(
      this.formateursSharedCollection,
      apprenant.formateur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.inscriptionService
      .query()
      .pipe(map((res: HttpResponse<IInscription[]>) => res.body ?? []))
      .pipe(
        map((inscriptions: IInscription[]) =>
          this.inscriptionService.addInscriptionToCollectionIfMissing(inscriptions, this.editForm.get('inscription')!.value)
        )
      )
      .subscribe((inscriptions: IInscription[]) => (this.inscriptionsSharedCollection = inscriptions));

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

  protected createFromForm(): IApprenant {
    return {
      ...new Apprenant(),
      id: this.editForm.get(['id'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      niveau: this.editForm.get(['niveau'])!.value,
      etatStud: this.editForm.get(['etatStud'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      email: this.editForm.get(['email'])!.value,
      addParent: this.editForm.get(['addParent'])!.value,
      inscription: this.editForm.get(['inscription'])!.value,
      formateur: this.editForm.get(['formateur'])!.value,
    };
  }
}
