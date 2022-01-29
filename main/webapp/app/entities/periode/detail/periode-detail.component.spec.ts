import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeriodeDetailComponent } from './periode-detail.component';

describe('Component Tests', () => {
  describe('Periode Management Detail Component', () => {
    let comp: PeriodeDetailComponent;
    let fixture: ComponentFixture<PeriodeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PeriodeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ periode: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PeriodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PeriodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load periode on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.periode).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
