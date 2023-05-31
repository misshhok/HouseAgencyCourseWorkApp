import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PurposesOfNonResidentalEstateComponent } from '../list/purposes-of-non-residental-estate.component';
import { PurposesOfNonResidentalEstateDetailComponent } from '../detail/purposes-of-non-residental-estate-detail.component';
import { PurposesOfNonResidentalEstateUpdateComponent } from '../update/purposes-of-non-residental-estate-update.component';
import { PurposesOfNonResidentalEstateRoutingResolveService } from './purposes-of-non-residental-estate-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const purposesOfNonResidentalEstateRoute: Routes = [
  {
    path: '',
    component: PurposesOfNonResidentalEstateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PurposesOfNonResidentalEstateDetailComponent,
    resolve: {
      purposesOfNonResidentalEstate: PurposesOfNonResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PurposesOfNonResidentalEstateUpdateComponent,
    resolve: {
      purposesOfNonResidentalEstate: PurposesOfNonResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PurposesOfNonResidentalEstateUpdateComponent,
    resolve: {
      purposesOfNonResidentalEstate: PurposesOfNonResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(purposesOfNonResidentalEstateRoute)],
  exports: [RouterModule],
})
export class PurposesOfNonResidentalEstateRoutingModule {}
