import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TypesOfResidentalEstateComponent } from '../list/types-of-residental-estate.component';
import { TypesOfResidentalEstateDetailComponent } from '../detail/types-of-residental-estate-detail.component';
import { TypesOfResidentalEstateUpdateComponent } from '../update/types-of-residental-estate-update.component';
import { TypesOfResidentalEstateRoutingResolveService } from './types-of-residental-estate-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const typesOfResidentalEstateRoute: Routes = [
  {
    path: '',
    component: TypesOfResidentalEstateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypesOfResidentalEstateDetailComponent,
    resolve: {
      typesOfResidentalEstate: TypesOfResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypesOfResidentalEstateUpdateComponent,
    resolve: {
      typesOfResidentalEstate: TypesOfResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypesOfResidentalEstateUpdateComponent,
    resolve: {
      typesOfResidentalEstate: TypesOfResidentalEstateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(typesOfResidentalEstateRoute)],
  exports: [RouterModule],
})
export class TypesOfResidentalEstateRoutingModule {}
