import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStreetsOfCities, NewStreetsOfCities } from '../streets-of-cities.model';

export type PartialUpdateStreetsOfCities = Partial<IStreetsOfCities> & Pick<IStreetsOfCities, 'id'>;

export type EntityResponseType = HttpResponse<IStreetsOfCities>;
export type EntityArrayResponseType = HttpResponse<IStreetsOfCities[]>;

@Injectable({ providedIn: 'root' })
export class StreetsOfCitiesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/streets-of-cities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(streetsOfCities: NewStreetsOfCities): Observable<EntityResponseType> {
    return this.http.post<IStreetsOfCities>(this.resourceUrl, streetsOfCities, { observe: 'response' });
  }

  update(streetsOfCities: IStreetsOfCities): Observable<EntityResponseType> {
    return this.http.put<IStreetsOfCities>(`${this.resourceUrl}/${this.getStreetsOfCitiesIdentifier(streetsOfCities)}`, streetsOfCities, {
      observe: 'response',
    });
  }

  partialUpdate(streetsOfCities: PartialUpdateStreetsOfCities): Observable<EntityResponseType> {
    return this.http.patch<IStreetsOfCities>(`${this.resourceUrl}/${this.getStreetsOfCitiesIdentifier(streetsOfCities)}`, streetsOfCities, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStreetsOfCities>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStreetsOfCities[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStreetsOfCitiesIdentifier(streetsOfCities: Pick<IStreetsOfCities, 'id'>): number {
    return streetsOfCities.id;
  }

  compareStreetsOfCities(o1: Pick<IStreetsOfCities, 'id'> | null, o2: Pick<IStreetsOfCities, 'id'> | null): boolean {
    return o1 && o2 ? this.getStreetsOfCitiesIdentifier(o1) === this.getStreetsOfCitiesIdentifier(o2) : o1 === o2;
  }

  addStreetsOfCitiesToCollectionIfMissing<Type extends Pick<IStreetsOfCities, 'id'>>(
    streetsOfCitiesCollection: Type[],
    ...streetsOfCitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const streetsOfCities: Type[] = streetsOfCitiesToCheck.filter(isPresent);
    if (streetsOfCities.length > 0) {
      const streetsOfCitiesCollectionIdentifiers = streetsOfCitiesCollection.map(
        streetsOfCitiesItem => this.getStreetsOfCitiesIdentifier(streetsOfCitiesItem)!
      );
      const streetsOfCitiesToAdd = streetsOfCities.filter(streetsOfCitiesItem => {
        const streetsOfCitiesIdentifier = this.getStreetsOfCitiesIdentifier(streetsOfCitiesItem);
        if (streetsOfCitiesCollectionIdentifiers.includes(streetsOfCitiesIdentifier)) {
          return false;
        }
        streetsOfCitiesCollectionIdentifiers.push(streetsOfCitiesIdentifier);
        return true;
      });
      return [...streetsOfCitiesToAdd, ...streetsOfCitiesCollection];
    }
    return streetsOfCitiesCollection;
  }
}
