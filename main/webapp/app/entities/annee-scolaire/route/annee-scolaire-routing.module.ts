import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AnneeScolaireComponent } from '../list/annee-scolaire.component';
import { AnneeScolaireDetailComponent } from '../detail/annee-scolaire-detail.component';
import { AnneeScolaireUpdateComponent } from '../update/annee-scolaire-update.component';
import { AnneeScolaireRoutingResolveService } from './annee-scolaire-routing-resolve.service';

const anneeScolaireRoute: Routes = [
  {
    path: '',
    component: AnneeScolaireComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnneeScolaireDetailComponent,
    resolve: {
      anneeScolaire: AnneeScolaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnneeScolaireUpdateComponent,
    resolve: {
      anneeScolaire: AnneeScolaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnneeScolaireUpdateComponent,
    resolve: {
      anneeScolaire: AnneeScolaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(anneeScolaireRoute)],
  exports: [RouterModule],
})
export class AnneeScolaireRoutingModule {}
