import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BuildingTypeOfNonResidentalEstateComponent } from '../list/building-type-of-non-residental-estate.component';
import { BuildingTypeOfNonResidentalEstateDetailComponent } from '../detail/building-type-of-non-residental-estate-detail.component';
import { BuildingTypeOfNonResidentalEstateUpdateComponent } from '../update/building-type-of-non-residental-estate-update.component';
import { BuildingTypeOfNonResidentalEstateRoutingResolveService } from './building-type-of-non-residental-estate-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const buildingTypeOfNonResidentalEstateRoute: Routes = [
  {
    path: '',
    component: BuildingTypeOfNonResidentalEstateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BuildingTypeOfNonResidentalEstateDetailComponent,
    resolve: {
      buildingTypeOfNonResidentalEstate: BuildingTypeOfNonResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BuildingTypeOfNonResidentalEstateUpdateComponent,
    resolve: {
      buildingTypeOfNonResidentalEstate: BuildingTypeOfNonResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BuildingTypeOfNonResidentalEstateUpdateComponent,
    resolve: {
      buildingTypeOfNonResidentalEstate: BuildingTypeOfNonResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(buildingTypeOfNonResidentalEstateRoute)],
  exports: [RouterModule],
})
export class BuildingTypeOfNonResidentalEstateRoutingModule {}
