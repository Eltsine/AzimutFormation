import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRapport, getRapportIdentifier } from '../rapport.model';

export type EntityResponseType = HttpResponse<IRapport>;
export type EntityArrayResponseType = HttpResponse<IRapport[]>;

@Injectable({ providedIn: 'root' })
export class RapportService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rapports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rapport: IRapport): Observable<EntityResponseType> {
    return this.http.post<IRapport>(this.resourceUrl, rapport, { observe: 'response' });
  }

  update(rapport: IRapport): Observable<EntityResponseType> {
    return this.http.put<IRapport>(`${this.resourceUrl}/${getRapportIdentifier(rapport) as number}`, rapport, { observe: 'response' });
  }

  partialUpdate(rapport: IRapport): Observable<EntityResponseType> {
    return this.http.patch<IRapport>(`${this.resourceUrl}/${getRapportIdentifier(rapport) as number}`, rapport, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRapport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRapport[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRapportToCollectionIfMissing(rapportCollection: IRapport[], ...rapportsToCheck: (IRapport | null | undefined)[]): IRapport[] {
    const rapports: IRapport[] = rapportsToCheck.filter(isPresent);
    if (rapports.length > 0) {
      const rapportCollectionIdentifiers = rapportCollection.map(rapportItem => getRapportIdentifier(rapportItem)!);
      const rapportsToAdd = rapports.filter(rapportItem => {
        const rapportIdentifier = getRapportIdentifier(rapportItem);
        if (rapportIdentifier == null || rapportCollectionIdentifiers.includes(rapportIdentifier)) {
          return false;
        }
        rapportCollectionIdentifiers.push(rapportIdentifier);
        return true;
      });
      return [...rapportsToAdd, ...rapportCollection];
    }
    return rapportCollection;
  }
}
