import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPeriode, Periode } from '../periode.model';

import { PeriodeService } from './periode.service';

describe('Service Tests', () => {
  describe('Periode Service', () => {
    let service: PeriodeService;
    let httpMock: HttpTestingController;
    let elemDefault: IPeriode;
    let expectedResult: IPeriode | IPeriode[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PeriodeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        duree: 'AAAAAAA',
        dateDebut: currentDate,
        dateFin: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateDebut: currentDate.format(DATE_TIME_FORMAT),
            dateFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Periode', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateDebut: currentDate.format(DATE_TIME_FORMAT),
            dateFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );

        service.create(new Periode()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Periode', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            duree: 'BBBBBB',
            dateDebut: currentDate.format(DATE_TIME_FORMAT),
            dateFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Periode', () => {
        const patchObject = Object.assign(
          {
            duree: 'BBBBBB',
          },
          new Periode()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Periode', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            duree: 'BBBBBB',
            dateDebut: currentDate.format(DATE_TIME_FORMAT),
            dateFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Periode', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPeriodeToCollectionIfMissing', () => {
        it('should add a Periode to an empty array', () => {
          const periode: IPeriode = { id: 123 };
          expectedResult = service.addPeriodeToCollectionIfMissing([], periode);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(periode);
        });

        it('should not add a Periode to an array that contains it', () => {
          const periode: IPeriode = { id: 123 };
          const periodeCollection: IPeriode[] = [
            {
              ...periode,
            },
            { id: 456 },
          ];
          expectedResult = service.addPeriodeToCollectionIfMissing(periodeCollection, periode);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Periode to an array that doesn't contain it", () => {
          const periode: IPeriode = { id: 123 };
          const periodeCollection: IPeriode[] = [{ id: 456 }];
          expectedResult = service.addPeriodeToCollectionIfMissing(periodeCollection, periode);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(periode);
        });

        it('should add only unique Periode to an array', () => {
          const periodeArray: IPeriode[] = [{ id: 123 }, { id: 456 }, { id: 70807 }];
          const periodeCollection: IPeriode[] = [{ id: 123 }];
          expectedResult = service.addPeriodeToCollectionIfMissing(periodeCollection, ...periodeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const periode: IPeriode = { id: 123 };
          const periode2: IPeriode = { id: 456 };
          expectedResult = service.addPeriodeToCollectionIfMissing([], periode, periode2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(periode);
          expect(expectedResult).toContain(periode2);
        });

        it('should accept null and undefined values', () => {
          const periode: IPeriode = { id: 123 };
          expectedResult = service.addPeriodeToCollectionIfMissing([], null, periode, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(periode);
        });

        it('should return initial array if no Periode is added', () => {
          const periodeCollection: IPeriode[] = [{ id: 123 }];
          expectedResult = service.addPeriodeToCollectionIfMissing(periodeCollection, undefined, null);
          expect(expectedResult).toEqual(periodeCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
