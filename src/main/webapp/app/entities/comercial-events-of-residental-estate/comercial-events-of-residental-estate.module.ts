import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ComercialEventsOfResidentalEstateComponent } from './list/comercial-events-of-residental-estate.component';
import { ComercialEventsOfResidentalEstateDetailComponent } from './detail/comercial-events-of-residental-estate-detail.component';
import { ComercialEventsOfResidentalEstateUpdateComponent } from './update/comercial-events-of-residental-estate-update.component';
import { ComercialEventsOfResidentalEstateDeleteDialogComponent } from './delete/comercial-events-of-residental-estate-delete-dialog.component';
import { ComercialEventsOfResidentalEstateRoutingModule } from './route/comercial-events-of-residental-estate-routing.module';

@NgModule({
  imports: [SharedModule, ComercialEventsOfResidentalEstateRoutingModule],
  declarations: [
    ComercialEventsOfResidentalEstateComponent,
    ComercialEventsOfResidentalEstateDetailComponent,
    ComercialEventsOfResidentalEstateUpdateComponent,
    ComercialEventsOfResidentalEstateDeleteDialogComponent,
  ],
})
export class ComercialEventsOfResidentalEstateModule {}
