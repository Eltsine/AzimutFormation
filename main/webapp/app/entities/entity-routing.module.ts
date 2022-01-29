import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'annee-scolaire',
        data: { pageTitle: 'azimutApp.anneeScolaire.home.title' },
        loadChildren: () => import('./annee-scolaire/annee-scolaire.module').then(m => m.AnneeScolaireModule),
      },
      {
        path: 'periode',
        data: { pageTitle: 'azimutApp.periode.home.title' },
        loadChildren: () => import('./periode/periode.module').then(m => m.PeriodeModule),
      },
      {
        path: 'formation',
        data: { pageTitle: 'azimutApp.formation.home.title' },
        loadChildren: () => import('./formation/formation.module').then(m => m.FormationModule),
      },
      {
        path: 'modules',
        data: { pageTitle: 'azimutApp.modules.home.title' },
        loadChildren: () => import('./modules/modules.module').then(m => m.ModulesModule),
      },
      {
        path: 'rapport',
        data: { pageTitle: 'azimutApp.rapport.home.title' },
        loadChildren: () => import('./rapport/rapport.module').then(m => m.RapportModule),
      },
      {
        path: 'session',
        data: { pageTitle: 'azimutApp.session.home.title' },
        loadChildren: () => import('./session/session.module').then(m => m.SessionModule),
      },
      {
        path: 'salle',
        data: { pageTitle: 'azimutApp.salle.home.title' },
        loadChildren: () => import('./salle/salle.module').then(m => m.SalleModule),
      },
      {
        path: 'formateur',
        data: { pageTitle: 'azimutApp.formateur.home.title' },
        loadChildren: () => import('./formateur/formateur.module').then(m => m.FormateurModule),
      },
      {
        path: 'specialite',
        data: { pageTitle: 'azimutApp.specialite.home.title' },
        loadChildren: () => import('./specialite/specialite.module').then(m => m.SpecialiteModule),
      },
      {
        path: 'apprenant',
        data: { pageTitle: 'azimutApp.apprenant.home.title' },
        loadChildren: () => import('./apprenant/apprenant.module').then(m => m.ApprenantModule),
      },
      {
        path: 'etablissement',
        data: { pageTitle: 'azimutApp.etablissement.home.title' },
        loadChildren: () => import('./etablissement/etablissement.module').then(m => m.EtablissementModule),
      },
      {
        path: 'inscription',
        data: { pageTitle: 'azimutApp.inscription.home.title' },
        loadChildren: () => import('./inscription/inscription.module').then(m => m.InscriptionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
