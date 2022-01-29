import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAnneeScolaire, AnneeScolaire } from '../annee-scolaire.model';

import { AnneeScolaireService } from './annee-scolaire.service';

describe('Service Tests', () => {
  describe('AnneeScolaire Service', () => {
    let service: AnneeScolaireService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnneeScolaire;
    let expectedResult: IAnneeScolaire | IAnneeScolaire[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AnneeScolaireService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        libelleAnnee: 'AAAAAAA',
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

      it('should create a AnneeScolaire', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AnneeScolaire()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AnneeScolaire', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            libelleAnnee: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a AnneeScolaire', () => {
        const patchObject = Object.assign({}, new AnneeScolaire());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AnneeScolaire', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            libelleAnnee: 'BBBBBB',
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

      it('should delete a AnneeScolaire', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAnneeScolaireToCollectionIfMissing', () => {
        it('should add a AnneeScolaire to an empty array', () => {
          const anneeScolaire: IAnneeScolaire = { id: 123 };
          expectedResult = service.addAnneeScolaireToCollectionIfMissing([], anneeScolaire);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(anneeScolaire);
        });

        it('should not add a AnneeScolaire to an array that contains it', () => {
          const anneeScolaire: IAnneeScolaire = { id: 123 };
          const anneeScolaireCollection: IAnneeScolaire[] = [
            {
              ...anneeScolaire,
            },
            { id: 456 },
          ];
          expectedResult = service.addAnneeScolaireToCollectionIfMissing(anneeScolaireCollection, anneeScolaire);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a AnneeScolaire to an array that doesn't contain it", () => {
          const anneeScolaire: IAnneeScolaire = { id: 123 };
          const anneeScolaireCollection: IAnneeScolaire[] = [{ id: 456 }];
          expectedResult = service.addAnneeScolaireToCollectionIfMissing(anneeScolaireCollection, anneeScolaire);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(anneeScolaire);
        });

        it('should add only unique AnneeScolaire to an array', () => {
          const anneeScolaireArray: IAnneeScolaire[] = [{ id: 123 }, { id: 456 }, { id: 36111 }];
          const anneeScolaireCollection: IAnneeScolaire[] = [{ id: 123 }];
          expectedResult = service.addAnneeScolaireToCollectionIfMissing(anneeScolaireCollection, ...anneeScolaireArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const anneeScolaire: IAnneeScolaire = { id: 123 };
          const anneeScolaire2: IAnneeScolaire = { id: 456 };
          expectedResult = service.addAnneeScolaireToCollectionIfMissing([], anneeScolaire, anneeScolaire2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(anneeScolaire);
          expect(expectedResult).toContain(anneeScolaire2);
        });

        it('should accept null and undefined values', () => {
          const anneeScolaire: IAnneeScolaire = { id: 123 };
          expectedResult = service.addAnneeScolaireToCollectionIfMissing([], null, anneeScolaire, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(anneeScolaire);
        });

        it('should return initial array if no AnneeScolaire is added', () => {
          const anneeScolaireCollection: IAnneeScolaire[] = [{ id: 123 }];
          expectedResult = service.addAnneeScolaireToCollectionIfMissing(anneeScolaireCollection, undefined, null);
          expect(expectedResult).toEqual(anneeScolaireCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
