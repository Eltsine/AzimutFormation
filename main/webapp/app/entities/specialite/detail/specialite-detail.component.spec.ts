import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpecialiteDetailComponent } from './specialite-detail.component';

describe('Component Tests', () => {
  describe('Specialite Management Detail Component', () => {
    let comp: SpecialiteDetailComponent;
    let fixture: ComponentFixture<SpecialiteDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [SpecialiteDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ specialite: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(SpecialiteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpecialiteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load specialite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.specialite).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
