import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResidentalEstates } from '../residental-estates.model';
import { ResidentalEstatesService } from '../service/residental-estates.service';

@Injectable({ providedIn: 'root' })
export class ResidentalEstatesRoutingResolveService implements Resolve<IResidentalEstates | null> {
  constructor(protected service: ResidentalEstatesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResidentalEstates | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((residentalEstates: HttpResponse<IResidentalEstates>) => {
          if (residentalEstates.body) {
            return of(residentalEstates.body);
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
