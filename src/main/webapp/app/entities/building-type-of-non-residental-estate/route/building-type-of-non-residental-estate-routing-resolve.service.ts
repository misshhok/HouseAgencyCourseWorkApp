import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBuildingTypeOfNonResidentalEstate } from '../building-type-of-non-residental-estate.model';
import { BuildingTypeOfNonResidentalEstateService } from '../service/building-type-of-non-residental-estate.service';

@Injectable({ providedIn: 'root' })
export class BuildingTypeOfNonResidentalEstateRoutingResolveService implements Resolve<IBuildingTypeOfNonResidentalEstate | null> {
  constructor(protected service: BuildingTypeOfNonResidentalEstateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBuildingTypeOfNonResidentalEstate | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((buildingTypeOfNonResidentalEstate: HttpResponse<IBuildingTypeOfNonResidentalEstate>) => {
          if (buildingTypeOfNonResidentalEstate.body) {
            return of(buildingTypeOfNonResidentalEstate.body);
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
