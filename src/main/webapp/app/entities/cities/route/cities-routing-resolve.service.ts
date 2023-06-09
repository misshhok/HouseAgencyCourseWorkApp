import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICities } from '../cities.model';
import { CitiesService } from '../service/cities.service';

@Injectable({ providedIn: 'root' })
export class CitiesRoutingResolveService implements Resolve<ICities | null> {
  constructor(protected service: CitiesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICities | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cities: HttpResponse<ICities>) => {
          if (cities.body) {
            return of(cities.body);
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
