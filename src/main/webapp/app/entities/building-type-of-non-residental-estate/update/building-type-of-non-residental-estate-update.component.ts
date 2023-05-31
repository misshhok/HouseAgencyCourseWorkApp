import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import {
  BuildingTypeOfNonResidentalEstateFormService,
  BuildingTypeOfNonResidentalEstateFormGroup,
} from './building-type-of-non-residental-estate-form.service';
import { IBuildingTypeOfNonResidentalEstate } from '../building-type-of-non-residental-estate.model';
import { BuildingTypeOfNonResidentalEstateService } from '../service/building-type-of-non-residental-estate.service';

@Component({
  selector: 'jhi-building-type-of-non-residental-estate-update',
  templateUrl: './building-type-of-non-residental-estate-update.component.html',
})
export class BuildingTypeOfNonResidentalEstateUpdateComponent implements OnInit {
  isSaving = false;
  buildingTypeOfNonResidentalEstate: IBuildingTypeOfNonResidentalEstate | null = null;

  editForm: BuildingTypeOfNonResidentalEstateFormGroup =
    this.buildingTypeOfNonResidentalEstateFormService.createBuildingTypeOfNonResidentalEstateFormGroup();

  constructor(
    protected buildingTypeOfNonResidentalEstateService: BuildingTypeOfNonResidentalEstateService,
    protected buildingTypeOfNonResidentalEstateFormService: BuildingTypeOfNonResidentalEstateFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ buildingTypeOfNonResidentalEstate }) => {
      this.buildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstate;
      if (buildingTypeOfNonResidentalEstate) {
        this.updateForm(buildingTypeOfNonResidentalEstate);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const buildingTypeOfNonResidentalEstate = this.buildingTypeOfNonResidentalEstateFormService.getBuildingTypeOfNonResidentalEstate(
      this.editForm
    );
    if (buildingTypeOfNonResidentalEstate.id !== null) {
      this.subscribeToSaveResponse(this.buildingTypeOfNonResidentalEstateService.update(buildingTypeOfNonResidentalEstate));
    } else {
      this.subscribeToSaveResponse(this.buildingTypeOfNonResidentalEstateService.create(buildingTypeOfNonResidentalEstate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBuildingTypeOfNonResidentalEstate>>): void {
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

  protected updateForm(buildingTypeOfNonResidentalEstate: IBuildingTypeOfNonResidentalEstate): void {
    this.buildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstate;
    this.buildingTypeOfNonResidentalEstateFormService.resetForm(this.editForm, buildingTypeOfNonResidentalEstate);
  }
}
