import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {
  IComercialEventsOfNonResidentalEstate,
  NewComercialEventsOfNonResidentalEstate,
} from '../comercial-events-of-non-residental-estate.model';

export type PartialUpdateComercialEventsOfNonResidentalEstate = Partial<IComercialEventsOfNonResidentalEstate> &
  Pick<IComercialEventsOfNonResidentalEstate, 'id'>;

type RestOf<T extends IComercialEventsOfNonResidentalEstate | NewComercialEventsOfNonResidentalEstate> = Omit<T, 'dateOfEvent'> & {
  dateOfEvent?: string | null;
};

export type RestComercialEventsOfNonResidentalEstate = RestOf<IComercialEventsOfNonResidentalEstate>;

export type NewRestComercialEventsOfNonResidentalEstate = RestOf<NewComercialEventsOfNonResidentalEstate>;

export type PartialUpdateRestComercialEventsOfNonResidentalEstate = RestOf<PartialUpdateComercialEventsOfNonResidentalEstate>;

export type EntityResponseType = HttpResponse<IComercialEventsOfNonResidentalEstate>;
export type EntityArrayResponseType = HttpResponse<IComercialEventsOfNonResidentalEstate[]>;

@Injectable({ providedIn: 'root' })
export class ComercialEventsOfNonResidentalEstateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/comercial-events-of-non-residental-estates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(comercialEventsOfNonResidentalEstate: NewComercialEventsOfNonResidentalEstate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comercialEventsOfNonResidentalEstate);
    return this.http
      .post<RestComercialEventsOfNonResidentalEstate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comercialEventsOfNonResidentalEstate);
    return this.http
      .put<RestComercialEventsOfNonResidentalEstate>(
        `${this.resourceUrl}/${this.getComercialEventsOfNonResidentalEstateIdentifier(comercialEventsOfNonResidentalEstate)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(comercialEventsOfNonResidentalEstate: PartialUpdateComercialEventsOfNonResidentalEstate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comercialEventsOfNonResidentalEstate);
    return this.http
      .patch<RestComercialEventsOfNonResidentalEstate>(
        `${this.resourceUrl}/${this.getComercialEventsOfNonResidentalEstateIdentifier(comercialEventsOfNonResidentalEstate)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestComercialEventsOfNonResidentalEstate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestComercialEventsOfNonResidentalEstate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getComercialEventsOfNonResidentalEstateIdentifier(
    comercialEventsOfNonResidentalEstate: Pick<IComercialEventsOfNonResidentalEstate, 'id'>
  ): number {
    return comercialEventsOfNonResidentalEstate.id;
  }

  compareComercialEventsOfNonResidentalEstate(
    o1: Pick<IComercialEventsOfNonResidentalEstate, 'id'> | null,
    o2: Pick<IComercialEventsOfNonResidentalEstate, 'id'> | null
  ): boolean {
    return o1 && o2
      ? this.getComercialEventsOfNonResidentalEstateIdentifier(o1) === this.getComercialEventsOfNonResidentalEstateIdentifier(o2)
      : o1 === o2;
  }

  addComercialEventsOfNonResidentalEstateToCollectionIfMissing<Type extends Pick<IComercialEventsOfNonResidentalEstate, 'id'>>(
    comercialEventsOfNonResidentalEstateCollection: Type[],
    ...comercialEventsOfNonResidentalEstatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const comercialEventsOfNonResidentalEstates: Type[] = comercialEventsOfNonResidentalEstatesToCheck.filter(isPresent);
    if (comercialEventsOfNonResidentalEstates.length > 0) {
      const comercialEventsOfNonResidentalEstateCollectionIdentifiers = comercialEventsOfNonResidentalEstateCollection.map(
        comercialEventsOfNonResidentalEstateItem =>
          this.getComercialEventsOfNonResidentalEstateIdentifier(comercialEventsOfNonResidentalEstateItem)!
      );
      const comercialEventsOfNonResidentalEstatesToAdd = comercialEventsOfNonResidentalEstates.filter(
        comercialEventsOfNonResidentalEstateItem => {
          const comercialEventsOfNonResidentalEstateIdentifier = this.getComercialEventsOfNonResidentalEstateIdentifier(
            comercialEventsOfNonResidentalEstateItem
          );
          if (comercialEventsOfNonResidentalEstateCollectionIdentifiers.includes(comercialEventsOfNonResidentalEstateIdentifier)) {
            return false;
          }
          comercialEventsOfNonResidentalEstateCollectionIdentifiers.push(comercialEventsOfNonResidentalEstateIdentifier);
          return true;
        }
      );
      return [...comercialEventsOfNonResidentalEstatesToAdd, ...comercialEventsOfNonResidentalEstateCollection];
    }
    return comercialEventsOfNonResidentalEstateCollection;
  }

  protected convertDateFromClient<
    T extends
      | IComercialEventsOfNonResidentalEstate
      | NewComercialEventsOfNonResidentalEstate
      | PartialUpdateComercialEventsOfNonResidentalEstate
  >(comercialEventsOfNonResidentalEstate: T): RestOf<T> {
    return {
      ...comercialEventsOfNonResidentalEstate,
      dateOfEvent: comercialEventsOfNonResidentalEstate.dateOfEvent?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(
    restComercialEventsOfNonResidentalEstate: RestComercialEventsOfNonResidentalEstate
  ): IComercialEventsOfNonResidentalEstate {
    return {
      ...restComercialEventsOfNonResidentalEstate,
      dateOfEvent: restComercialEventsOfNonResidentalEstate.dateOfEvent
        ? dayjs(restComercialEventsOfNonResidentalEstate.dateOfEvent)
        : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestComercialEventsOfNonResidentalEstate>
  ): HttpResponse<IComercialEventsOfNonResidentalEstate> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestComercialEventsOfNonResidentalEstate[]>
  ): HttpResponse<IComercialEventsOfNonResidentalEstate[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
