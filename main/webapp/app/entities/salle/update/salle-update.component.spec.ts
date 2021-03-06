jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SalleService } from '../service/salle.service';
import { ISalle, Salle } from '../salle.model';
import { ISession } from 'app/entities/session/session.model';
import { SessionService } from 'app/entities/session/service/session.service';

import { SalleUpdateComponent } from './salle-update.component';

describe('Component Tests', () => {
  describe('Salle Management Update Component', () => {
    let comp: SalleUpdateComponent;
    let fixture: ComponentFixture<SalleUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let salleService: SalleService;
    let sessionService: SessionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SalleUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SalleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SalleUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      salleService = TestBed.inject(SalleService);
      sessionService = TestBed.inject(SessionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Session query and add missing value', () => {
        const salle: ISalle = { id: 456 };
        const session: ISession = { id: 57097 };
        salle.session = session;

        const sessionCollection: ISession[] = [{ id: 51951 }];
        jest.spyOn(sessionService, 'query').mockReturnValue(of(new HttpResponse({ body: sessionCollection })));
        const additionalSessions = [session];
        const expectedCollection: ISession[] = [...additionalSessions, ...sessionCollection];
        jest.spyOn(sessionService, 'addSessionToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ salle });
        comp.ngOnInit();

        expect(sessionService.query).toHaveBeenCalled();
        expect(sessionService.addSessionToCollectionIfMissing).toHaveBeenCalledWith(sessionCollection, ...additionalSessions);
        expect(comp.sessionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const salle: ISalle = { id: 456 };
        const session: ISession = { id: 96045 };
        salle.session = session;

        activatedRoute.data = of({ salle });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(salle));
        expect(comp.sessionsSharedCollection).toContain(session);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Salle>>();
        const salle = { id: 123 };
        jest.spyOn(salleService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ salle });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: salle }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(salleService.update).toHaveBeenCalledWith(salle);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Salle>>();
        const salle = new Salle();
        jest.spyOn(salleService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ salle });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: salle }));
        saveSubject.complete();

        // THEN
        expect(salleService.create).toHaveBeenCalledWith(salle);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Salle>>();
        const salle = { id: 123 };
        jest.spyOn(salleService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ salle });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(salleService.update).toHaveBeenCalledWith(salle);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackSessionById', () => {
        it('Should return tracked Session primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSessionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
