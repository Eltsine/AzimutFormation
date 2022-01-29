import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SpecialiteComponent } from './list/specialite.component';
import { SpecialiteDetailComponent } from './detail/specialite-detail.component';
import { SpecialiteUpdateComponent } from './update/specialite-update.component';
import { SpecialiteDeleteDialogComponent } from './delete/specialite-delete-dialog.component';
import { SpecialiteRoutingModule } from './route/specialite-routing.module';

@NgModule({
  imports: [SharedModule, SpecialiteRoutingModule],
  declarations: [SpecialiteComponent, SpecialiteDetailComponent, SpecialiteUpdateComponent, SpecialiteDeleteDialogComponent],
  entryComponents: [SpecialiteDeleteDialogComponent],
})
export class SpecialiteModule {}
