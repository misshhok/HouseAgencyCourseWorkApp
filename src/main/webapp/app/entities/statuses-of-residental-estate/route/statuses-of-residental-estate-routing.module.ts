import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StatusesOfResidentalEstateComponent } from '../list/statuses-of-residental-estate.component';
import { StatusesOfResidentalEstateDetailComponent } from '../detail/statuses-of-residental-estate-detail.component';
import { StatusesOfResidentalEstateUpdateComponent } from '../update/statuses-of-residental-estate-update.component';
import { StatusesOfResidentalEstateRoutingResolveService } from './statuses-of-residental-estate-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const statusesOfResidentalEstateRoute: Routes = [
  {
    path: '',
    component: StatusesOfResidentalEstateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatusesOfResidentalEstateDetailComponent,
    resolve: {
      statusesOfResidentalEstate: StatusesOfResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatusesOfResidentalEstateUpdateComponent,
    resolve: {
      statusesOfResidentalEstate: StatusesOfResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatusesOfResidentalEstateUpdateComponent,
    resolve: {
      statusesOfResidentalEstate: StatusesOfResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(statusesOfResidentalEstateRoute)],
  exports: [RouterModule],
})
export class StatusesOfResidentalEstateRoutingModule {}
