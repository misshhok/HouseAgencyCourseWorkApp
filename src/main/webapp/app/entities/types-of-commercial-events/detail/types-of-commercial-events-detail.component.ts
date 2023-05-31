import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypesOfCommercialEvents } from '../types-of-commercial-events.model';

@Component({
  selector: 'jhi-types-of-commercial-events-detail',
  templateUrl: './types-of-commercial-events-detail.component.html',
})
export class TypesOfCommercialEventsDetailComponent implements OnInit {
  typesOfCommercialEvents: ITypesOfCommercialEvents | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typesOfCommercialEvents }) => {
      this.typesOfCommercialEvents = typesOfCommercialEvents;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
