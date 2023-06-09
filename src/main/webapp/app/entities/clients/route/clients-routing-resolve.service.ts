import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClients } from '../clients.model';
import { ClientsService } from '../service/clients.service';

@Injectable({ providedIn: 'root' })
export class ClientsRoutingResolveService implements Resolve<IClients | null> {
  constructor(protected service: ClientsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClients | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((clients: HttpResponse<IClients>) => {
          if (clients.body) {
            return of(clients.body);
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
