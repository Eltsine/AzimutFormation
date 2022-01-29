jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RapportService } from '../service/rapport.service';
import { IRapport, Rapport } from '../rapport.model';

import { RapportUpdateComponent } from './rapport-update.component';

describe('Component Tests', () => {
  describe('Rapport Management Update Component', () => {
    let comp: RapportUpdateComponent;
    let fixture: ComponentFixture<RapportUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let rapportService: RapportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RapportUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RapportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RapportUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      rapportService = TestBed.inject(RapportService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const rapport: IRapport = { id: 456 };

        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(rapport));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rapport>>();
        const rapport = { id: 123 };
        jest.spyOn(rapportService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: rapport }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(rapportService.update).toHaveBeenCalledWith(rapport);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rapport>>();
        const rapport = new Rapport();
        jest.spyOn(rapportService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: rapport }));
        saveSubject.complete();

        // THEN
        expect(rapportService.create).toHaveBeenCalledWith(rapport);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rapport>>();
        const rapport = { id: 123 };
        jest.spyOn(rapportService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(rapportService.update).toHaveBeenCalledWith(rapport);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
