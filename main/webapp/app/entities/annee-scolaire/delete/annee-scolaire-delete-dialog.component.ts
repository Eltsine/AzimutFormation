import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnneeScolaire } from '../annee-scolaire.model';
import { AnneeScolaireService } from '../service/annee-scolaire.service';

@Component({
  templateUrl: './annee-scolaire-delete-dialog.component.html',
})
export class AnneeScolaireDeleteDialogComponent {
  anneeScolaire?: IAnneeScolaire;

  constructor(protected anneeScolaireService: AnneeScolaireService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anneeScolaireService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
