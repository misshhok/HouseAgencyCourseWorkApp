import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResidentalEstates } from '../residental-estates.model';

@Component({
  selector: 'jhi-residental-estates-detail',
  templateUrl: './residental-estates-detail.component.html',
})
export class ResidentalEstatesDetailComponent implements OnInit {
  residentalEstates: IResidentalEstates | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ residentalEstates }) => {
      this.residentalEstates = residentalEstates;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
