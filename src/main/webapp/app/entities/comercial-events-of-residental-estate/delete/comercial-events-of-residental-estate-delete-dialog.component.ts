import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IComercialEventsOfResidentalEstate } from '../comercial-events-of-residental-estate.model';
import { ComercialEventsOfResidentalEstateService } from '../service/comercial-events-of-residental-estate.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './comercial-events-of-residental-estate-delete-dialog.component.html',
})
export class ComercialEventsOfResidentalEstateDeleteDialogComponent {
  comercialEventsOfResidentalEstate?: IComercialEventsOfResidentalEstate;

  constructor(
    protected comercialEventsOfResidentalEstateService: ComercialEventsOfResidentalEstateService,
    protected activeModal: NgbActiveModal
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comercialEventsOfResidentalEstateService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
