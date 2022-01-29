import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPeriode, Periode } from '../periode.model';
import { PeriodeService } from '../service/periode.service';

@Injectable({ providedIn: 'root' })
export class PeriodeRoutingResolveService implements Resolve<IPeriode> {
  constructor(protected service: PeriodeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPeriode> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((periode: HttpResponse<Periode>) => {
          if (periode.body) {
            return of(periode.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Periode());
  }
}
