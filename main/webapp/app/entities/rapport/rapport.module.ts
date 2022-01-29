import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RapportComponent } from './list/rapport.component';
import { RapportDetailComponent } from './detail/rapport-detail.component';
import { RapportUpdateComponent } from './update/rapport-update.component';
import { RapportDeleteDialogComponent } from './delete/rapport-delete-dialog.component';
import { RapportRoutingModule } from './route/rapport-routing.module';

@NgModule({
  imports: [SharedModule, RapportRoutingModule],
  declarations: [RapportComponent, RapportDetailComponent, RapportUpdateComponent, RapportDeleteDialogComponent],
  entryComponents: [RapportDeleteDialogComponent],
})
export class RapportModule {}
