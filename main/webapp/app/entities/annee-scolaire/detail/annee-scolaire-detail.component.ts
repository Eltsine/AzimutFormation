import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnneeScolaire } from '../annee-scolaire.model';

@Component({
  selector: 'jhi-annee-scolaire-detail',
  templateUrl: './annee-scolaire-detail.component.html',
})
export class AnneeScolaireDetailComponent implements OnInit {
  anneeScolaire: IAnneeScolaire | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anneeScolaire }) => {
      this.anneeScolaire = anneeScolaire;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
