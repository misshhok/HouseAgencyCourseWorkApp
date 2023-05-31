import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TypesOfCommercialEventsComponent } from './list/types-of-commercial-events.component';
import { TypesOfCommercialEventsDetailComponent } from './detail/types-of-commercial-events-detail.component';
import { TypesOfCommercialEventsUpdateComponent } from './update/types-of-commercial-events-update.component';
import { TypesOfCommercialEventsDeleteDialogComponent } from './delete/types-of-commercial-events-delete-dialog.component';
import { TypesOfCommercialEventsRoutingModule } from './route/types-of-commercial-events-routing.module';

@NgModule({
  imports: [SharedModule, TypesOfCommercialEventsRoutingModule],
  declarations: [
    TypesOfCommercialEventsComponent,
    TypesOfCommercialEventsDetailComponent,
    TypesOfCommercialEventsUpdateComponent,
    TypesOfCommercialEventsDeleteDialogComponent,
  ],
})
export class TypesOfCommercialEventsModule {}
