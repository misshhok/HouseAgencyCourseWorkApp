import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypesOfCommercialEvents } from '../types-of-commercial-events.model';
import { TypesOfCommercialEventsService } from '../service/types-of-commercial-events.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './types-of-commercial-events-delete-dialog.component.html',
})
export class TypesOfCommercialEventsDeleteDialogComponent {
  typesOfCommercialEvents?: ITypesOfCommercialEvents;

  constructor(protected typesOfCommercialEventsService: TypesOfCommercialEventsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typesOfCommercialEventsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
