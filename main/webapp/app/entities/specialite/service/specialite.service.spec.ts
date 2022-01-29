import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISpecialite, Specialite } from '../specialite.model';

import { SpecialiteService } from './specialite.service';

describe('Service Tests', () => {
  describe('Specialite Service', () => {
    let service: SpecialiteService;
    let httpMock: HttpTestingController;
    let elemDefault: ISpecialite;
    let expectedResult: ISpecialite | ISpecialite[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SpecialiteService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        libSpecialite: 'AAAAAAA',
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

      it('should create a Specialite', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Specialite()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Specialite', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            libSpecialite: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Specialite', () => {
        const patchObject = Object.assign(
          {
            libSpecialite: 'BBBBBB',
          },
          new Specialite()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Specialite', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            libSpecialite: 'BBBBBB',
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

      it('should delete a Specialite', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSpecialiteToCollectionIfMissing', () => {
        it('should add a Specialite to an empty array', () => {
          const specialite: ISpecialite = { id: 123 };
          expectedResult = service.addSpecialiteToCollectionIfMissing([], specialite);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(specialite);
        });

        it('should not add a Specialite to an array that contains it', () => {
          const specialite: ISpecialite = { id: 123 };
          const specialiteCollection: ISpecialite[] = [
            {
              ...specialite,
            },
            { id: 456 },
          ];
          expectedResult = service.addSpecialiteToCollectionIfMissing(specialiteCollection, specialite);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Specialite to an array that doesn't contain it", () => {
          const specialite: ISpecialite = { id: 123 };
          const specialiteCollection: ISpecialite[] = [{ id: 456 }];
          expectedResult = service.addSpecialiteToCollectionIfMissing(specialiteCollection, specialite);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(specialite);
        });

        it('should add only unique Specialite to an array', () => {
          const specialiteArray: ISpecialite[] = [{ id: 123 }, { id: 456 }, { id: 18394 }];
          const specialiteCollection: ISpecialite[] = [{ id: 123 }];
          expectedResult = service.addSpecialiteToCollectionIfMissing(specialiteCollection, ...specialiteArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const specialite: ISpecialite = { id: 123 };
          const specialite2: ISpecialite = { id: 456 };
          expectedResult = service.addSpecialiteToCollectionIfMissing([], specialite, specialite2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(specialite);
          expect(expectedResult).toContain(specialite2);
        });

        it('should accept null and undefined values', () => {
          const specialite: ISpecialite = { id: 123 };
          expectedResult = service.addSpecialiteToCollectionIfMissing([], null, specialite, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(specialite);
        });

        it('should return initial array if no Specialite is added', () => {
          const specialiteCollection: ISpecialite[] = [{ id: 123 }];
          expectedResult = service.addSpecialiteToCollectionIfMissing(specialiteCollection, undefined, null);
          expect(expectedResult).toEqual(specialiteCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
