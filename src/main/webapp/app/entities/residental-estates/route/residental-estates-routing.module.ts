import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResidentalEstatesComponent } from '../list/residental-estates.component';
import { ResidentalEstatesDetailComponent } from '../detail/residental-estates-detail.component';
import { ResidentalEstatesUpdateComponent } from '../update/residental-estates-update.component';
import { ResidentalEstatesRoutingResolveService } from './residental-estates-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const residentalEstatesRoute: Routes = [
  {
    path: '',
    component: ResidentalEstatesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResidentalEstatesDetailComponent,
    resolve: {
      residentalEstates: ResidentalEstatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResidentalEstatesUpdateComponent,
    resolve: {
      residentalEstates: ResidentalEstatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResidentalEstatesUpdateComponent,
    resolve: {
      residentalEstates: ResidentalEstatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(residentalEstatesRoute)],
  exports: [RouterModule],
})
export class ResidentalEstatesRoutingModule {}
