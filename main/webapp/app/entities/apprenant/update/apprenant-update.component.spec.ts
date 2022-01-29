jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ApprenantService } from '../service/apprenant.service';
import { IApprenant, Apprenant } from '../apprenant.model';
import { IInscription } from 'app/entities/inscription/inscription.model';
import { InscriptionService } from 'app/entities/inscription/service/inscription.service';
import { IFormateur } from 'app/entities/formateur/formateur.model';
import { FormateurService } from 'app/entities/formateur/service/formateur.service';

import { ApprenantUpdateComponent } from './apprenant-update.component';

describe('Component Tests', () => {
  describe('Apprenant Management Update Component', () => {
    let comp: ApprenantUpdateComponent;
    let fixture: ComponentFixture<ApprenantUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let apprenantService: ApprenantService;
    let inscriptionService: InscriptionService;
    let formateurService: FormateurService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ApprenantUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ApprenantUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApprenantUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      apprenantService = TestBed.inject(ApprenantService);
      inscriptionService = TestBed.inject(InscriptionService);
      formateurService = TestBed.inject(FormateurService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Inscription query and add missing value', () => {
        const apprenant: IApprenant = { id: 456 };
        const inscription: IInscription = { id: 48992 };
        apprenant.inscription = inscription;

        const inscriptionCollection: IInscription[] = [{ id: 56893 }];
        jest.spyOn(inscriptionService, 'query').mockReturnValue(of(new HttpResponse({ body: inscriptionCollection })));
        const additionalInscriptions = [inscription];
        const expectedCollection: IInscription[] = [...additionalInscriptions, ...inscriptionCollection];
        jest.spyOn(inscriptionService, 'addInscriptionToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ apprenant });
        comp.ngOnInit();

        expect(inscriptionService.query).toHaveBeenCalled();
        expect(inscriptionService.addInscriptionToCollectionIfMissing).toHaveBeenCalledWith(
          inscriptionCollection,
          ...additionalInscriptions
        );
        expect(comp.inscriptionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Formateur query and add missing value', () => {
        const apprenant: IApprenant = { id: 456 };
        const formateur: IFormateur = { id: 80458 };
        apprenant.formateur = formateur;

        const formateurCollection: IFormateur[] = [{ id: 48535 }];
        jest.spyOn(formateurService, 'query').mockReturnValue(of(new HttpResponse({ body: formateurCollection })));
        const additionalFormateurs = [formateur];
        const expectedCollection: IFormateur[] = [...additionalFormateurs, ...formateurCollection];
        jest.spyOn(formateurService, 'addFormateurToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ apprenant });
        comp.ngOnInit();

        expect(formateurService.query).toHaveBeenCalled();
        expect(formateurService.addFormateurToCollectionIfMissing).toHaveBeenCalledWith(formateurCollection, ...additionalFormateurs);
        expect(comp.formateursSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const apprenant: IApprenant = { id: 456 };
        const inscription: IInscription = { id: 72567 };
        apprenant.inscription = inscription;
        const formateur: IFormateur = { id: 3094 };
        apprenant.formateur = formateur;

        activatedRoute.data = of({ apprenant });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(apprenant));
        expect(comp.inscriptionsSharedCollection).toContain(inscription);
        expect(comp.formateursSharedCollection).toContain(formateur);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Apprenant>>();
        const apprenant = { id: 123 };
        jest.spyOn(apprenantService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ apprenant });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: apprenant }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(apprenantService.update).toHaveBeenCalledWith(apprenant);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Apprenant>>();
        const apprenant = new Apprenant();
        jest.spyOn(apprenantService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ apprenant });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: apprenant }));
        saveSubject.complete();

        // THEN
        expect(apprenantService.create).toHaveBeenCalledWith(apprenant);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Apprenant>>();
        const apprenant = { id: 123 };
        jest.spyOn(apprenantService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ apprenant });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(apprenantService.update).toHaveBeenCalledWith(apprenant);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackInscriptionById', () => {
        it('Should return tracked Inscription primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackInscriptionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackFormateurById', () => {
        it('Should return tracked Formateur primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFormateurById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
