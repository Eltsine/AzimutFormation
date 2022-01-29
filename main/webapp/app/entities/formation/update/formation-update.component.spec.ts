jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FormationService } from '../service/formation.service';
import { IFormation, Formation } from '../formation.model';
import { IRapport } from 'app/entities/rapport/rapport.model';
import { RapportService } from 'app/entities/rapport/service/rapport.service';
import { IInscription } from 'app/entities/inscription/inscription.model';
import { InscriptionService } from 'app/entities/inscription/service/inscription.service';

import { FormationUpdateComponent } from './formation-update.component';

describe('Component Tests', () => {
  describe('Formation Management Update Component', () => {
    let comp: FormationUpdateComponent;
    let fixture: ComponentFixture<FormationUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let formationService: FormationService;
    let rapportService: RapportService;
    let inscriptionService: InscriptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FormationUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FormationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormationUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      formationService = TestBed.inject(FormationService);
      rapportService = TestBed.inject(RapportService);
      inscriptionService = TestBed.inject(InscriptionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call rapport query and add missing value', () => {
        const formation: IFormation = { id: 456 };
        const rapport: IRapport = { id: 20205 };
        formation.rapport = rapport;

        const rapportCollection: IRapport[] = [{ id: 78165 }];
        jest.spyOn(rapportService, 'query').mockReturnValue(of(new HttpResponse({ body: rapportCollection })));
        const expectedCollection: IRapport[] = [rapport, ...rapportCollection];
        jest.spyOn(rapportService, 'addRapportToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ formation });
        comp.ngOnInit();

        expect(rapportService.query).toHaveBeenCalled();
        expect(rapportService.addRapportToCollectionIfMissing).toHaveBeenCalledWith(rapportCollection, rapport);
        expect(comp.rapportsCollection).toEqual(expectedCollection);
      });

      it('Should call Inscription query and add missing value', () => {
        const formation: IFormation = { id: 456 };
        const inscription: IInscription = { id: 29101 };
        formation.inscription = inscription;

        const inscriptionCollection: IInscription[] = [{ id: 98764 }];
        jest.spyOn(inscriptionService, 'query').mockReturnValue(of(new HttpResponse({ body: inscriptionCollection })));
        const additionalInscriptions = [inscription];
        const expectedCollection: IInscription[] = [...additionalInscriptions, ...inscriptionCollection];
        jest.spyOn(inscriptionService, 'addInscriptionToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ formation });
        comp.ngOnInit();

        expect(inscriptionService.query).toHaveBeenCalled();
        expect(inscriptionService.addInscriptionToCollectionIfMissing).toHaveBeenCalledWith(
          inscriptionCollection,
          ...additionalInscriptions
        );
        expect(comp.inscriptionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const formation: IFormation = { id: 456 };
        const rapport: IRapport = { id: 17129 };
        formation.rapport = rapport;
        const inscription: IInscription = { id: 64365 };
        formation.inscription = inscription;

        activatedRoute.data = of({ formation });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(formation));
        expect(comp.rapportsCollection).toContain(rapport);
        expect(comp.inscriptionsSharedCollection).toContain(inscription);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Formation>>();
        const formation = { id: 123 };
        jest.spyOn(formationService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ formation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: formation }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(formationService.update).toHaveBeenCalledWith(formation);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Formation>>();
        const formation = new Formation();
        jest.spyOn(formationService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ formation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: formation }));
        saveSubject.complete();

        // THEN
        expect(formationService.create).toHaveBeenCalledWith(formation);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Formation>>();
        const formation = { id: 123 };
        jest.spyOn(formationService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ formation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(formationService.update).toHaveBeenCalledWith(formation);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackRapportById', () => {
        it('Should return tracked Rapport primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackRapportById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackInscriptionById', () => {
        it('Should return tracked Inscription primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackInscriptionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
