jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { InscriptionService } from '../service/inscription.service';
import { IInscription, Inscription } from '../inscription.model';
import { IPeriode } from 'app/entities/periode/periode.model';
import { PeriodeService } from 'app/entities/periode/service/periode.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { IAnneeScolaire } from 'app/entities/annee-scolaire/annee-scolaire.model';
import { AnneeScolaireService } from 'app/entities/annee-scolaire/service/annee-scolaire.service';

import { InscriptionUpdateComponent } from './inscription-update.component';

describe('Component Tests', () => {
  describe('Inscription Management Update Component', () => {
    let comp: InscriptionUpdateComponent;
    let fixture: ComponentFixture<InscriptionUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let inscriptionService: InscriptionService;
    let periodeService: PeriodeService;
    let etablissementService: EtablissementService;
    let anneeScolaireService: AnneeScolaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [InscriptionUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(InscriptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InscriptionUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      inscriptionService = TestBed.inject(InscriptionService);
      periodeService = TestBed.inject(PeriodeService);
      etablissementService = TestBed.inject(EtablissementService);
      anneeScolaireService = TestBed.inject(AnneeScolaireService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call periode query and add missing value', () => {
        const inscription: IInscription = { id: 456 };
        const periode: IPeriode = { id: 50975 };
        inscription.periode = periode;

        const periodeCollection: IPeriode[] = [{ id: 88718 }];
        jest.spyOn(periodeService, 'query').mockReturnValue(of(new HttpResponse({ body: periodeCollection })));
        const expectedCollection: IPeriode[] = [periode, ...periodeCollection];
        jest.spyOn(periodeService, 'addPeriodeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ inscription });
        comp.ngOnInit();

        expect(periodeService.query).toHaveBeenCalled();
        expect(periodeService.addPeriodeToCollectionIfMissing).toHaveBeenCalledWith(periodeCollection, periode);
        expect(comp.periodesCollection).toEqual(expectedCollection);
      });

      it('Should call Etablissement query and add missing value', () => {
        const inscription: IInscription = { id: 456 };
        const etablissement: IEtablissement = { id: 29404 };
        inscription.etablissement = etablissement;

        const etablissementCollection: IEtablissement[] = [{ id: 94473 }];
        jest.spyOn(etablissementService, 'query').mockReturnValue(of(new HttpResponse({ body: etablissementCollection })));
        const additionalEtablissements = [etablissement];
        const expectedCollection: IEtablissement[] = [...additionalEtablissements, ...etablissementCollection];
        jest.spyOn(etablissementService, 'addEtablissementToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ inscription });
        comp.ngOnInit();

        expect(etablissementService.query).toHaveBeenCalled();
        expect(etablissementService.addEtablissementToCollectionIfMissing).toHaveBeenCalledWith(
          etablissementCollection,
          ...additionalEtablissements
        );
        expect(comp.etablissementsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call AnneeScolaire query and add missing value', () => {
        const inscription: IInscription = { id: 456 };
        const anneeScolaire: IAnneeScolaire = { id: 57899 };
        inscription.anneeScolaire = anneeScolaire;

        const anneeScolaireCollection: IAnneeScolaire[] = [{ id: 70977 }];
        jest.spyOn(anneeScolaireService, 'query').mockReturnValue(of(new HttpResponse({ body: anneeScolaireCollection })));
        const additionalAnneeScolaires = [anneeScolaire];
        const expectedCollection: IAnneeScolaire[] = [...additionalAnneeScolaires, ...anneeScolaireCollection];
        jest.spyOn(anneeScolaireService, 'addAnneeScolaireToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ inscription });
        comp.ngOnInit();

        expect(anneeScolaireService.query).toHaveBeenCalled();
        expect(anneeScolaireService.addAnneeScolaireToCollectionIfMissing).toHaveBeenCalledWith(
          anneeScolaireCollection,
          ...additionalAnneeScolaires
        );
        expect(comp.anneeScolairesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const inscription: IInscription = { id: 456 };
        const periode: IPeriode = { id: 14540 };
        inscription.periode = periode;
        const etablissement: IEtablissement = { id: 57167 };
        inscription.etablissement = etablissement;
        const anneeScolaire: IAnneeScolaire = { id: 23138 };
        inscription.anneeScolaire = anneeScolaire;

        activatedRoute.data = of({ inscription });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(inscription));
        expect(comp.periodesCollection).toContain(periode);
        expect(comp.etablissementsSharedCollection).toContain(etablissement);
        expect(comp.anneeScolairesSharedCollection).toContain(anneeScolaire);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Inscription>>();
        const inscription = { id: 123 };
        jest.spyOn(inscriptionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ inscription });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: inscription }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(inscriptionService.update).toHaveBeenCalledWith(inscription);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Inscription>>();
        const inscription = new Inscription();
        jest.spyOn(inscriptionService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ inscription });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: inscription }));
        saveSubject.complete();

        // THEN
        expect(inscriptionService.create).toHaveBeenCalledWith(inscription);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Inscription>>();
        const inscription = { id: 123 };
        jest.spyOn(inscriptionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ inscription });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(inscriptionService.update).toHaveBeenCalledWith(inscription);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPeriodeById', () => {
        it('Should return tracked Periode primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPeriodeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackEtablissementById', () => {
        it('Should return tracked Etablissement primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEtablissementById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackAnneeScolaireById', () => {
        it('Should return tracked AnneeScolaire primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAnneeScolaireById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
