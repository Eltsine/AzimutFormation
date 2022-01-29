import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RapportDetailComponent } from './rapport-detail.component';

describe('Component Tests', () => {
  describe('Rapport Management Detail Component', () => {
    let comp: RapportDetailComponent;
    let fixture: ComponentFixture<RapportDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RapportDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ rapport: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(RapportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RapportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rapport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rapport).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
