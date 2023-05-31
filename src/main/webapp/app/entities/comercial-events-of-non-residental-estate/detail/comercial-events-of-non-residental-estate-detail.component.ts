import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComercialEventsOfNonResidentalEstate } from '../comercial-events-of-non-residental-estate.model';

@Component({
  selector: 'jhi-comercial-events-of-non-residental-estate-detail',
  templateUrl: './comercial-events-of-non-residental-estate-detail.component.html',
})
export class ComercialEventsOfNonResidentalEstateDetailComponent implements OnInit {
  comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comercialEventsOfNonResidentalEstate }) => {
      this.comercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
