import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypesOfResidentalEstate } from '../types-of-residental-estate.model';
import { TypesOfResidentalEstateService } from '../service/types-of-residental-estate.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './types-of-residental-estate-delete-dialog.component.html',
})
export class TypesOfResidentalEstateDeleteDialogComponent {
  typesOfResidentalEstate?: ITypesOfResidentalEstate;

  constructor(protected typesOfResidentalEstateService: TypesOfResidentalEstateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typesOfResidentalEstateService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
