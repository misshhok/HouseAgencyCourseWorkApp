import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IComercialEventsOfNonResidentalEstate } from '../comercial-events-of-non-residental-estate.model';
import { ComercialEventsOfNonResidentalEstateService } from '../service/comercial-events-of-non-residental-estate.service';

@Injectable({ providedIn: 'root' })
export class ComercialEventsOfNonResidentalEstateRoutingResolveService implements Resolve<IComercialEventsOfNonResidentalEstate | null> {
  constructor(protected service: ComercialEventsOfNonResidentalEstateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComercialEventsOfNonResidentalEstate | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((comercialEventsOfNonResidentalEstate: HttpResponse<IComercialEventsOfNonResidentalEstate>) => {
          if (comercialEventsOfNonResidentalEstate.body) {
            return of(comercialEventsOfNonResidentalEstate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
