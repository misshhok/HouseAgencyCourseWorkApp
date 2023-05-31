import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TypesOfResidentalEstateComponent } from './list/types-of-residental-estate.component';
import { TypesOfResidentalEstateDetailComponent } from './detail/types-of-residental-estate-detail.component';
import { TypesOfResidentalEstateUpdateComponent } from './update/types-of-residental-estate-update.component';
import { TypesOfResidentalEstateDeleteDialogComponent } from './delete/types-of-residental-estate-delete-dialog.component';
import { TypesOfResidentalEstateRoutingModule } from './route/types-of-residental-estate-routing.module';

@NgModule({
  imports: [SharedModule, TypesOfResidentalEstateRoutingModule],
  declarations: [
    TypesOfResidentalEstateComponent,
    TypesOfResidentalEstateDetailComponent,
    TypesOfResidentalEstateUpdateComponent,
    TypesOfResidentalEstateDeleteDialogComponent,
  ],
})
export class TypesOfResidentalEstateModule {}
