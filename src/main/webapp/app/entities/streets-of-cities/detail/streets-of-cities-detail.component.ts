import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStreetsOfCities } from '../streets-of-cities.model';

@Component({
  selector: 'jhi-streets-of-cities-detail',
  templateUrl: './streets-of-cities-detail.component.html',
})
export class StreetsOfCitiesDetailComponent implements OnInit {
  streetsOfCities: IStreetsOfCities | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ streetsOfCities }) => {
      this.streetsOfCities = streetsOfCities;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
