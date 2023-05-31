import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IComercialEventsOfResidentalEstate } from '../comercial-events-of-residental-estate.model';
import { ComercialEventsOfResidentalEstateService } from '../service/comercial-events-of-residental-estate.service';

@Injectable({ providedIn: 'root' })
export class ComercialEventsOfResidentalEstateRoutingResolveService implements Resolve<IComercialEventsOfResidentalEstate | null> {
  constructor(protected service: ComercialEventsOfResidentalEstateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComercialEventsOfResidentalEstate | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((comercialEventsOfResidentalEstate: HttpResponse<IComercialEventsOfResidentalEstate>) => {
          if (comercialEventsOfResidentalEstate.body) {
            return of(comercialEventsOfResidentalEstate.body);
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
