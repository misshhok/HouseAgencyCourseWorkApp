import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IComercialEventsOfNonResidentalEstate } from '../comercial-events-of-non-residental-estate.model';
import { ComercialEventsOfNonResidentalEstateService } from '../service/comercial-events-of-non-residental-estate.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './comercial-events-of-non-residental-estate-delete-dialog.component.html',
})
export class ComercialEventsOfNonResidentalEstateDeleteDialogComponent {
  comercialEventsOfNonResidentalEstate?: IComercialEventsOfNonResidentalEstate;

  constructor(
    protected comercialEventsOfNonResidentalEstateService: ComercialEventsOfNonResidentalEstateService,
    protected activeModal: NgbActiveModal
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comercialEventsOfNonResidentalEstateService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
