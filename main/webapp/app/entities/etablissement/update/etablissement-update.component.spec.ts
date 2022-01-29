jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EtablissementService } from '../service/etablissement.service';
import { IEtablissement, Etablissement } from '../etablissement.model';

import { EtablissementUpdateComponent } from './etablissement-update.component';

describe('Component Tests', () => {
  describe('Etablissement Management Update Component', () => {
    let comp: EtablissementUpdateComponent;
    let fixture: ComponentFixture<EtablissementUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let etablissementService: EtablissementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EtablissementUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EtablissementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtablissementUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      etablissementService = TestBed.inject(EtablissementService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const etablissement: IEtablissement = { id: 456 };

        activatedRoute.data = of({ etablissement });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(etablissement));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Etablissement>>();
        const etablissement = { id: 123 };
        jest.spyOn(etablissementService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etablissement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: etablissement }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(etablissementService.update).toHaveBeenCalledWith(etablissement);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Etablissement>>();
        const etablissement = new Etablissement();
        jest.spyOn(etablissementService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etablissement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: etablissement }));
        saveSubject.complete();

        // THEN
        expect(etablissementService.create).toHaveBeenCalledWith(etablissement);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Etablissement>>();
        const etablissement = { id: 123 };
        jest.spyOn(etablissementService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etablissement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(etablissementService.update).toHaveBeenCalledWith(etablissement);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
