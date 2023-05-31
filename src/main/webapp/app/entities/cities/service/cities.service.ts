import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICities, NewCities } from '../cities.model';

export type PartialUpdateCities = Partial<ICities> & Pick<ICities, 'id'>;

export type EntityResponseType = HttpResponse<ICities>;
export type EntityArrayResponseType = HttpResponse<ICities[]>;

@Injectable({ providedIn: 'root' })
export class CitiesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cities: NewCities): Observable<EntityResponseType> {
    return this.http.post<ICities>(this.resourceUrl, cities, { observe: 'response' });
  }

  update(cities: ICities): Observable<EntityResponseType> {
    return this.http.put<ICities>(`${this.resourceUrl}/${this.getCitiesIdentifier(cities)}`, cities, { observe: 'response' });
  }

  partialUpdate(cities: PartialUpdateCities): Observable<EntityResponseType> {
    return this.http.patch<ICities>(`${this.resourceUrl}/${this.getCitiesIdentifier(cities)}`, cities, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICities>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICities[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCitiesIdentifier(cities: Pick<ICities, 'id'>): number {
    return cities.id;
  }

  compareCities(o1: Pick<ICities, 'id'> | null, o2: Pick<ICities, 'id'> | null): boolean {
    return o1 && o2 ? this.getCitiesIdentifier(o1) === this.getCitiesIdentifier(o2) : o1 === o2;
  }

  addCitiesToCollectionIfMissing<Type extends Pick<ICities, 'id'>>(
    citiesCollection: Type[],
    ...citiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cities: Type[] = citiesToCheck.filter(isPresent);
    if (cities.length > 0) {
      const citiesCollectionIdentifiers = citiesCollection.map(citiesItem => this.getCitiesIdentifier(citiesItem)!);
      const citiesToAdd = cities.filter(citiesItem => {
        const citiesIdentifier = this.getCitiesIdentifier(citiesItem);
        if (citiesCollectionIdentifiers.includes(citiesIdentifier)) {
          return false;
        }
        citiesCollectionIdentifiers.push(citiesIdentifier);
        return true;
      });
      return [...citiesToAdd, ...citiesCollection];
    }
    return citiesCollection;
  }
}
