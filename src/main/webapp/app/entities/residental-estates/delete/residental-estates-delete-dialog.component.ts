import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResidentalEstates } from '../residental-estates.model';
import { ResidentalEstatesService } from '../service/residental-estates.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './residental-estates-delete-dialog.component.html',
})
export class ResidentalEstatesDeleteDialogComponent {
  residentalEstates?: IResidentalEstates;

  constructor(protected residentalEstatesService: ResidentalEstatesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.residentalEstatesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
