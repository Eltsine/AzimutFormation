jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SpecialiteService } from '../service/specialite.service';
import { ISpecialite, Specialite } from '../specialite.model';
import { IFormateur } from 'app/entities/formateur/formateur.model';
import { FormateurService } from 'app/entities/formateur/service/formateur.service';

import { SpecialiteUpdateComponent } from './specialite-update.component';

describe('Component Tests', () => {
  describe('Specialite Management Update Component', () => {
    let comp: SpecialiteUpdateComponent;
    let fixture: ComponentFixture<SpecialiteUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let specialiteService: SpecialiteService;
    let formateurService: FormateurService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SpecialiteUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SpecialiteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpecialiteUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      specialiteService = TestBed.inject(SpecialiteService);
      formateurService = TestBed.inject(FormateurService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Formateur query and add missing value', () => {
        const specialite: ISpecialite = { id: 456 };
        const formateur: IFormateur = { id: 36972 };
        specialite.formateur = formateur;

        const formateurCollection: IFormateur[] = [{ id: 12840 }];
        jest.spyOn(formateurService, 'query').mockReturnValue(of(new HttpResponse({ body: formateurCollection })));
        const additionalFormateurs = [formateur];
        const expectedCollection: IFormateur[] = [...additionalFormateurs, ...formateurCollection];
        jest.spyOn(formateurService, 'addFormateurToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ specialite });
        comp.ngOnInit();

        expect(formateurService.query).toHaveBeenCalled();
        expect(formateurService.addFormateurToCollectionIfMissing).toHaveBeenCalledWith(formateurCollection, ...additionalFormateurs);
        expect(comp.formateursSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const specialite: ISpecialite = { id: 456 };
        const formateur: IFormateur = { id: 35460 };
        specialite.formateur = formateur;

        activatedRoute.data = of({ specialite });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(specialite));
        expect(comp.formateursSharedCollection).toContain(formateur);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Specialite>>();
        const specialite = { id: 123 };
        jest.spyOn(specialiteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ specialite });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: specialite }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(specialiteService.update).toHaveBeenCalledWith(specialite);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Specialite>>();
        const specialite = new Specialite();
        jest.spyOn(specialiteService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ specialite });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: specialite }));
        saveSubject.complete();

        // THEN
        expect(specialiteService.create).toHaveBeenCalledWith(specialite);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Specialite>>();
        const specialite = { id: 123 };
        jest.spyOn(specialiteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ specialite });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(specialiteService.update).toHaveBeenCalledWith(specialite);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
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
