jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPeriode, Periode } from '../periode.model';
import { PeriodeService } from '../service/periode.service';

import { PeriodeRoutingResolveService } from './periode-routing-resolve.service';

describe('Service Tests', () => {
  describe('Periode routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PeriodeRoutingResolveService;
    let service: PeriodeService;
    let resultPeriode: IPeriode | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PeriodeRoutingResolveService);
      service = TestBed.inject(PeriodeService);
      resultPeriode = undefined;
    });

    describe('resolve', () => {
      it('should return IPeriode returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPeriode = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPeriode).toEqual({ id: 123 });
      });

      it('should return new IPeriode if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPeriode = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPeriode).toEqual(new Periode());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Periode })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPeriode = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPeriode).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
