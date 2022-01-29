jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ModulesService } from '../service/modules.service';
import { IModules, Modules } from '../modules.model';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';

import { ModulesUpdateComponent } from './modules-update.component';

describe('Component Tests', () => {
  describe('Modules Management Update Component', () => {
    let comp: ModulesUpdateComponent;
    let fixture: ComponentFixture<ModulesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let modulesService: ModulesService;
    let formationService: FormationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ModulesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ModulesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModulesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      modulesService = TestBed.inject(ModulesService);
      formationService = TestBed.inject(FormationService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Formation query and add missing value', () => {
        const modules: IModules = { id: 456 };
        const formation: IFormation = { id: 73219 };
        modules.formation = formation;

        const formationCollection: IFormation[] = [{ id: 32610 }];
        jest.spyOn(formationService, 'query').mockReturnValue(of(new HttpResponse({ body: formationCollection })));
        const additionalFormations = [formation];
        const expectedCollection: IFormation[] = [...additionalFormations, ...formationCollection];
        jest.spyOn(formationService, 'addFormationToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ modules });
        comp.ngOnInit();

        expect(formationService.query).toHaveBeenCalled();
        expect(formationService.addFormationToCollectionIfMissing).toHaveBeenCalledWith(formationCollection, ...additionalFormations);
        expect(comp.formationsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const modules: IModules = { id: 456 };
        const formation: IFormation = { id: 33185 };
        modules.formation = formation;

        activatedRoute.data = of({ modules });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(modules));
        expect(comp.formationsSharedCollection).toContain(formation);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Modules>>();
        const modules = { id: 123 };
        jest.spyOn(modulesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ modules });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: modules }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(modulesService.update).toHaveBeenCalledWith(modules);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Modules>>();
        const modules = new Modules();
        jest.spyOn(modulesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ modules });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: modules }));
        saveSubject.complete();

        // THEN
        expect(modulesService.create).toHaveBeenCalledWith(modules);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Modules>>();
        const modules = { id: 123 };
        jest.spyOn(modulesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ modules });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(modulesService.update).toHaveBeenCalledWith(modules);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackFormationById', () => {
        it('Should return tracked Formation primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFormationById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
