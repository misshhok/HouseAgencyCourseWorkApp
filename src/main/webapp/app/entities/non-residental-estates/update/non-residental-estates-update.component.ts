import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NonResidentalEstatesFormService, NonResidentalEstatesFormGroup } from './non-residental-estates-form.service';
import { INonResidentalEstates } from '../non-residental-estates.model';
import { NonResidentalEstatesService } from '../service/non-residental-estates.service';
import { IPurposesOfNonResidentalEstate } from 'app/entities/purposes-of-non-residental-estate/purposes-of-non-residental-estate.model';
import { PurposesOfNonResidentalEstateService } from 'app/entities/purposes-of-non-residental-estate/service/purposes-of-non-residental-estate.service';
import { IBuildingTypeOfNonResidentalEstate } from 'app/entities/building-type-of-non-residental-estate/building-type-of-non-residental-estate.model';
import { BuildingTypeOfNonResidentalEstateService } from 'app/entities/building-type-of-non-residental-estate/service/building-type-of-non-residental-estate.service';
import { IAddresses } from 'app/entities/addresses/addresses.model';
import { AddressesService } from 'app/entities/addresses/service/addresses.service';

@Component({
  selector: 'jhi-non-residental-estates-update',
  templateUrl: './non-residental-estates-update.component.html',
})
export class NonResidentalEstatesUpdateComponent implements OnInit {
  isSaving = false;
  nonResidentalEstates: INonResidentalEstates | null = null;

  purposesOfNonResidentalEstatesSharedCollection: IPurposesOfNonResidentalEstate[] = [];
  buildingTypeOfNonResidentalEstatesSharedCollection: IBuildingTypeOfNonResidentalEstate[] = [];
  addressesSharedCollection: IAddresses[] = [];

  editForm: NonResidentalEstatesFormGroup = this.nonResidentalEstatesFormService.createNonResidentalEstatesFormGroup();

  constructor(
    protected nonResidentalEstatesService: NonResidentalEstatesService,
    protected nonResidentalEstatesFormService: NonResidentalEstatesFormService,
    protected purposesOfNonResidentalEstateService: PurposesOfNonResidentalEstateService,
    protected buildingTypeOfNonResidentalEstateService: BuildingTypeOfNonResidentalEstateService,
    protected addressesService: AddressesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePurposesOfNonResidentalEstate = (o1: IPurposesOfNonResidentalEstate | null, o2: IPurposesOfNonResidentalEstate | null): boolean =>
    this.purposesOfNonResidentalEstateService.comparePurposesOfNonResidentalEstate(o1, o2);

  compareBuildingTypeOfNonResidentalEstate = (
    o1: IBuildingTypeOfNonResidentalEstate | null,
    o2: IBuildingTypeOfNonResidentalEstate | null
  ): boolean => this.buildingTypeOfNonResidentalEstateService.compareBuildingTypeOfNonResidentalEstate(o1, o2);

  compareAddresses = (o1: IAddresses | null, o2: IAddresses | null): boolean => this.addressesService.compareAddresses(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nonResidentalEstates }) => {
      this.nonResidentalEstates = nonResidentalEstates;
      if (nonResidentalEstates) {
        this.updateForm(nonResidentalEstates);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nonResidentalEstates = this.nonResidentalEstatesFormService.getNonResidentalEstates(this.editForm);
    if (nonResidentalEstates.id !== null) {
      this.subscribeToSaveResponse(this.nonResidentalEstatesService.update(nonResidentalEstates));
    } else {
      this.subscribeToSaveResponse(this.nonResidentalEstatesService.create(nonResidentalEstates));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INonResidentalEstates>>): void {
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

  protected updateForm(nonResidentalEstates: INonResidentalEstates): void {
    this.nonResidentalEstates = nonResidentalEstates;
    this.nonResidentalEstatesFormService.resetForm(this.editForm, nonResidentalEstates);

    this.purposesOfNonResidentalEstatesSharedCollection =
      this.purposesOfNonResidentalEstateService.addPurposesOfNonResidentalEstateToCollectionIfMissing<IPurposesOfNonResidentalEstate>(
        this.purposesOfNonResidentalEstatesSharedCollection,
        nonResidentalEstates.purposeOfNonResidentalEstateId
      );
    this.buildingTypeOfNonResidentalEstatesSharedCollection =
      this.buildingTypeOfNonResidentalEstateService.addBuildingTypeOfNonResidentalEstateToCollectionIfMissing<IBuildingTypeOfNonResidentalEstate>(
        this.buildingTypeOfNonResidentalEstatesSharedCollection,
        nonResidentalEstates.buildingTypeOfNonResidentalEstateId
      );
    this.addressesSharedCollection = this.addressesService.addAddressesToCollectionIfMissing<IAddresses>(
      this.addressesSharedCollection,
      nonResidentalEstates.addressId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.purposesOfNonResidentalEstateService
      .query()
      .pipe(map((res: HttpResponse<IPurposesOfNonResidentalEstate[]>) => res.body ?? []))
      .pipe(
        map((purposesOfNonResidentalEstates: IPurposesOfNonResidentalEstate[]) =>
          this.purposesOfNonResidentalEstateService.addPurposesOfNonResidentalEstateToCollectionIfMissing<IPurposesOfNonResidentalEstate>(
            purposesOfNonResidentalEstates,
            this.nonResidentalEstates?.purposeOfNonResidentalEstateId
          )
        )
      )
      .subscribe(
        (purposesOfNonResidentalEstates: IPurposesOfNonResidentalEstate[]) =>
          (this.purposesOfNonResidentalEstatesSharedCollection = purposesOfNonResidentalEstates)
      );

    this.buildingTypeOfNonResidentalEstateService
      .query()
      .pipe(map((res: HttpResponse<IBuildingTypeOfNonResidentalEstate[]>) => res.body ?? []))
      .pipe(
        map((buildingTypeOfNonResidentalEstates: IBuildingTypeOfNonResidentalEstate[]) =>
          this.buildingTypeOfNonResidentalEstateService.addBuildingTypeOfNonResidentalEstateToCollectionIfMissing<IBuildingTypeOfNonResidentalEstate>(
            buildingTypeOfNonResidentalEstates,
            this.nonResidentalEstates?.buildingTypeOfNonResidentalEstateId
          )
        )
      )
      .subscribe(
        (buildingTypeOfNonResidentalEstates: IBuildingTypeOfNonResidentalEstate[]) =>
          (this.buildingTypeOfNonResidentalEstatesSharedCollection = buildingTypeOfNonResidentalEstates)
      );

    this.addressesService
      .query()
      .pipe(map((res: HttpResponse<IAddresses[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddresses[]) =>
          this.addressesService.addAddressesToCollectionIfMissing<IAddresses>(addresses, this.nonResidentalEstates?.addressId)
        )
      )
      .subscribe((addresses: IAddresses[]) => (this.addressesSharedCollection = addresses));
  }
}
