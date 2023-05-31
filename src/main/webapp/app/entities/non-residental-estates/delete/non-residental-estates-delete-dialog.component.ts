import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INonResidentalEstates } from '../non-residental-estates.model';
import { NonResidentalEstatesService } from '../service/non-residental-estates.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './non-residental-estates-delete-dialog.component.html',
})
export class NonResidentalEstatesDeleteDialogComponent {
  nonResidentalEstates?: INonResidentalEstates;

  constructor(protected nonResidentalEstatesService: NonResidentalEstatesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nonResidentalEstatesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
