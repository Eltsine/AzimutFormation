jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRapport, Rapport } from '../rapport.model';
import { RapportService } from '../service/rapport.service';

import { RapportRoutingResolveService } from './rapport-routing-resolve.service';

describe('Service Tests', () => {
  describe('Rapport routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: RapportRoutingResolveService;
    let service: RapportService;
    let resultRapport: IRapport | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(RapportRoutingResolveService);
      service = TestBed.inject(RapportService);
      resultRapport = undefined;
    });

    describe('resolve', () => {
      it('should return IRapport returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRapport = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRapport).toEqual({ id: 123 });
      });

      it('should return new IRapport if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRapport = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultRapport).toEqual(new Rapport());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Rapport })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRapport = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRapport).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
