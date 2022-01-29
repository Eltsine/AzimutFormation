import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPeriode, getPeriodeIdentifier } from '../periode.model';

export type EntityResponseType = HttpResponse<IPeriode>;
export type EntityArrayResponseType = HttpResponse<IPeriode[]>;

@Injectable({ providedIn: 'root' })
export class PeriodeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/periodes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(periode: IPeriode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periode);
    return this.http
      .post<IPeriode>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(periode: IPeriode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periode);
    return this.http
      .put<IPeriode>(`${this.resourceUrl}/${getPeriodeIdentifier(periode) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(periode: IPeriode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periode);
    return this.http
      .patch<IPeriode>(`${this.resourceUrl}/${getPeriodeIdentifier(periode) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPeriode>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPeriode[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPeriodeToCollectionIfMissing(periodeCollection: IPeriode[], ...periodesToCheck: (IPeriode | null | undefined)[]): IPeriode[] {
    const periodes: IPeriode[] = periodesToCheck.filter(isPresent);
    if (periodes.length > 0) {
      const periodeCollectionIdentifiers = periodeCollection.map(periodeItem => getPeriodeIdentifier(periodeItem)!);
      const periodesToAdd = periodes.filter(periodeItem => {
        const periodeIdentifier = getPeriodeIdentifier(periodeItem);
        if (periodeIdentifier == null || periodeCollectionIdentifiers.includes(periodeIdentifier)) {
          return false;
        }
        periodeCollectionIdentifiers.push(periodeIdentifier);
        return true;
      });
      return [...periodesToAdd, ...periodeCollection];
    }
    return periodeCollection;
  }

  protected convertDateFromClient(periode: IPeriode): IPeriode {
    return Object.assign({}, periode, {
      dateDebut: periode.dateDebut?.isValid() ? periode.dateDebut.toJSON() : undefined,
      dateFin: periode.dateFin?.isValid() ? periode.dateFin.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? dayjs(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? dayjs(res.body.dateFin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((periode: IPeriode) => {
        periode.dateDebut = periode.dateDebut ? dayjs(periode.dateDebut) : undefined;
        periode.dateFin = periode.dateFin ? dayjs(periode.dateFin) : undefined;
      });
    }
    return res;
  }
}
