import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NonResidentalEstatesComponent } from './list/non-residental-estates.component';
import { NonResidentalEstatesDetailComponent } from './detail/non-residental-estates-detail.component';
import { NonResidentalEstatesUpdateComponent } from './update/non-residental-estates-update.component';
import { NonResidentalEstatesDeleteDialogComponent } from './delete/non-residental-estates-delete-dialog.component';
import { NonResidentalEstatesRoutingModule } from './route/non-residental-estates-routing.module';

@NgModule({
  imports: [SharedModule, NonResidentalEstatesRoutingModule],
  declarations: [
    NonResidentalEstatesComponent,
    NonResidentalEstatesDetailComponent,
    NonResidentalEstatesUpdateComponent,
    NonResidentalEstatesDeleteDialogComponent,
  ],
})
export class NonResidentalEstatesModule {}
