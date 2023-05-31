import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStreetsOfCities } from '../streets-of-cities.model';
import { StreetsOfCitiesService } from '../service/streets-of-cities.service';

@Injectable({ providedIn: 'root' })
export class StreetsOfCitiesRoutingResolveService implements Resolve<IStreetsOfCities | null> {
  constructor(protected service: StreetsOfCitiesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStreetsOfCities | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((streetsOfCities: HttpResponse<IStreetsOfCities>) => {
          if (streetsOfCities.body) {
            return of(streetsOfCities.body);
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
