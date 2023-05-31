import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StreetsOfCitiesComponent } from './list/streets-of-cities.component';
import { StreetsOfCitiesDetailComponent } from './detail/streets-of-cities-detail.component';
import { StreetsOfCitiesUpdateComponent } from './update/streets-of-cities-update.component';
import { StreetsOfCitiesDeleteDialogComponent } from './delete/streets-of-cities-delete-dialog.component';
import { StreetsOfCitiesRoutingModule } from './route/streets-of-cities-routing.module';

@NgModule({
  imports: [SharedModule, StreetsOfCitiesRoutingModule],
  declarations: [
    StreetsOfCitiesComponent,
    StreetsOfCitiesDetailComponent,
    StreetsOfCitiesUpdateComponent,
    StreetsOfCitiesDeleteDialogComponent,
  ],
})
export class StreetsOfCitiesModule {}
