import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeriode } from '../periode.model';
import { PeriodeService } from '../service/periode.service';

@Component({
  templateUrl: './periode-delete-dialog.component.html',
})
export class PeriodeDeleteDialogComponent {
  periode?: IPeriode;

  constructor(protected periodeService: PeriodeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.periodeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
