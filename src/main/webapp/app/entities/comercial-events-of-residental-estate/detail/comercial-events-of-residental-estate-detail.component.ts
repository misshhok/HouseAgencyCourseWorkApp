import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComercialEventsOfResidentalEstate } from '../comercial-events-of-residental-estate.model';

@Component({
  selector: 'jhi-comercial-events-of-residental-estate-detail',
  templateUrl: './comercial-events-of-residental-estate-detail.component.html',
})
export class ComercialEventsOfResidentalEstateDetailComponent implements OnInit {
  comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comercialEventsOfResidentalEstate }) => {
      this.comercialEventsOfResidentalEstate = comercialEventsOfResidentalEstate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
