import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPurposesOfNonResidentalEstate, NewPurposesOfNonResidentalEstate } from '../purposes-of-non-residental-estate.model';

export type PartialUpdatePurposesOfNonResidentalEstate = Partial<IPurposesOfNonResidentalEstate> &
  Pick<IPurposesOfNonResidentalEstate, 'id'>;

export type EntityResponseType = HttpResponse<IPurposesOfNonResidentalEstate>;
export type EntityArrayResponseType = HttpResponse<IPurposesOfNonResidentalEstate[]>;

@Injectable({ providedIn: 'root' })
export class PurposesOfNonResidentalEstateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/purposes-of-non-residental-estates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(purposesOfNonResidentalEstate: NewPurposesOfNonResidentalEstate): Observable<EntityResponseType> {
    return this.http.post<IPurposesOfNonResidentalEstate>(this.resourceUrl, purposesOfNonResidentalEstate, { observe: 'response' });
  }

  update(purposesOfNonResidentalEstate: IPurposesOfNonResidentalEstate): Observable<EntityResponseType> {
    return this.http.put<IPurposesOfNonResidentalEstate>(
      `${this.resourceUrl}/${this.getPurposesOfNonResidentalEstateIdentifier(purposesOfNonResidentalEstate)}`,
      purposesOfNonResidentalEstate,
      { observe: 'response' }
    );
  }

  partialUpdate(purposesOfNonResidentalEstate: PartialUpdatePurposesOfNonResidentalEstate): Observable<EntityResponseType> {
    return this.http.patch<IPurposesOfNonResidentalEstate>(
      `${this.resourceUrl}/${this.getPurposesOfNonResidentalEstateIdentifier(purposesOfNonResidentalEstate)}`,
      purposesOfNonResidentalEstate,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPurposesOfNonResidentalEstate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPurposesOfNonResidentalEstate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPurposesOfNonResidentalEstateIdentifier(purposesOfNonResidentalEstate: Pick<IPurposesOfNonResidentalEstate, 'id'>): number {
    return purposesOfNonResidentalEstate.id;
  }

  comparePurposesOfNonResidentalEstate(
    o1: Pick<IPurposesOfNonResidentalEstate, 'id'> | null,
    o2: Pick<IPurposesOfNonResidentalEstate, 'id'> | null
  ): boolean {
    return o1 && o2
      ? this.getPurposesOfNonResidentalEstateIdentifier(o1) === this.getPurposesOfNonResidentalEstateIdentifier(o2)
      : o1 === o2;
  }

  addPurposesOfNonResidentalEstateToCollectionIfMissing<Type extends Pick<IPurposesOfNonResidentalEstate, 'id'>>(
    purposesOfNonResidentalEstateCollection: Type[],
    ...purposesOfNonResidentalEstatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const purposesOfNonResidentalEstates: Type[] = purposesOfNonResidentalEstatesToCheck.filter(isPresent);
    if (purposesOfNonResidentalEstates.length > 0) {
      const purposesOfNonResidentalEstateCollectionIdentifiers = purposesOfNonResidentalEstateCollection.map(
        purposesOfNonResidentalEstateItem => this.getPurposesOfNonResidentalEstateIdentifier(purposesOfNonResidentalEstateItem)!
      );
      const purposesOfNonResidentalEstatesToAdd = purposesOfNonResidentalEstates.filter(purposesOfNonResidentalEstateItem => {
        const purposesOfNonResidentalEstateIdentifier = this.getPurposesOfNonResidentalEstateIdentifier(purposesOfNonResidentalEstateItem);
        if (purposesOfNonResidentalEstateCollectionIdentifiers.includes(purposesOfNonResidentalEstateIdentifier)) {
          return false;
        }
        purposesOfNonResidentalEstateCollectionIdentifiers.push(purposesOfNonResidentalEstateIdentifier);
        return true;
      });
      return [...purposesOfNonResidentalEstatesToAdd, ...purposesOfNonResidentalEstateCollection];
    }
    return purposesOfNonResidentalEstateCollection;
  }
}
