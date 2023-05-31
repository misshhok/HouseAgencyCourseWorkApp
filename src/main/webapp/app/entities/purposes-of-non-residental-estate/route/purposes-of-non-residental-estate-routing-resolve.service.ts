import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPurposesOfNonResidentalEstate } from '../purposes-of-non-residental-estate.model';
import { PurposesOfNonResidentalEstateService } from '../service/purposes-of-non-residental-estate.service';

@Injectable({ providedIn: 'root' })
export class PurposesOfNonResidentalEstateRoutingResolveService implements Resolve<IPurposesOfNonResidentalEstate | null> {
  constructor(protected service: PurposesOfNonResidentalEstateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPurposesOfNonResidentalEstate | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((purposesOfNonResidentalEstate: HttpResponse<IPurposesOfNonResidentalEstate>) => {
          if (purposesOfNonResidentalEstate.body) {
            return of(purposesOfNonResidentalEstate.body);
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
