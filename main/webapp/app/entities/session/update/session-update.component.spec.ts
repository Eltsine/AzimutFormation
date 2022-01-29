jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SessionService } from '../service/session.service';
import { ISession, Session } from '../session.model';
import { IModules } from 'app/entities/modules/modules.model';
import { ModulesService } from 'app/entities/modules/service/modules.service';

import { SessionUpdateComponent } from './session-update.component';

describe('Component Tests', () => {
  describe('Session Management Update Component', () => {
    let comp: SessionUpdateComponent;
    let fixture: ComponentFixture<SessionUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let sessionService: SessionService;
    let modulesService: ModulesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SessionUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SessionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SessionUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      sessionService = TestBed.inject(SessionService);
      modulesService = TestBed.inject(ModulesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Modules query and add missing value', () => {
        const session: ISession = { id: 456 };
        const modules: IModules = { id: 90923 };
        session.modules = modules;

        const modulesCollection: IModules[] = [{ id: 75660 }];
        jest.spyOn(modulesService, 'query').mockReturnValue(of(new HttpResponse({ body: modulesCollection })));
        const additionalModules = [modules];
        const expectedCollection: IModules[] = [...additionalModules, ...modulesCollection];
        jest.spyOn(modulesService, 'addModulesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ session });
        comp.ngOnInit();

        expect(modulesService.query).toHaveBeenCalled();
        expect(modulesService.addModulesToCollectionIfMissing).toHaveBeenCalledWith(modulesCollection, ...additionalModules);
        expect(comp.modulesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const session: ISession = { id: 456 };
        const modules: IModules = { id: 96260 };
        session.modules = modules;

        activatedRoute.data = of({ session });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(session));
        expect(comp.modulesSharedCollection).toContain(modules);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Session>>();
        const session = { id: 123 };
        jest.spyOn(sessionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ session });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: session }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(sessionService.update).toHaveBeenCalledWith(session);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Session>>();
        const session = new Session();
        jest.spyOn(sessionService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ session });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: session }));
        saveSubject.complete();

        // THEN
        expect(sessionService.create).toHaveBeenCalledWith(session);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Session>>();
        const session = { id: 123 };
        jest.spyOn(sessionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ session });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(sessionService.update).toHaveBeenCalledWith(session);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackModulesById', () => {
        it('Should return tracked Modules primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackModulesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
