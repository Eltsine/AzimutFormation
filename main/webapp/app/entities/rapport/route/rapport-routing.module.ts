import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RapportComponent } from '../list/rapport.component';
import { RapportDetailComponent } from '../detail/rapport-detail.component';
import { RapportUpdateComponent } from '../update/rapport-update.component';
import { RapportRoutingResolveService } from './rapport-routing-resolve.service';

const rapportRoute: Routes = [
  {
    path: '',
    component: RapportComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RapportDetailComponent,
    resolve: {
      rapport: RapportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RapportUpdateComponent,
    resolve: {
      rapport: RapportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RapportUpdateComponent,
    resolve: {
      rapport: RapportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rapportRoute)],
  exports: [RouterModule],
})
export class RapportRoutingModule {}
