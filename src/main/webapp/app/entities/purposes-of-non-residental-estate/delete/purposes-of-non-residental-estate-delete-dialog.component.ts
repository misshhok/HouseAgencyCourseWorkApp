import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPurposesOfNonResidentalEstate } from '../purposes-of-non-residental-estate.model';
import { PurposesOfNonResidentalEstateService } from '../service/purposes-of-non-residental-estate.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './purposes-of-non-residental-estate-delete-dialog.component.html',
})
export class PurposesOfNonResidentalEstateDeleteDialogComponent {
  purposesOfNonResidentalEstate?: IPurposesOfNonResidentalEstate;

  constructor(
    protected purposesOfNonResidentalEstateService: PurposesOfNonResidentalEstateService,
    protected activeModal: NgbActiveModal
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.purposesOfNonResidentalEstateService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
