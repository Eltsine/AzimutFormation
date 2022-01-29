import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SpecialiteComponent } from '../list/specialite.component';
import { SpecialiteDetailComponent } from '../detail/specialite-detail.component';
import { SpecialiteUpdateComponent } from '../update/specialite-update.component';
import { SpecialiteRoutingResolveService } from './specialite-routing-resolve.service';

const specialiteRoute: Routes = [
  {
    path: '',
    component: SpecialiteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpecialiteDetailComponent,
    resolve: {
      specialite: SpecialiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpecialiteUpdateComponent,
    resolve: {
      specialite: SpecialiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpecialiteUpdateComponent,
    resolve: {
      specialite: SpecialiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(specialiteRoute)],
  exports: [RouterModule],
})
export class SpecialiteRoutingModule {}
