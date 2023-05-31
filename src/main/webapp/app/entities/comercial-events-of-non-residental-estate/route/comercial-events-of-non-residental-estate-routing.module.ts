import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ComercialEventsOfNonResidentalEstateComponent } from '../list/comercial-events-of-non-residental-estate.component';
import { ComercialEventsOfNonResidentalEstateDetailComponent } from '../detail/comercial-events-of-non-residental-estate-detail.component';
import { ComercialEventsOfNonResidentalEstateUpdateComponent } from '../update/comercial-events-of-non-residental-estate-update.component';
import { ComercialEventsOfNonResidentalEstateRoutingResolveService } from './comercial-events-of-non-residental-estate-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const comercialEventsOfNonResidentalEstateRoute: Routes = [
  {
    path: '',
    component: ComercialEventsOfNonResidentalEstateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ComercialEventsOfNonResidentalEstateDetailComponent,
    resolve: {
      comercialEventsOfNonResidentalEstate: ComercialEventsOfNonResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ComercialEventsOfNonResidentalEstateUpdateComponent,
    resolve: {
      comercialEventsOfNonResidentalEstate: ComercialEventsOfNonResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ComercialEventsOfNonResidentalEstateUpdateComponent,
    resolve: {
      comercialEventsOfNonResidentalEstate: ComercialEventsOfNonResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(comercialEventsOfNonResidentalEstateRoute)],
  exports: [RouterModule],
})
export class ComercialEventsOfNonResidentalEstateRoutingModule {}
