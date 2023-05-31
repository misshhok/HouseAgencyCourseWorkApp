import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStatusesOfResidentalEstate } from '../statuses-of-residental-estate.model';
import { StatusesOfResidentalEstateService } from '../service/statuses-of-residental-estate.service';

@Injectable({ providedIn: 'root' })
export class StatusesOfResidentalEstateRoutingResolveService implements Resolve<IStatusesOfResidentalEstate | null> {
  constructor(protected service: StatusesOfResidentalEstateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatusesOfResidentalEstate | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((statusesOfResidentalEstate: HttpResponse<IStatusesOfResidentalEstate>) => {
          if (statusesOfResidentalEstate.body) {
            return of(statusesOfResidentalEstate.body);
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
