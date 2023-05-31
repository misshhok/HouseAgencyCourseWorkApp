import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStatusesOfResidentalEstate } from '../statuses-of-residental-estate.model';
import { StatusesOfResidentalEstateService } from '../service/statuses-of-residental-estate.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './statuses-of-residental-estate-delete-dialog.component.html',
})
export class StatusesOfResidentalEstateDeleteDialogComponent {
  statusesOfResidentalEstate?: IStatusesOfResidentalEstate;

  constructor(protected statusesOfResidentalEstateService: StatusesOfResidentalEstateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statusesOfResidentalEstateService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
