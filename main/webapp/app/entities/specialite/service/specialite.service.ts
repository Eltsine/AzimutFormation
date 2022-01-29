import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISpecialite, getSpecialiteIdentifier } from '../specialite.model';

export type EntityResponseType = HttpResponse<ISpecialite>;
export type EntityArrayResponseType = HttpResponse<ISpecialite[]>;

@Injectable({ providedIn: 'root' })
export class SpecialiteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/specialites');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(specialite: ISpecialite): Observable<EntityResponseType> {
    return this.http.post<ISpecialite>(this.resourceUrl, specialite, { observe: 'response' });
  }

  update(specialite: ISpecialite): Observable<EntityResponseType> {
    return this.http.put<ISpecialite>(`${this.resourceUrl}/${getSpecialiteIdentifier(specialite) as number}`, specialite, {
      observe: 'response',
    });
  }

  partialUpdate(specialite: ISpecialite): Observable<EntityResponseType> {
    return this.http.patch<ISpecialite>(`${this.resourceUrl}/${getSpecialiteIdentifier(specialite) as number}`, specialite, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpecialite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpecialite[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSpecialiteToCollectionIfMissing(
    specialiteCollection: ISpecialite[],
    ...specialitesToCheck: (ISpecialite | null | undefined)[]
  ): ISpecialite[] {
    const specialites: ISpecialite[] = specialitesToCheck.filter(isPresent);
    if (specialites.length > 0) {
      const specialiteCollectionIdentifiers = specialiteCollection.map(specialiteItem => getSpecialiteIdentifier(specialiteItem)!);
      const specialitesToAdd = specialites.filter(specialiteItem => {
        const specialiteIdentifier = getSpecialiteIdentifier(specialiteItem);
        if (specialiteIdentifier == null || specialiteCollectionIdentifiers.includes(specialiteIdentifier)) {
          return false;
        }
        specialiteCollectionIdentifiers.push(specialiteIdentifier);
        return true;
      });
      return [...specialitesToAdd, ...specialiteCollection];
    }
    return specialiteCollection;
  }
}
