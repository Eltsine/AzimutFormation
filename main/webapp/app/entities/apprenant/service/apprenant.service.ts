import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApprenant, getApprenantIdentifier } from '../apprenant.model';

export type EntityResponseType = HttpResponse<IApprenant>;
export type EntityArrayResponseType = HttpResponse<IApprenant[]>;

@Injectable({ providedIn: 'root' })
export class ApprenantService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/apprenants');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(apprenant: IApprenant): Observable<EntityResponseType> {
    return this.http.post<IApprenant>(this.resourceUrl, apprenant, { observe: 'response' });
  }

  update(apprenant: IApprenant): Observable<EntityResponseType> {
    return this.http.put<IApprenant>(`${this.resourceUrl}/${getApprenantIdentifier(apprenant) as number}`, apprenant, {
      observe: 'response',
    });
  }

  partialUpdate(apprenant: IApprenant): Observable<EntityResponseType> {
    return this.http.patch<IApprenant>(`${this.resourceUrl}/${getApprenantIdentifier(apprenant) as number}`, apprenant, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApprenant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApprenant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addApprenantToCollectionIfMissing(
    apprenantCollection: IApprenant[],
    ...apprenantsToCheck: (IApprenant | null | undefined)[]
  ): IApprenant[] {
    const apprenants: IApprenant[] = apprenantsToCheck.filter(isPresent);
    if (apprenants.length > 0) {
      const apprenantCollectionIdentifiers = apprenantCollection.map(apprenantItem => getApprenantIdentifier(apprenantItem)!);
      const apprenantsToAdd = apprenants.filter(apprenantItem => {
        const apprenantIdentifier = getApprenantIdentifier(apprenantItem);
        if (apprenantIdentifier == null || apprenantCollectionIdentifiers.includes(apprenantIdentifier)) {
          return false;
        }
        apprenantCollectionIdentifiers.push(apprenantIdentifier);
        return true;
      });
      return [...apprenantsToAdd, ...apprenantCollection];
    }
    return apprenantCollection;
  }
}
