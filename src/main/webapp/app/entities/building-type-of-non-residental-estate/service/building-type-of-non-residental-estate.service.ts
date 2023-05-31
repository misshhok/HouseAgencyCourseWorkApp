import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBuildingTypeOfNonResidentalEstate, NewBuildingTypeOfNonResidentalEstate } from '../building-type-of-non-residental-estate.model';

export type PartialUpdateBuildingTypeOfNonResidentalEstate = Partial<IBuildingTypeOfNonResidentalEstate> &
  Pick<IBuildingTypeOfNonResidentalEstate, 'id'>;

export type EntityResponseType = HttpResponse<IBuildingTypeOfNonResidentalEstate>;
export type EntityArrayResponseType = HttpResponse<IBuildingTypeOfNonResidentalEstate[]>;

@Injectable({ providedIn: 'root' })
export class BuildingTypeOfNonResidentalEstateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/building-type-of-non-residental-estates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(buildingTypeOfNonResidentalEstate: NewBuildingTypeOfNonResidentalEstate): Observable<EntityResponseType> {
    return this.http.post<IBuildingTypeOfNonResidentalEstate>(this.resourceUrl, buildingTypeOfNonResidentalEstate, { observe: 'response' });
  }

  update(buildingTypeOfNonResidentalEstate: IBuildingTypeOfNonResidentalEstate): Observable<EntityResponseType> {
    return this.http.put<IBuildingTypeOfNonResidentalEstate>(
      `${this.resourceUrl}/${this.getBuildingTypeOfNonResidentalEstateIdentifier(buildingTypeOfNonResidentalEstate)}`,
      buildingTypeOfNonResidentalEstate,
      { observe: 'response' }
    );
  }

  partialUpdate(buildingTypeOfNonResidentalEstate: PartialUpdateBuildingTypeOfNonResidentalEstate): Observable<EntityResponseType> {
    return this.http.patch<IBuildingTypeOfNonResidentalEstate>(
      `${this.resourceUrl}/${this.getBuildingTypeOfNonResidentalEstateIdentifier(buildingTypeOfNonResidentalEstate)}`,
      buildingTypeOfNonResidentalEstate,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBuildingTypeOfNonResidentalEstate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBuildingTypeOfNonResidentalEstate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBuildingTypeOfNonResidentalEstateIdentifier(
    buildingTypeOfNonResidentalEstate: Pick<IBuildingTypeOfNonResidentalEstate, 'id'>
  ): number {
    return buildingTypeOfNonResidentalEstate.id;
  }

  compareBuildingTypeOfNonResidentalEstate(
    o1: Pick<IBuildingTypeOfNonResidentalEstate, 'id'> | null,
    o2: Pick<IBuildingTypeOfNonResidentalEstate, 'id'> | null
  ): boolean {
    return o1 && o2
      ? this.getBuildingTypeOfNonResidentalEstateIdentifier(o1) === this.getBuildingTypeOfNonResidentalEstateIdentifier(o2)
      : o1 === o2;
  }

  addBuildingTypeOfNonResidentalEstateToCollectionIfMissing<Type extends Pick<IBuildingTypeOfNonResidentalEstate, 'id'>>(
    buildingTypeOfNonResidentalEstateCollection: Type[],
    ...buildingTypeOfNonResidentalEstatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const buildingTypeOfNonResidentalEstates: Type[] = buildingTypeOfNonResidentalEstatesToCheck.filter(isPresent);
    if (buildingTypeOfNonResidentalEstates.length > 0) {
      const buildingTypeOfNonResidentalEstateCollectionIdentifiers = buildingTypeOfNonResidentalEstateCollection.map(
        buildingTypeOfNonResidentalEstateItem => this.getBuildingTypeOfNonResidentalEstateIdentifier(buildingTypeOfNonResidentalEstateItem)!
      );
      const buildingTypeOfNonResidentalEstatesToAdd = buildingTypeOfNonResidentalEstates.filter(buildingTypeOfNonResidentalEstateItem => {
        const buildingTypeOfNonResidentalEstateIdentifier = this.getBuildingTypeOfNonResidentalEstateIdentifier(
          buildingTypeOfNonResidentalEstateItem
        );
        if (buildingTypeOfNonResidentalEstateCollectionIdentifiers.includes(buildingTypeOfNonResidentalEstateIdentifier)) {
          return false;
        }
        buildingTypeOfNonResidentalEstateCollectionIdentifiers.push(buildingTypeOfNonResidentalEstateIdentifier);
        return true;
      });
      return [...buildingTypeOfNonResidentalEstatesToAdd, ...buildingTypeOfNonResidentalEstateCollection];
    }
    return buildingTypeOfNonResidentalEstateCollection;
  }
}
