import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INonResidentalEstates } from '../non-residental-estates.model';
import { NonResidentalEstatesService } from '../service/non-residental-estates.service';

@Injectable({ providedIn: 'root' })
export class NonResidentalEstatesRoutingResolveService implements Resolve<INonResidentalEstates | null> {
  constructor(protected service: NonResidentalEstatesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INonResidentalEstates | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nonResidentalEstates: HttpResponse<INonResidentalEstates>) => {
          if (nonResidentalEstates.body) {
            return of(nonResidentalEstates.body);
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
