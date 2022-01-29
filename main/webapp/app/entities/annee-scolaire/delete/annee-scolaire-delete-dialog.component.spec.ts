jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AnneeScolaireService } from '../service/annee-scolaire.service';

import { AnneeScolaireDeleteDialogComponent } from './annee-scolaire-delete-dialog.component';

describe('Component Tests', () => {
  describe('AnneeScolaire Management Delete Component', () => {
    let comp: AnneeScolaireDeleteDialogComponent;
    let fixture: ComponentFixture<AnneeScolaireDeleteDialogComponent>;
    let service: AnneeScolaireService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AnneeScolaireDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(AnneeScolaireDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnneeScolaireDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(AnneeScolaireService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        jest.spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
