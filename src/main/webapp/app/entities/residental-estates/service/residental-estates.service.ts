import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResidentalEstates, NewResidentalEstates } from '../residental-estates.model';

export type PartialUpdateResidentalEstates = Partial<IResidentalEstates> & Pick<IResidentalEstates, 'id'>;

type RestOf<T extends IResidentalEstates | NewResidentalEstates> = Omit<T, 'commissioningDate'> & {
  commissioningDate?: string | null;
};

export type RestResidentalEstates = RestOf<IResidentalEstates>;

export type NewRestResidentalEstates = RestOf<NewResidentalEstates>;

export type PartialUpdateRestResidentalEstates = RestOf<PartialUpdateResidentalEstates>;

export type EntityResponseType = HttpResponse<IResidentalEstates>;
export type EntityArrayResponseType = HttpResponse<IResidentalEstates[]>;

@Injectable({ providedIn: 'root' })
export class ResidentalEstatesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/residental-estates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(residentalEstates: NewResidentalEstates): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(residentalEstates);
    return this.http
      .post<RestResidentalEstates>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(residentalEstates: IResidentalEstates): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(residentalEstates);
    return this.http
      .put<RestResidentalEstates>(`${this.resourceUrl}/${this.getResidentalEstatesIdentifier(residentalEstates)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(residentalEstates: PartialUpdateResidentalEstates): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(residentalEstates);
    return this.http
      .patch<RestResidentalEstates>(`${this.resourceUrl}/${this.getResidentalEstatesIdentifier(residentalEstates)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestResidentalEstates>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestResidentalEstates[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResidentalEstatesIdentifier(residentalEstates: Pick<IResidentalEstates, 'id'>): number {
    return residentalEstates.id;
  }

  compareResidentalEstates(o1: Pick<IResidentalEstates, 'id'> | null, o2: Pick<IResidentalEstates, 'id'> | null): boolean {
    return o1 && o2 ? this.getResidentalEstatesIdentifier(o1) === this.getResidentalEstatesIdentifier(o2) : o1 === o2;
  }

  addResidentalEstatesToCollectionIfMissing<Type extends Pick<IResidentalEstates, 'id'>>(
    residentalEstatesCollection: Type[],
    ...residentalEstatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const residentalEstates: Type[] = residentalEstatesToCheck.filter(isPresent);
    if (residentalEstates.length > 0) {
      const residentalEstatesCollectionIdentifiers = residentalEstatesCollection.map(
        residentalEstatesItem => this.getResidentalEstatesIdentifier(residentalEstatesItem)!
      );
      const residentalEstatesToAdd = residentalEstates.filter(residentalEstatesItem => {
        const residentalEstatesIdentifier = this.getResidentalEstatesIdentifier(residentalEstatesItem);
        if (residentalEstatesCollectionIdentifiers.includes(residentalEstatesIdentifier)) {
          return false;
        }
        residentalEstatesCollectionIdentifiers.push(residentalEstatesIdentifier);
        return true;
      });
      return [...residentalEstatesToAdd, ...residentalEstatesCollection];
    }
    return residentalEstatesCollection;
  }

  protected convertDateFromClient<T extends IResidentalEstates | NewResidentalEstates | PartialUpdateResidentalEstates>(
    residentalEstates: T
  ): RestOf<T> {
    return {
      ...residentalEstates,
      commissioningDate: residentalEstates.commissioningDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restResidentalEstates: RestResidentalEstates): IResidentalEstates {
    return {
      ...restResidentalEstates,
      commissioningDate: restResidentalEstates.commissioningDate ? dayjs(restResidentalEstates.commissioningDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestResidentalEstates>): HttpResponse<IResidentalEstates> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestResidentalEstates[]>): HttpResponse<IResidentalEstates[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
