import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StreetsOfCitiesComponent } from '../list/streets-of-cities.component';
import { StreetsOfCitiesDetailComponent } from '../detail/streets-of-cities-detail.component';
import { StreetsOfCitiesUpdateComponent } from '../update/streets-of-cities-update.component';
import { StreetsOfCitiesRoutingResolveService } from './streets-of-cities-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const streetsOfCitiesRoute: Routes = [
  {
    path: '',
    component: StreetsOfCitiesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StreetsOfCitiesDetailComponent,
    resolve: {
      streetsOfCities: StreetsOfCitiesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StreetsOfCitiesUpdateComponent,
    resolve: {
      streetsOfCities: StreetsOfCitiesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StreetsOfCitiesUpdateComponent,
    resolve: {
      streetsOfCities: StreetsOfCitiesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(streetsOfCitiesRoute)],
  exports: [RouterModule],
})
export class StreetsOfCitiesRoutingModule {}
