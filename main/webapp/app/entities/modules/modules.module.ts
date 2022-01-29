import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ModulesComponent } from './list/modules.component';
import { ModulesDetailComponent } from './detail/modules-detail.component';
import { ModulesUpdateComponent } from './update/modules-update.component';
import { ModulesDeleteDialogComponent } from './delete/modules-delete-dialog.component';
import { ModulesRoutingModule } from './route/modules-routing.module';

@NgModule({
  imports: [SharedModule, ModulesRoutingModule],
  declarations: [ModulesComponent, ModulesDetailComponent, ModulesUpdateComponent, ModulesDeleteDialogComponent],
  entryComponents: [ModulesDeleteDialogComponent],
})
export class ModulesModule {}
