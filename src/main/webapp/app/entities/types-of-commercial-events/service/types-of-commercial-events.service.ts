import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypesOfCommercialEvents, NewTypesOfCommercialEvents } from '../types-of-commercial-events.model';

export type PartialUpdateTypesOfCommercialEvents = Partial<ITypesOfCommercialEvents> & Pick<ITypesOfCommercialEvents, 'id'>;

export type EntityResponseType = HttpResponse<ITypesOfCommercialEvents>;
export type EntityArrayResponseType = HttpResponse<ITypesOfCommercialEvents[]>;

@Injectable({ providedIn: 'root' })
export class TypesOfCommercialEventsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/types-of-commercial-events');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(typesOfCommercialEvents: NewTypesOfCommercialEvents): Observable<EntityResponseType> {
    return this.http.post<ITypesOfCommercialEvents>(this.resourceUrl, typesOfCommercialEvents, { observe: 'response' });
  }

  update(typesOfCommercialEvents: ITypesOfCommercialEvents): Observable<EntityResponseType> {
    return this.http.put<ITypesOfCommercialEvents>(
      `${this.resourceUrl}/${this.getTypesOfCommercialEventsIdentifier(typesOfCommercialEvents)}`,
      typesOfCommercialEvents,
      { observe: 'response' }
    );
  }

  partialUpdate(typesOfCommercialEvents: PartialUpdateTypesOfCommercialEvents): Observable<EntityResponseType> {
    return this.http.patch<ITypesOfCommercialEvents>(
      `${this.resourceUrl}/${this.getTypesOfCommercialEventsIdentifier(typesOfCommercialEvents)}`,
      typesOfCommercialEvents,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypesOfCommercialEvents>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypesOfCommercialEvents[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTypesOfCommercialEventsIdentifier(typesOfCommercialEvents: Pick<ITypesOfCommercialEvents, 'id'>): number {
    return typesOfCommercialEvents.id;
  }

  compareTypesOfCommercialEvents(
    o1: Pick<ITypesOfCommercialEvents, 'id'> | null,
    o2: Pick<ITypesOfCommercialEvents, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getTypesOfCommercialEventsIdentifier(o1) === this.getTypesOfCommercialEventsIdentifier(o2) : o1 === o2;
  }

  addTypesOfCommercialEventsToCollectionIfMissing<Type extends Pick<ITypesOfCommercialEvents, 'id'>>(
    typesOfCommercialEventsCollection: Type[],
    ...typesOfCommercialEventsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const typesOfCommercialEvents: Type[] = typesOfCommercialEventsToCheck.filter(isPresent);
    if (typesOfCommercialEvents.length > 0) {
      const typesOfCommercialEventsCollectionIdentifiers = typesOfCommercialEventsCollection.map(
        typesOfCommercialEventsItem => this.getTypesOfCommercialEventsIdentifier(typesOfCommercialEventsItem)!
      );
      const typesOfCommercialEventsToAdd = typesOfCommercialEvents.filter(typesOfCommercialEventsItem => {
        const typesOfCommercialEventsIdentifier = this.getTypesOfCommercialEventsIdentifier(typesOfCommercialEventsItem);
        if (typesOfCommercialEventsCollectionIdentifiers.includes(typesOfCommercialEventsIdentifier)) {
          return false;
        }
        typesOfCommercialEventsCollectionIdentifiers.push(typesOfCommercialEventsIdentifier);
        return true;
      });
      return [...typesOfCommercialEventsToAdd, ...typesOfCommercialEventsCollection];
    }
    return typesOfCommercialEventsCollection;
  }
}
