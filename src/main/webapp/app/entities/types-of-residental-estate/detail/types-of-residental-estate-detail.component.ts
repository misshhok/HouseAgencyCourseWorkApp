import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypesOfResidentalEstate } from '../types-of-residental-estate.model';

@Component({
  selector: 'jhi-types-of-residental-estate-detail',
  templateUrl: './types-of-residental-estate-detail.component.html',
})
export class TypesOfResidentalEstateDetailComponent implements OnInit {
  typesOfResidentalEstate: ITypesOfResidentalEstate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typesOfResidentalEstate }) => {
      this.typesOfResidentalEstate = typesOfResidentalEstate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
