import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AnneeScolaireComponent } from './list/annee-scolaire.component';
import { AnneeScolaireDetailComponent } from './detail/annee-scolaire-detail.component';
import { AnneeScolaireUpdateComponent } from './update/annee-scolaire-update.component';
import { AnneeScolaireDeleteDialogComponent } from './delete/annee-scolaire-delete-dialog.component';
import { AnneeScolaireRoutingModule } from './route/annee-scolaire-routing.module';

@NgModule({
  imports: [SharedModule, AnneeScolaireRoutingModule],
  declarations: [AnneeScolaireComponent, AnneeScolaireDetailComponent, AnneeScolaireUpdateComponent, AnneeScolaireDeleteDialogComponent],
  entryComponents: [AnneeScolaireDeleteDialogComponent],
})
export class AnneeScolaireModule {}
