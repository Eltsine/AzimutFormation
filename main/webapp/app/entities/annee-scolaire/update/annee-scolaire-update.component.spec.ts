jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AnneeScolaireService } from '../service/annee-scolaire.service';
import { IAnneeScolaire, AnneeScolaire } from '../annee-scolaire.model';

import { AnneeScolaireUpdateComponent } from './annee-scolaire-update.component';

describe('Component Tests', () => {
  describe('AnneeScolaire Management Update Component', () => {
    let comp: AnneeScolaireUpdateComponent;
    let fixture: ComponentFixture<AnneeScolaireUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let anneeScolaireService: AnneeScolaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AnneeScolaireUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AnneeScolaireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnneeScolaireUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      anneeScolaireService = TestBed.inject(AnneeScolaireService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const anneeScolaire: IAnneeScolaire = { id: 456 };

        activatedRoute.data = of({ anneeScolaire });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(anneeScolaire));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnneeScolaire>>();
        const anneeScolaire = { id: 123 };
        jest.spyOn(anneeScolaireService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anneeScolaire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: anneeScolaire }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(anneeScolaireService.update).toHaveBeenCalledWith(anneeScolaire);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnneeScolaire>>();
        const anneeScolaire = new AnneeScolaire();
        jest.spyOn(anneeScolaireService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anneeScolaire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: anneeScolaire }));
        saveSubject.complete();

        // THEN
        expect(anneeScolaireService.create).toHaveBeenCalledWith(anneeScolaire);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnneeScolaire>>();
        const anneeScolaire = { id: 123 };
        jest.spyOn(anneeScolaireService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anneeScolaire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(anneeScolaireService.update).toHaveBeenCalledWith(anneeScolaire);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
