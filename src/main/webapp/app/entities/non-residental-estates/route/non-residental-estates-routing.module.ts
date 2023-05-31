import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NonResidentalEstatesComponent } from '../list/non-residental-estates.component';
import { NonResidentalEstatesDetailComponent } from '../detail/non-residental-estates-detail.component';
import { NonResidentalEstatesUpdateComponent } from '../update/non-residental-estates-update.component';
import { NonResidentalEstatesRoutingResolveService } from './non-residental-estates-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const nonResidentalEstatesRoute: Routes = [
  {
    path: '',
    component: NonResidentalEstatesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NonResidentalEstatesDetailComponent,
    resolve: {
      nonResidentalEstates: NonResidentalEstatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NonResidentalEstatesUpdateComponent,
    resolve: {
      nonResidentalEstates: NonResidentalEstatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NonResidentalEstatesUpdateComponent,
    resolve: {
      nonResidentalEstates: NonResidentalEstatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nonResidentalEstatesRoute)],
  exports: [RouterModule],
})
export class NonResidentalEstatesRoutingModule {}
