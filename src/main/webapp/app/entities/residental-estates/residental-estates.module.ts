import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResidentalEstatesComponent } from './list/residental-estates.component';
import { ResidentalEstatesDetailComponent } from './detail/residental-estates-detail.component';
import { ResidentalEstatesUpdateComponent } from './update/residental-estates-update.component';
import { ResidentalEstatesDeleteDialogComponent } from './delete/residental-estates-delete-dialog.component';
import { ResidentalEstatesRoutingModule } from './route/residental-estates-routing.module';

@NgModule({
  imports: [SharedModule, ResidentalEstatesRoutingModule],
  declarations: [
    ResidentalEstatesComponent,
    ResidentalEstatesDetailComponent,
    ResidentalEstatesUpdateComponent,
    ResidentalEstatesDeleteDialogComponent,
  ],
})
export class ResidentalEstatesModule {}
