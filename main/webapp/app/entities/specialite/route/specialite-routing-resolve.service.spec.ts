jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISpecialite, Specialite } from '../specialite.model';
import { SpecialiteService } from '../service/specialite.service';

import { SpecialiteRoutingResolveService } from './specialite-routing-resolve.service';

describe('Service Tests', () => {
  describe('Specialite routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: SpecialiteRoutingResolveService;
    let service: SpecialiteService;
    let resultSpecialite: ISpecialite | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(SpecialiteRoutingResolveService);
      service = TestBed.inject(SpecialiteService);
      resultSpecialite = undefined;
    });

    describe('resolve', () => {
      it('should return ISpecialite returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSpecialite = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSpecialite).toEqual({ id: 123 });
      });

      it('should return new ISpecialite if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSpecialite = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultSpecialite).toEqual(new Specialite());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Specialite })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSpecialite = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSpecialite).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
