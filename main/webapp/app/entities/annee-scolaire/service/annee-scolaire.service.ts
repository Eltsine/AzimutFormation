import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnneeScolaire, getAnneeScolaireIdentifier } from '../annee-scolaire.model';

export type EntityResponseType = HttpResponse<IAnneeScolaire>;
export type EntityArrayResponseType = HttpResponse<IAnneeScolaire[]>;

@Injectable({ providedIn: 'root' })
export class AnneeScolaireService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/annee-scolaires');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(anneeScolaire: IAnneeScolaire): Observable<EntityResponseType> {
    return this.http.post<IAnneeScolaire>(this.resourceUrl, anneeScolaire, { observe: 'response' });
  }

  update(anneeScolaire: IAnneeScolaire): Observable<EntityResponseType> {
    return this.http.put<IAnneeScolaire>(`${this.resourceUrl}/${getAnneeScolaireIdentifier(anneeScolaire) as number}`, anneeScolaire, {
      observe: 'response',
    });
  }

  partialUpdate(anneeScolaire: IAnneeScolaire): Observable<EntityResponseType> {
    return this.http.patch<IAnneeScolaire>(`${this.resourceUrl}/${getAnneeScolaireIdentifier(anneeScolaire) as number}`, anneeScolaire, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnneeScolaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnneeScolaire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAnneeScolaireToCollectionIfMissing(
    anneeScolaireCollection: IAnneeScolaire[],
    ...anneeScolairesToCheck: (IAnneeScolaire | null | undefined)[]
  ): IAnneeScolaire[] {
    const anneeScolaires: IAnneeScolaire[] = anneeScolairesToCheck.filter(isPresent);
    if (anneeScolaires.length > 0) {
      const anneeScolaireCollectionIdentifiers = anneeScolaireCollection.map(
        anneeScolaireItem => getAnneeScolaireIdentifier(anneeScolaireItem)!
      );
      const anneeScolairesToAdd = anneeScolaires.filter(anneeScolaireItem => {
        const anneeScolaireIdentifier = getAnneeScolaireIdentifier(anneeScolaireItem);
        if (anneeScolaireIdentifier == null || anneeScolaireCollectionIdentifiers.includes(anneeScolaireIdentifier)) {
          return false;
        }
        anneeScolaireCollectionIdentifiers.push(anneeScolaireIdentifier);
        return true;
      });
      return [...anneeScolairesToAdd, ...anneeScolaireCollection];
    }
    return anneeScolaireCollection;
  }
}
