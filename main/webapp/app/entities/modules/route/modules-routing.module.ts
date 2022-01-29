import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ModulesComponent } from '../list/modules.component';
import { ModulesDetailComponent } from '../detail/modules-detail.component';
import { ModulesUpdateComponent } from '../update/modules-update.component';
import { ModulesRoutingResolveService } from './modules-routing-resolve.service';

const modulesRoute: Routes = [
  {
    path: '',
    component: ModulesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModulesDetailComponent,
    resolve: {
      modules: ModulesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModulesUpdateComponent,
    resolve: {
      modules: ModulesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModulesUpdateComponent,
    resolve: {
      modules: ModulesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(modulesRoute)],
  exports: [RouterModule],
})
export class ModulesRoutingModule {}
