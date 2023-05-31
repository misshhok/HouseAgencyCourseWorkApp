import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypesOfCommercialEvents } from '../types-of-commercial-events.model';
import { TypesOfCommercialEventsService } from '../service/types-of-commercial-events.service';

@Injectable({ providedIn: 'root' })
export class TypesOfCommercialEventsRoutingResolveService implements Resolve<ITypesOfCommercialEvents | null> {
  constructor(protected service: TypesOfCommercialEventsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypesOfCommercialEvents | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((typesOfCommercialEvents: HttpResponse<ITypesOfCommercialEvents>) => {
          if (typesOfCommercialEvents.body) {
            return of(typesOfCommercialEvents.body);
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
