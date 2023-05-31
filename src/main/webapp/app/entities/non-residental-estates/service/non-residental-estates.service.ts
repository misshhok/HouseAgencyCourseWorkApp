import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INonResidentalEstates, NewNonResidentalEstates } from '../non-residental-estates.model';

export type PartialUpdateNonResidentalEstates = Partial<INonResidentalEstates> & Pick<INonResidentalEstates, 'id'>;

type RestOf<T extends INonResidentalEstates | NewNonResidentalEstates> = Omit<T, 'commissioningDate'> & {
  commissioningDate?: string | null;
};

export type RestNonResidentalEstates = RestOf<INonResidentalEstates>;

export type NewRestNonResidentalEstates = RestOf<NewNonResidentalEstates>;

export type PartialUpdateRestNonResidentalEstates = RestOf<PartialUpdateNonResidentalEstates>;

export type EntityResponseType = HttpResponse<INonResidentalEstates>;
export type EntityArrayResponseType = HttpResponse<INonResidentalEstates[]>;

@Injectable({ providedIn: 'root' })
export class NonResidentalEstatesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/non-residental-estates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nonResidentalEstates: NewNonResidentalEstates): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nonResidentalEstates);
    return this.http
      .post<RestNonResidentalEstates>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(nonResidentalEstates: INonResidentalEstates): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nonResidentalEstates);
    return this.http
      .put<RestNonResidentalEstates>(`${this.resourceUrl}/${this.getNonResidentalEstatesIdentifier(nonResidentalEstates)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(nonResidentalEstates: PartialUpdateNonResidentalEstates): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nonResidentalEstates);
    return this.http
      .patch<RestNonResidentalEstates>(`${this.resourceUrl}/${this.getNonResidentalEstatesIdentifier(nonResidentalEstates)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestNonResidentalEstates>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestNonResidentalEstates[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNonResidentalEstatesIdentifier(nonResidentalEstates: Pick<INonResidentalEstates, 'id'>): number {
    return nonResidentalEstates.id;
  }

  compareNonResidentalEstates(o1: Pick<INonResidentalEstates, 'id'> | null, o2: Pick<INonResidentalEstates, 'id'> | null): boolean {
    return o1 && o2 ? this.getNonResidentalEstatesIdentifier(o1) === this.getNonResidentalEstatesIdentifier(o2) : o1 === o2;
  }

  addNonResidentalEstatesToCollectionIfMissing<Type extends Pick<INonResidentalEstates, 'id'>>(
    nonResidentalEstatesCollection: Type[],
    ...nonResidentalEstatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const nonResidentalEstates: Type[] = nonResidentalEstatesToCheck.filter(isPresent);
    if (nonResidentalEstates.length > 0) {
      const nonResidentalEstatesCollectionIdentifiers = nonResidentalEstatesCollection.map(
        nonResidentalEstatesItem => this.getNonResidentalEstatesIdentifier(nonResidentalEstatesItem)!
      );
      const nonResidentalEstatesToAdd = nonResidentalEstates.filter(nonResidentalEstatesItem => {
        const nonResidentalEstatesIdentifier = this.getNonResidentalEstatesIdentifier(nonResidentalEstatesItem);
        if (nonResidentalEstatesCollectionIdentifiers.includes(nonResidentalEstatesIdentifier)) {
          return false;
        }
        nonResidentalEstatesCollectionIdentifiers.push(nonResidentalEstatesIdentifier);
        return true;
      });
      return [...nonResidentalEstatesToAdd, ...nonResidentalEstatesCollection];
    }
    return nonResidentalEstatesCollection;
  }

  protected convertDateFromClient<T extends INonResidentalEstates | NewNonResidentalEstates | PartialUpdateNonResidentalEstates>(
    nonResidentalEstates: T
  ): RestOf<T> {
    return {
      ...nonResidentalEstates,
      commissioningDate: nonResidentalEstates.commissioningDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restNonResidentalEstates: RestNonResidentalEstates): INonResidentalEstates {
    return {
      ...restNonResidentalEstates,
      commissioningDate: restNonResidentalEstates.commissioningDate ? dayjs(restNonResidentalEstates.commissioningDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestNonResidentalEstates>): HttpResponse<INonResidentalEstates> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestNonResidentalEstates[]>): HttpResponse<INonResidentalEstates[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
