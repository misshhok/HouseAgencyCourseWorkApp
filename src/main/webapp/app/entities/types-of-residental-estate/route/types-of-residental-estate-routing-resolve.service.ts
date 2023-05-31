import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypesOfResidentalEstate } from '../types-of-residental-estate.model';
import { TypesOfResidentalEstateService } from '../service/types-of-residental-estate.service';

@Injectable({ providedIn: 'root' })
export class TypesOfResidentalEstateRoutingResolveService implements Resolve<ITypesOfResidentalEstate | null> {
  constructor(protected service: TypesOfResidentalEstateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypesOfResidentalEstate | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((typesOfResidentalEstate: HttpResponse<ITypesOfResidentalEstate>) => {
          if (typesOfResidentalEstate.body) {
            return of(typesOfResidentalEstate.body);
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
