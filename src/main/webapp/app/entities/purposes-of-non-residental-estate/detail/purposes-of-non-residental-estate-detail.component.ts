import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurposesOfNonResidentalEstate } from '../purposes-of-non-residental-estate.model';

@Component({
  selector: 'jhi-purposes-of-non-residental-estate-detail',
  templateUrl: './purposes-of-non-residental-estate-detail.component.html',
})
export class PurposesOfNonResidentalEstateDetailComponent implements OnInit {
  purposesOfNonResidentalEstate: IPurposesOfNonResidentalEstate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purposesOfNonResidentalEstate }) => {
      this.purposesOfNonResidentalEstate = purposesOfNonResidentalEstate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
