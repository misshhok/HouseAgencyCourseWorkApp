import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBuildingTypeOfNonResidentalEstate } from '../building-type-of-non-residental-estate.model';

@Component({
  selector: 'jhi-building-type-of-non-residental-estate-detail',
  templateUrl: './building-type-of-non-residental-estate-detail.component.html',
})
export class BuildingTypeOfNonResidentalEstateDetailComponent implements OnInit {
  buildingTypeOfNonResidentalEstate: IBuildingTypeOfNonResidentalEstate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ buildingTypeOfNonResidentalEstate }) => {
      this.buildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
