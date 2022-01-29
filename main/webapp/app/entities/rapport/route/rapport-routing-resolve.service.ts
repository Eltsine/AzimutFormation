import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRapport, Rapport } from '../rapport.model';
import { RapportService } from '../service/rapport.service';

@Injectable({ providedIn: 'root' })
export class RapportRoutingResolveService implements Resolve<IRapport> {
  constructor(protected service: RapportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRapport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rapport: HttpResponse<Rapport>) => {
          if (rapport.body) {
            return of(rapport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Rapport());
  }
}
