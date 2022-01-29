import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRapport } from '../rapport.model';

@Component({
  selector: 'jhi-rapport-detail',
  templateUrl: './rapport-detail.component.html',
})
export class RapportDetailComponent implements OnInit {
  rapport: IRapport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rapport }) => {
      this.rapport = rapport;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
