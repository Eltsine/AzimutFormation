jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PeriodeService } from '../service/periode.service';
import { IPeriode, Periode } from '../periode.model';

import { PeriodeUpdateComponent } from './periode-update.component';

describe('Component Tests', () => {
  describe('Periode Management Update Component', () => {
    let comp: PeriodeUpdateComponent;
    let fixture: ComponentFixture<PeriodeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let periodeService: PeriodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PeriodeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PeriodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeriodeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      periodeService = TestBed.inject(PeriodeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const periode: IPeriode = { id: 456 };

        activatedRoute.data = of({ periode });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(periode));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Periode>>();
        const periode = { id: 123 };
        jest.spyOn(periodeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ periode });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: periode }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(periodeService.update).toHaveBeenCalledWith(periode);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Periode>>();
        const periode = new Periode();
        jest.spyOn(periodeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ periode });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: periode }));
        saveSubject.complete();

        // THEN
        expect(periodeService.create).toHaveBeenCalledWith(periode);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Periode>>();
        const periode = { id: 123 };
        jest.spyOn(periodeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ periode });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(periodeService.update).toHaveBeenCalledWith(periode);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
