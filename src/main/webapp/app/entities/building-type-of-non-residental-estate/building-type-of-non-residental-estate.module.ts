import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BuildingTypeOfNonResidentalEstateComponent } from './list/building-type-of-non-residental-estate.component';
import { BuildingTypeOfNonResidentalEstateDetailComponent } from './detail/building-type-of-non-residental-estate-detail.component';
import { BuildingTypeOfNonResidentalEstateUpdateComponent } from './update/building-type-of-non-residental-estate-update.component';
import { BuildingTypeOfNonResidentalEstateDeleteDialogComponent } from './delete/building-type-of-non-residental-estate-delete-dialog.component';
import { BuildingTypeOfNonResidentalEstateRoutingModule } from './route/building-type-of-non-residental-estate-routing.module';

@NgModule({
  imports: [SharedModule, BuildingTypeOfNonResidentalEstateRoutingModule],
  declarations: [
    BuildingTypeOfNonResidentalEstateComponent,
    BuildingTypeOfNonResidentalEstateDetailComponent,
    BuildingTypeOfNonResidentalEstateUpdateComponent,
    BuildingTypeOfNonResidentalEstateDeleteDialogComponent,
  ],
})
export class BuildingTypeOfNonResidentalEstateModule {}
