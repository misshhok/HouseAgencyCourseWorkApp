import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypesOfResidentalEstate, NewTypesOfResidentalEstate } from '../types-of-residental-estate.model';

export type PartialUpdateTypesOfResidentalEstate = Partial<ITypesOfResidentalEstate> & Pick<ITypesOfResidentalEstate, 'id'>;

export type EntityResponseType = HttpResponse<ITypesOfResidentalEstate>;
export type EntityArrayResponseType = HttpResponse<ITypesOfResidentalEstate[]>;

@Injectable({ providedIn: 'root' })
export class TypesOfResidentalEstateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/types-of-residental-estates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(typesOfResidentalEstate: NewTypesOfResidentalEstate): Observable<EntityResponseType> {
    return this.http.post<ITypesOfResidentalEstate>(this.resourceUrl, typesOfResidentalEstate, { observe: 'response' });
  }

  update(typesOfResidentalEstate: ITypesOfResidentalEstate): Observable<EntityResponseType> {
    return this.http.put<ITypesOfResidentalEstate>(
      `${this.resourceUrl}/${this.getTypesOfResidentalEstateIdentifier(typesOfResidentalEstate)}`,
      typesOfResidentalEstate,
      { observe: 'response' }
    );
  }

  partialUpdate(typesOfResidentalEstate: PartialUpdateTypesOfResidentalEstate): Observable<EntityResponseType> {
    return this.http.patch<ITypesOfResidentalEstate>(
      `${this.resourceUrl}/${this.getTypesOfResidentalEstateIdentifier(typesOfResidentalEstate)}`,
      typesOfResidentalEstate,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypesOfResidentalEstate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypesOfResidentalEstate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTypesOfResidentalEstateIdentifier(typesOfResidentalEstate: Pick<ITypesOfResidentalEstate, 'id'>): number {
    return typesOfResidentalEstate.id;
  }

  compareTypesOfResidentalEstate(
    o1: Pick<ITypesOfResidentalEstate, 'id'> | null,
    o2: Pick<ITypesOfResidentalEstate, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getTypesOfResidentalEstateIdentifier(o1) === this.getTypesOfResidentalEstateIdentifier(o2) : o1 === o2;
  }

  addTypesOfResidentalEstateToCollectionIfMissing<Type extends Pick<ITypesOfResidentalEstate, 'id'>>(
    typesOfResidentalEstateCollection: Type[],
    ...typesOfResidentalEstatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const typesOfResidentalEstates: Type[] = typesOfResidentalEstatesToCheck.filter(isPresent);
    if (typesOfResidentalEstates.length > 0) {
      const typesOfResidentalEstateCollectionIdentifiers = typesOfResidentalEstateCollection.map(
        typesOfResidentalEstateItem => this.getTypesOfResidentalEstateIdentifier(typesOfResidentalEstateItem)!
      );
      const typesOfResidentalEstatesToAdd = typesOfResidentalEstates.filter(typesOfResidentalEstateItem => {
        const typesOfResidentalEstateIdentifier = this.getTypesOfResidentalEstateIdentifier(typesOfResidentalEstateItem);
        if (typesOfResidentalEstateCollectionIdentifiers.includes(typesOfResidentalEstateIdentifier)) {
          return false;
        }
        typesOfResidentalEstateCollectionIdentifiers.push(typesOfResidentalEstateIdentifier);
        return true;
      });
      return [...typesOfResidentalEstatesToAdd, ...typesOfResidentalEstateCollection];
    }
    return typesOfResidentalEstateCollection;
  }
}
