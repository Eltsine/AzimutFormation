import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PeriodeComponent } from '../list/periode.component';
import { PeriodeDetailComponent } from '../detail/periode-detail.component';
import { PeriodeUpdateComponent } from '../update/periode-update.component';
import { PeriodeRoutingResolveService } from './periode-routing-resolve.service';

const periodeRoute: Routes = [
  {
    path: '',
    component: PeriodeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PeriodeDetailComponent,
    resolve: {
      periode: PeriodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PeriodeUpdateComponent,
    resolve: {
      periode: PeriodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PeriodeUpdateComponent,
    resolve: {
      periode: PeriodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(periodeRoute)],
  exports: [RouterModule],
})
export class PeriodeRoutingModule {}
