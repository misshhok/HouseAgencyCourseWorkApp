import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INonResidentalEstates } from '../non-residental-estates.model';

@Component({
  selector: 'jhi-non-residental-estates-detail',
  templateUrl: './non-residental-estates-detail.component.html',
})
export class NonResidentalEstatesDetailComponent implements OnInit {
  nonResidentalEstates: INonResidentalEstates | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nonResidentalEstates }) => {
      this.nonResidentalEstates = nonResidentalEstates;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
