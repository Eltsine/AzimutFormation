import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormation } from '../formation.model';

@Component({
  selector: 'jhi-formation-detail',
  templateUrl: './formation-detail.component.html',
})
export class FormationDetailComponent implements OnInit {
  formation: IFormation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formation }) => {
      this.formation = formation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
