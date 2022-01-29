import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IModules } from '../modules.model';
import { ModulesService } from '../service/modules.service';

@Component({
  templateUrl: './modules-delete-dialog.component.html',
})
export class ModulesDeleteDialogComponent {
  modules?: IModules;

  constructor(protected modulesService: ModulesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modulesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
