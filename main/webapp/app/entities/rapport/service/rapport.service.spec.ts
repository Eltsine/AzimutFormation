import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRapport, Rapport } from '../rapport.model';

import { RapportService } from './rapport.service';

describe('Service Tests', () => {
  describe('Rapport Service', () => {
    let service: RapportService;
    let httpMock: HttpTestingController;
    let elemDefault: IRapport;
    let expectedResult: IRapport | IRapport[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RapportService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
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

      it('should create a Rapport', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Rapport()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Rapport', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Rapport', () => {
        const patchObject = Object.assign({}, new Rapport());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Rapport', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
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

      it('should delete a Rapport', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRapportToCollectionIfMissing', () => {
        it('should add a Rapport to an empty array', () => {
          const rapport: IRapport = { id: 123 };
          expectedResult = service.addRapportToCollectionIfMissing([], rapport);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(rapport);
        });

        it('should not add a Rapport to an array that contains it', () => {
          const rapport: IRapport = { id: 123 };
          const rapportCollection: IRapport[] = [
            {
              ...rapport,
            },
            { id: 456 },
          ];
          expectedResult = service.addRapportToCollectionIfMissing(rapportCollection, rapport);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Rapport to an array that doesn't contain it", () => {
          const rapport: IRapport = { id: 123 };
          const rapportCollection: IRapport[] = [{ id: 456 }];
          expectedResult = service.addRapportToCollectionIfMissing(rapportCollection, rapport);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(rapport);
        });

        it('should add only unique Rapport to an array', () => {
          const rapportArray: IRapport[] = [{ id: 123 }, { id: 456 }, { id: 81880 }];
          const rapportCollection: IRapport[] = [{ id: 123 }];
          expectedResult = service.addRapportToCollectionIfMissing(rapportCollection, ...rapportArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const rapport: IRapport = { id: 123 };
          const rapport2: IRapport = { id: 456 };
          expectedResult = service.addRapportToCollectionIfMissing([], rapport, rapport2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(rapport);
          expect(expectedResult).toContain(rapport2);
        });

        it('should accept null and undefined values', () => {
          const rapport: IRapport = { id: 123 };
          expectedResult = service.addRapportToCollectionIfMissing([], null, rapport, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(rapport);
        });

        it('should return initial array if no Rapport is added', () => {
          const rapportCollection: IRapport[] = [{ id: 123 }];
          expectedResult = service.addRapportToCollectionIfMissing(rapportCollection, undefined, null);
          expect(expectedResult).toEqual(rapportCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
