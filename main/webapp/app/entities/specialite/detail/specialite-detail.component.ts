import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpecialite } from '../specialite.model';

@Component({
  selector: 'jhi-specialite-detail',
  templateUrl: './specialite-detail.component.html',
})
export class SpecialiteDetailComponent implements OnInit {
  specialite: ISpecialite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialite }) => {
      this.specialite = specialite;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
