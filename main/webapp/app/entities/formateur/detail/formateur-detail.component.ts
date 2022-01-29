import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormateur } from '../formateur.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-formateur-detail',
  templateUrl: './formateur-detail.component.html',
})
export class FormateurDetailComponent implements OnInit {
  formateur: IFormateur | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formateur }) => {
      this.formateur = formateur;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
