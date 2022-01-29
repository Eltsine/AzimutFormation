jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FormateurService } from '../service/formateur.service';
import { IFormateur, Formateur } from '../formateur.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';

import { FormateurUpdateComponent } from './formateur-update.component';

describe('Component Tests', () => {
  describe('Formateur Management Update Component', () => {
    let comp: FormateurUpdateComponent;
    let fixture: ComponentFixture<FormateurUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let formateurService: FormateurService;
    let etablissementService: EtablissementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FormateurUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FormateurUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormateurUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      formateurService = TestBed.inject(FormateurService);
      etablissementService = TestBed.inject(EtablissementService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Etablissement query and add missing value', () => {
        const formateur: IFormateur = { id: 456 };
        const etablissement: IEtablissement = { id: 62627 };
        formateur.etablissement = etablissement;

        const etablissementCollection: IEtablissement[] = [{ id: 11017 }];
        jest.spyOn(etablissementService, 'query').mockReturnValue(of(new HttpResponse({ body: etablissementCollection })));
        const additionalEtablissements = [etablissement];
        const expectedCollection: IEtablissement[] = [...additionalEtablissements, ...etablissementCollection];
        jest.spyOn(etablissementService, 'addEtablissementToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ formateur });
        comp.ngOnInit();

        expect(etablissementService.query).toHaveBeenCalled();
        expect(etablissementService.addEtablissementToCollectionIfMissing).toHaveBeenCalledWith(
          etablissementCollection,
          ...additionalEtablissements
        );
        expect(comp.etablissementsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const formateur: IFormateur = { id: 456 };
        const etablissement: IEtablissement = { id: 11216 };
        formateur.etablissement = etablissement;

        activatedRoute.data = of({ formateur });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(formateur));
        expect(comp.etablissementsSharedCollection).toContain(etablissement);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Formateur>>();
        const formateur = { id: 123 };
        jest.spyOn(formateurService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ formateur });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: formateur }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(formateurService.update).toHaveBeenCalledWith(formateur);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Formateur>>();
        const formateur = new Formateur();
        jest.spyOn(formateurService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ formateur });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: formateur }));
        saveSubject.complete();

        // THEN
        expect(formateurService.create).toHaveBeenCalledWith(formateur);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Formateur>>();
        const formateur = { id: 123 };
        jest.spyOn(formateurService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ formateur });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(formateurService.update).toHaveBeenCalledWith(formateur);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackEtablissementById', () => {
        it('Should return tracked Etablissement primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEtablissementById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
