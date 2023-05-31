import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StatusesOfResidentalEstateComponent } from './list/statuses-of-residental-estate.component';
import { StatusesOfResidentalEstateDetailComponent } from './detail/statuses-of-residental-estate-detail.component';
import { StatusesOfResidentalEstateUpdateComponent } from './update/statuses-of-residental-estate-update.component';
import { StatusesOfResidentalEstateDeleteDialogComponent } from './delete/statuses-of-residental-estate-delete-dialog.component';
import { StatusesOfResidentalEstateRoutingModule } from './route/statuses-of-residental-estate-routing.module';

@NgModule({
  imports: [SharedModule, StatusesOfResidentalEstateRoutingModule],
  declarations: [
    StatusesOfResidentalEstateComponent,
    StatusesOfResidentalEstateDetailComponent,
    StatusesOfResidentalEstateUpdateComponent,
    StatusesOfResidentalEstateDeleteDialogComponent,
  ],
})
export class StatusesOfResidentalEstateModule {}
