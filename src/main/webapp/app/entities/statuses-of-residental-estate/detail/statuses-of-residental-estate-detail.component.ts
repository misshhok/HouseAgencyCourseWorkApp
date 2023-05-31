import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatusesOfResidentalEstate } from '../statuses-of-residental-estate.model';

@Component({
  selector: 'jhi-statuses-of-residental-estate-detail',
  templateUrl: './statuses-of-residental-estate-detail.component.html',
})
export class StatusesOfResidentalEstateDetailComponent implements OnInit {
  statusesOfResidentalEstate: IStatusesOfResidentalEstate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusesOfResidentalEstate }) => {
      this.statusesOfResidentalEstate = statusesOfResidentalEstate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
