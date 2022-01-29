import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IModules, Modules } from '../modules.model';
import { ModulesService } from '../service/modules.service';

@Injectable({ providedIn: 'root' })
export class ModulesRoutingResolveService implements Resolve<IModules> {
  constructor(protected service: ModulesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModules> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((modules: HttpResponse<Modules>) => {
          if (modules.body) {
            return of(modules.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Modules());
  }
}
