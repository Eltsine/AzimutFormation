import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AnneeScolaireDetailComponent } from './annee-scolaire-detail.component';

describe('Component Tests', () => {
  describe('AnneeScolaire Management Detail Component', () => {
    let comp: AnneeScolaireDetailComponent;
    let fixture: ComponentFixture<AnneeScolaireDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AnneeScolaireDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ anneeScolaire: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AnneeScolaireDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnneeScolaireDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load anneeScolaire on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.anneeScolaire).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
