import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBuildingTypeOfNonResidentalEstate } from '../building-type-of-non-residental-estate.model';
import { BuildingTypeOfNonResidentalEstateService } from '../service/building-type-of-non-residental-estate.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './building-type-of-non-residental-estate-delete-dialog.component.html',
})
export class BuildingTypeOfNonResidentalEstateDeleteDialogComponent {
  buildingTypeOfNonResidentalEstate?: IBuildingTypeOfNonResidentalEstate;

  constructor(
    protected buildingTypeOfNonResidentalEstateService: BuildingTypeOfNonResidentalEstateService,
    protected activeModal: NgbActiveModal
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.buildingTypeOfNonResidentalEstateService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
