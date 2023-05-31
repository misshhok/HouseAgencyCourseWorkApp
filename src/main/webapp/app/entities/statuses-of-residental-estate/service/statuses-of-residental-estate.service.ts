import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStatusesOfResidentalEstate, NewStatusesOfResidentalEstate } from '../statuses-of-residental-estate.model';

export type PartialUpdateStatusesOfResidentalEstate = Partial<IStatusesOfResidentalEstate> & Pick<IStatusesOfResidentalEstate, 'id'>;

export type EntityResponseType = HttpResponse<IStatusesOfResidentalEstate>;
export type EntityArrayResponseType = HttpResponse<IStatusesOfResidentalEstate[]>;

@Injectable({ providedIn: 'root' })
export class StatusesOfResidentalEstateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/statuses-of-residental-estates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(statusesOfResidentalEstate: NewStatusesOfResidentalEstate): Observable<EntityResponseType> {
    return this.http.post<IStatusesOfResidentalEstate>(this.resourceUrl, statusesOfResidentalEstate, { observe: 'response' });
  }

  update(statusesOfResidentalEstate: IStatusesOfResidentalEstate): Observable<EntityResponseType> {
    return this.http.put<IStatusesOfResidentalEstate>(
      `${this.resourceUrl}/${this.getStatusesOfResidentalEstateIdentifier(statusesOfResidentalEstate)}`,
      statusesOfResidentalEstate,
      { observe: 'response' }
    );
  }

  partialUpdate(statusesOfResidentalEstate: PartialUpdateStatusesOfResidentalEstate): Observable<EntityResponseType> {
    return this.http.patch<IStatusesOfResidentalEstate>(
      `${this.resourceUrl}/${this.getStatusesOfResidentalEstateIdentifier(statusesOfResidentalEstate)}`,
      statusesOfResidentalEstate,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatusesOfResidentalEstate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatusesOfResidentalEstate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStatusesOfResidentalEstateIdentifier(statusesOfResidentalEstate: Pick<IStatusesOfResidentalEstate, 'id'>): number {
    return statusesOfResidentalEstate.id;
  }

  compareStatusesOfResidentalEstate(
    o1: Pick<IStatusesOfResidentalEstate, 'id'> | null,
    o2: Pick<IStatusesOfResidentalEstate, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getStatusesOfResidentalEstateIdentifier(o1) === this.getStatusesOfResidentalEstateIdentifier(o2) : o1 === o2;
  }

  addStatusesOfResidentalEstateToCollectionIfMissing<Type extends Pick<IStatusesOfResidentalEstate, 'id'>>(
    statusesOfResidentalEstateCollection: Type[],
    ...statusesOfResidentalEstatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const statusesOfResidentalEstates: Type[] = statusesOfResidentalEstatesToCheck.filter(isPresent);
    if (statusesOfResidentalEstates.length > 0) {
      const statusesOfResidentalEstateCollectionIdentifiers = statusesOfResidentalEstateCollection.map(
        statusesOfResidentalEstateItem => this.getStatusesOfResidentalEstateIdentifier(statusesOfResidentalEstateItem)!
      );
      const statusesOfResidentalEstatesToAdd = statusesOfResidentalEstates.filter(statusesOfResidentalEstateItem => {
        const statusesOfResidentalEstateIdentifier = this.getStatusesOfResidentalEstateIdentifier(statusesOfResidentalEstateItem);
        if (statusesOfResidentalEstateCollectionIdentifiers.includes(statusesOfResidentalEstateIdentifier)) {
          return false;
        }
        statusesOfResidentalEstateCollectionIdentifiers.push(statusesOfResidentalEstateIdentifier);
        return true;
      });
      return [...statusesOfResidentalEstatesToAdd, ...statusesOfResidentalEstateCollection];
    }
    return statusesOfResidentalEstateCollection;
  }
}
