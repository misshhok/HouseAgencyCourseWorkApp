import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ComercialEventsOfResidentalEstateComponent } from '../list/comercial-events-of-residental-estate.component';
import { ComercialEventsOfResidentalEstateDetailComponent } from '../detail/comercial-events-of-residental-estate-detail.component';
import { ComercialEventsOfResidentalEstateUpdateComponent } from '../update/comercial-events-of-residental-estate-update.component';
import { ComercialEventsOfResidentalEstateRoutingResolveService } from './comercial-events-of-residental-estate-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const comercialEventsOfResidentalEstateRoute: Routes = [
  {
    path: '',
    component: ComercialEventsOfResidentalEstateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ComercialEventsOfResidentalEstateDetailComponent,
    resolve: {
      comercialEventsOfResidentalEstate: ComercialEventsOfResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ComercialEventsOfResidentalEstateUpdateComponent,
    resolve: {
      comercialEventsOfResidentalEstate: ComercialEventsOfResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ComercialEventsOfResidentalEstateUpdateComponent,
    resolve: {
      comercialEventsOfResidentalEstate: ComercialEventsOfResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(comercialEventsOfResidentalEstateRoute)],
  exports: [RouterModule],
})
export class ComercialEventsOfResidentalEstateRoutingModule {}
