import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ModulesDetailComponent } from './modules-detail.component';

describe('Component Tests', () => {
  describe('Modules Management Detail Component', () => {
    let comp: ModulesDetailComponent;
    let fixture: ComponentFixture<ModulesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ModulesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ modules: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ModulesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModulesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load modules on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.modules).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
