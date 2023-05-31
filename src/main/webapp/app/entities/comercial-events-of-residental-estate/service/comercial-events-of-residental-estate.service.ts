import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IComercialEventsOfResidentalEstate, NewComercialEventsOfResidentalEstate } from '../comercial-events-of-residental-estate.model';

export type PartialUpdateComercialEventsOfResidentalEstate = Partial<IComercialEventsOfResidentalEstate> &
  Pick<IComercialEventsOfResidentalEstate, 'id'>;

type RestOf<T extends IComercialEventsOfResidentalEstate | NewComercialEventsOfResidentalEstate> = Omit<T, 'dateOfEvent'> & {
  dateOfEvent?: string | null;
};

export type RestComercialEventsOfResidentalEstate = RestOf<IComercialEventsOfResidentalEstate>;

export type NewRestComercialEventsOfResidentalEstate = RestOf<NewComercialEventsOfResidentalEstate>;

export type PartialUpdateRestComercialEventsOfResidentalEstate = RestOf<PartialUpdateComercialEventsOfResidentalEstate>;

export type EntityResponseType = HttpResponse<IComercialEventsOfResidentalEstate>;
export type EntityArrayResponseType = HttpResponse<IComercialEventsOfResidentalEstate[]>;

@Injectable({ providedIn: 'root' })
export class ComercialEventsOfResidentalEstateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/comercial-events-of-residental-estates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(comercialEventsOfResidentalEstate: NewComercialEventsOfResidentalEstate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comercialEventsOfResidentalEstate);
    return this.http
      .post<RestComercialEventsOfResidentalEstate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comercialEventsOfResidentalEstate);
    return this.http
      .put<RestComercialEventsOfResidentalEstate>(
        `${this.resourceUrl}/${this.getComercialEventsOfResidentalEstateIdentifier(comercialEventsOfResidentalEstate)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(comercialEventsOfResidentalEstate: PartialUpdateComercialEventsOfResidentalEstate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comercialEventsOfResidentalEstate);
    return this.http
      .patch<RestComercialEventsOfResidentalEstate>(
        `${this.resourceUrl}/${this.getComercialEventsOfResidentalEstateIdentifier(comercialEventsOfResidentalEstate)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestComercialEventsOfResidentalEstate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestComercialEventsOfResidentalEstate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getComercialEventsOfResidentalEstateIdentifier(
    comercialEventsOfResidentalEstate: Pick<IComercialEventsOfResidentalEstate, 'id'>
  ): number {
    return comercialEventsOfResidentalEstate.id;
  }

  compareComercialEventsOfResidentalEstate(
    o1: Pick<IComercialEventsOfResidentalEstate, 'id'> | null,
    o2: Pick<IComercialEventsOfResidentalEstate, 'id'> | null
  ): boolean {
    return o1 && o2
      ? this.getComercialEventsOfResidentalEstateIdentifier(o1) === this.getComercialEventsOfResidentalEstateIdentifier(o2)
      : o1 === o2;
  }

  addComercialEventsOfResidentalEstateToCollectionIfMissing<Type extends Pick<IComercialEventsOfResidentalEstate, 'id'>>(
    comercialEventsOfResidentalEstateCollection: Type[],
    ...comercialEventsOfResidentalEstatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const comercialEventsOfResidentalEstates: Type[] = comercialEventsOfResidentalEstatesToCheck.filter(isPresent);
    if (comercialEventsOfResidentalEstates.length > 0) {
      const comercialEventsOfResidentalEstateCollectionIdentifiers = comercialEventsOfResidentalEstateCollection.map(
        comercialEventsOfResidentalEstateItem => this.getComercialEventsOfResidentalEstateIdentifier(comercialEventsOfResidentalEstateItem)!
      );
      const comercialEventsOfResidentalEstatesToAdd = comercialEventsOfResidentalEstates.filter(comercialEventsOfResidentalEstateItem => {
        const comercialEventsOfResidentalEstateIdentifier = this.getComercialEventsOfResidentalEstateIdentifier(
          comercialEventsOfResidentalEstateItem
        );
        if (comercialEventsOfResidentalEstateCollectionIdentifiers.includes(comercialEventsOfResidentalEstateIdentifier)) {
          return false;
        }
        comercialEventsOfResidentalEstateCollectionIdentifiers.push(comercialEventsOfResidentalEstateIdentifier);
        return true;
      });
      return [...comercialEventsOfResidentalEstatesToAdd, ...comercialEventsOfResidentalEstateCollection];
    }
    return comercialEventsOfResidentalEstateCollection;
  }

  protected convertDateFromClient<
    T extends IComercialEventsOfResidentalEstate | NewComercialEventsOfResidentalEstate | PartialUpdateComercialEventsOfResidentalEstate
  >(comercialEventsOfResidentalEstate: T): RestOf<T> {
    return {
      ...comercialEventsOfResidentalEstate,
      dateOfEvent: comercialEventsOfResidentalEstate.dateOfEvent?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(
    restComercialEventsOfResidentalEstate: RestComercialEventsOfResidentalEstate
  ): IComercialEventsOfResidentalEstate {
    return {
      ...restComercialEventsOfResidentalEstate,
      dateOfEvent: restComercialEventsOfResidentalEstate.dateOfEvent ? dayjs(restComercialEventsOfResidentalEstate.dateOfEvent) : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestComercialEventsOfResidentalEstate>
  ): HttpResponse<IComercialEventsOfResidentalEstate> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestComercialEventsOfResidentalEstate[]>
  ): HttpResponse<IComercialEventsOfResidentalEstate[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
