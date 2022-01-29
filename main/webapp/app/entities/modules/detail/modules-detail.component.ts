import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModules } from '../modules.model';

@Component({
  selector: 'jhi-modules-detail',
  templateUrl: './modules-detail.component.html',
})
export class ModulesDetailComponent implements OnInit {
  modules: IModules | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modules }) => {
      this.modules = modules;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
