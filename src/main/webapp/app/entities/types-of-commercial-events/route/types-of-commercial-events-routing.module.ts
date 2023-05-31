import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TypesOfCommercialEventsComponent } from '../list/types-of-commercial-events.component';
import { TypesOfCommercialEventsDetailComponent } from '../detail/types-of-commercial-events-detail.component';
import { TypesOfCommercialEventsUpdateComponent } from '../update/types-of-commercial-events-update.component';
import { TypesOfCommercialEventsRoutingResolveService } from './types-of-commercial-events-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const typesOfCommercialEventsRoute: Routes = [
  {
    path: '',
    component: TypesOfCommercialEventsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypesOfCommercialEventsDetailComponent,
    resolve: {
      typesOfCommercialEvents: TypesOfCommercialEventsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypesOfCommercialEventsUpdateComponent,
    resolve: {
      typesOfCommercialEvents: TypesOfCommercialEventsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypesOfCommercialEventsUpdateComponent,
    resolve: {
      typesOfCommercialEvents: TypesOfCommercialEventsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(typesOfCommercialEventsRoute)],
  exports: [RouterModule],
})
export class TypesOfCommercialEventsRoutingModule {}
