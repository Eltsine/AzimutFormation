import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { NomModules } from 'app/entities/enumerations/nom-modules.model';
import { IModules, Modules } from '../modules.model';

import { ModulesService } from './modules.service';

describe('Service Tests', () => {
  describe('Modules Service', () => {
    let service: ModulesService;
    let httpMock: HttpTestingController;
    let elemDefault: IModules;
    let expectedResult: IModules | IModules[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ModulesService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nomModule: NomModules.Math_PC,
        prix: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Modules', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Modules()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Modules', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nomModule: 'BBBBBB',
            prix: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Modules', () => {
        const patchObject = Object.assign({}, new Modules());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Modules', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nomModule: 'BBBBBB',
            prix: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Modules', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addModulesToCollectionIfMissing', () => {
        it('should add a Modules to an empty array', () => {
          const modules: IModules = { id: 123 };
          expectedResult = service.addModulesToCollectionIfMissing([], modules);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(modules);
        });

        it('should not add a Modules to an array that contains it', () => {
          const modules: IModules = { id: 123 };
          const modulesCollection: IModules[] = [
            {
              ...modules,
            },
            { id: 456 },
          ];
          expectedResult = service.addModulesToCollectionIfMissing(modulesCollection, modules);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Modules to an array that doesn't contain it", () => {
          const modules: IModules = { id: 123 };
          const modulesCollection: IModules[] = [{ id: 456 }];
          expectedResult = service.addModulesToCollectionIfMissing(modulesCollection, modules);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(modules);
        });

        it('should add only unique Modules to an array', () => {
          const modulesArray: IModules[] = [{ id: 123 }, { id: 456 }, { id: 97558 }];
          const modulesCollection: IModules[] = [{ id: 123 }];
          expectedResult = service.addModulesToCollectionIfMissing(modulesCollection, ...modulesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const modules: IModules = { id: 123 };
          const modules2: IModules = { id: 456 };
          expectedResult = service.addModulesToCollectionIfMissing([], modules, modules2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(modules);
          expect(expectedResult).toContain(modules2);
        });

        it('should accept null and undefined values', () => {
          const modules: IModules = { id: 123 };
          expectedResult = service.addModulesToCollectionIfMissing([], null, modules, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(modules);
        });

        it('should return initial array if no Modules is added', () => {
          const modulesCollection: IModules[] = [{ id: 123 }];
          expectedResult = service.addModulesToCollectionIfMissing(modulesCollection, undefined, null);
          expect(expectedResult).toEqual(modulesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
