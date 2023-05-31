import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PurposesOfNonResidentalEstateComponent } from './list/purposes-of-non-residental-estate.component';
import { PurposesOfNonResidentalEstateDetailComponent } from './detail/purposes-of-non-residental-estate-detail.component';
import { PurposesOfNonResidentalEstateUpdateComponent } from './update/purposes-of-non-residental-estate-update.component';
import { PurposesOfNonResidentalEstateDeleteDialogComponent } from './delete/purposes-of-non-residental-estate-delete-dialog.component';
import { PurposesOfNonResidentalEstateRoutingModule } from './route/purposes-of-non-residental-estate-routing.module';

@NgModule({
  imports: [SharedModule, PurposesOfNonResidentalEstateRoutingModule],
  declarations: [
    PurposesOfNonResidentalEstateComponent,
    PurposesOfNonResidentalEstateDetailComponent,
    PurposesOfNonResidentalEstateUpdateComponent,
    PurposesOfNonResidentalEstateDeleteDialogComponent,
  ],
})
export class PurposesOfNonResidentalEstateModule {}
