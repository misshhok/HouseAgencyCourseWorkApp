import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStreetsOfCities } from '../streets-of-cities.model';
import { StreetsOfCitiesService } from '../service/streets-of-cities.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './streets-of-cities-delete-dialog.component.html',
})
export class StreetsOfCitiesDeleteDialogComponent {
  streetsOfCities?: IStreetsOfCities;

  constructor(protected streetsOfCitiesService: StreetsOfCitiesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.streetsOfCitiesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
