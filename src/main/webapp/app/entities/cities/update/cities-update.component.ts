import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CitiesFormService, CitiesFormGroup } from './cities-form.service';
import { ICities } from '../cities.model';
import { CitiesService } from '../service/cities.service';

@Component({
  selector: 'jhi-cities-update',
  templateUrl: './cities-update.component.html',
})
export class CitiesUpdateComponent implements OnInit {
  isSaving = false;
  cities: ICities | null = null;

  editForm: CitiesFormGroup = this.citiesFormService.createCitiesFormGroup();

  constructor(
    protected citiesService: CitiesService,
    protected citiesFormService: CitiesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cities }) => {
      this.cities = cities;
      if (cities) {
        this.updateForm(cities);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cities = this.citiesFormService.getCities(this.editForm);
    if (cities.id !== null) {
      this.subscribeToSaveResponse(this.citiesService.update(cities));
    } else {
      this.subscribeToSaveResponse(this.citiesService.create(cities));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICities>>): void {
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

  protected updateForm(cities: ICities): void {
    this.cities = cities;
    this.citiesFormService.resetForm(this.editForm, cities);
  }
}
