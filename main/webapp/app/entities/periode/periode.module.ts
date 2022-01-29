import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PeriodeComponent } from './list/periode.component';
import { PeriodeDetailComponent } from './detail/periode-detail.component';
import { PeriodeUpdateComponent } from './update/periode-update.component';
import { PeriodeDeleteDialogComponent } from './delete/periode-delete-dialog.component';
import { PeriodeRoutingModule } from './route/periode-routing.module';

@NgModule({
  imports: [SharedModule, PeriodeRoutingModule],
  declarations: [PeriodeComponent, PeriodeDetailComponent, PeriodeUpdateComponent, PeriodeDeleteDialogComponent],
  entryComponents: [PeriodeDeleteDialogComponent],
})
export class PeriodeModule {}
