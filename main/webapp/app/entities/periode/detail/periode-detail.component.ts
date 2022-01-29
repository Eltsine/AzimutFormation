import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriode } from '../periode.model';

@Component({
  selector: 'jhi-periode-detail',
  templateUrl: './periode-detail.component.html',
})
export class PeriodeDetailComponent implements OnInit {
  periode: IPeriode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periode }) => {
      this.periode = periode;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
