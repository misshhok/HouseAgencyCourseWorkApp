import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { StreetsOfCitiesFormService, StreetsOfCitiesFormGroup } from './streets-of-cities-form.service';
import { IStreetsOfCities } from '../streets-of-cities.model';
import { StreetsOfCitiesService } from '../service/streets-of-cities.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';

@Component({
  selector: 'jhi-streets-of-cities-update',
  templateUrl: './streets-of-cities-update.component.html',
})
export class StreetsOfCitiesUpdateComponent implements OnInit {
  isSaving = false;
  streetsOfCities: IStreetsOfCities | null = null;

  citiesSharedCollection: ICities[] = [];

  editForm: StreetsOfCitiesFormGroup = this.streetsOfCitiesFormService.createStreetsOfCitiesFormGroup();

  constructor(
    protected streetsOfCitiesService: StreetsOfCitiesService,
    protected streetsOfCitiesFormService: StreetsOfCitiesFormService,
    protected citiesService: CitiesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCities = (o1: ICities | null, o2: ICities | null): boolean => this.citiesService.compareCities(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ streetsOfCities }) => {
      this.streetsOfCities = streetsOfCities;
      if (streetsOfCities) {
        this.updateForm(streetsOfCities);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const streetsOfCities = this.streetsOfCitiesFormService.getStreetsOfCities(this.editForm);
    if (streetsOfCities.id !== null) {
      this.subscribeToSaveResponse(this.streetsOfCitiesService.update(streetsOfCities));
    } else {
      this.subscribeToSaveResponse(this.streetsOfCitiesService.create(streetsOfCities));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStreetsOfCities>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(streetsOfCities: IStreetsOfCities): void {
    this.streetsOfCities = streetsOfCities;
    this.streetsOfCitiesFormService.resetForm(this.editForm, streetsOfCities);

    this.citiesSharedCollection = this.citiesService.addCitiesToCollectionIfMissing<ICities>(
      this.citiesSharedCollection,
      streetsOfCities.cityId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.citiesService
      .query()
      .pipe(map((res: HttpResponse<ICities[]>) => res.body ?? []))
      .pipe(map((cities: ICities[]) => this.citiesService.addCitiesToCollectionIfMissing<ICities>(cities, this.streetsOfCities?.cityId)))
      .subscribe((cities: ICities[]) => (this.citiesSharedCollection = cities));
  }
}
