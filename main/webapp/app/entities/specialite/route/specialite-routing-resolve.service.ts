import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISpecialite, Specialite } from '../specialite.model';
import { SpecialiteService } from '../service/specialite.service';

@Injectable({ providedIn: 'root' })
export class SpecialiteRoutingResolveService implements Resolve<ISpecialite> {
  constructor(protected service: SpecialiteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpecialite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((specialite: HttpResponse<Specialite>) => {
          if (specialite.body) {
            return of(specialite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Specialite());
  }
}
