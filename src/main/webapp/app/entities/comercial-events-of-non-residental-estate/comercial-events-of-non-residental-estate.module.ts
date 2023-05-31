import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ComercialEventsOfNonResidentalEstateComponent } from './list/comercial-events-of-non-residental-estate.component';
import { ComercialEventsOfNonResidentalEstateDetailComponent } from './detail/comercial-events-of-non-residental-estate-detail.component';
import { ComercialEventsOfNonResidentalEstateUpdateComponent } from './update/comercial-events-of-non-residental-estate-update.component';
import { ComercialEventsOfNonResidentalEstateDeleteDialogComponent } from './delete/comercial-events-of-non-residental-estate-delete-dialog.component';
import { ComercialEventsOfNonResidentalEstateRoutingModule } from './route/comercial-events-of-non-residental-estate-routing.module';

@NgModule({
  imports: [SharedModule, ComercialEventsOfNonResidentalEstateRoutingModule],
  declarations: [
    ComercialEventsOfNonResidentalEstateComponent,
    ComercialEventsOfNonResidentalEstateDetailComponent,
    ComercialEventsOfNonResidentalEstateUpdateComponent,
    ComercialEventsOfNonResidentalEstateDeleteDialogComponent,
  ],
})
export class ComercialEventsOfNonResidentalEstateModule {}
