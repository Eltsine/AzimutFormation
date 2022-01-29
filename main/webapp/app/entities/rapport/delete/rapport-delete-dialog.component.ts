import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRapport } from '../rapport.model';
import { RapportService } from '../service/rapport.service';

@Component({
  templateUrl: './rapport-delete-dialog.component.html',
})
export class RapportDeleteDialogComponent {
  rapport?: IRapport;

  constructor(protected rapportService: RapportService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rapportService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
