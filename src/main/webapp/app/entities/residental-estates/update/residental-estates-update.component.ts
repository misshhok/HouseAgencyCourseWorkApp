import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ResidentalEstatesFormService, ResidentalEstatesFormGroup } from './residental-estates-form.service';
import { IResidentalEstates } from '../residental-estates.model';
import { ResidentalEstatesService } from '../service/residental-estates.service';
import { IAddresses } from 'app/entities/addresses/addresses.model';
import { AddressesService } from 'app/entities/addresses/service/addresses.service';
import { ITypesOfResidentalEstate } from 'app/entities/types-of-residental-estate/types-of-residental-estate.model';
import { TypesOfResidentalEstateService } from 'app/entities/types-of-residental-estate/service/types-of-residental-estate.service';
import { IStatusesOfResidentalEstate } from 'app/entities/statuses-of-residental-estate/statuses-of-residental-estate.model';
import { StatusesOfResidentalEstateService } from 'app/entities/statuses-of-residental-estate/service/statuses-of-residental-estate.service';

@Component({
  selector: 'jhi-residental-estates-update',
  templateUrl: './residental-estates-update.component.html',
})
export class ResidentalEstatesUpdateComponent implements OnInit {
  isSaving = false;
  residentalEstates: IResidentalEstates | null = null;

  addressesSharedCollection: IAddresses[] = [];
  typesOfResidentalEstatesSharedCollection: ITypesOfResidentalEstate[] = [];
  statusesOfResidentalEstatesSharedCollection: IStatusesOfResidentalEstate[] = [];

  editForm: ResidentalEstatesFormGroup = this.residentalEstatesFormService.createResidentalEstatesFormGroup();

  constructor(
    protected residentalEstatesService: ResidentalEstatesService,
    protected residentalEstatesFormService: ResidentalEstatesFormService,
    protected addressesService: AddressesService,
    protected typesOfResidentalEstateService: TypesOfResidentalEstateService,
    protected statusesOfResidentalEstateService: StatusesOfResidentalEstateService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAddresses = (o1: IAddresses | null, o2: IAddresses | null): boolean => this.addressesService.compareAddresses(o1, o2);

  compareTypesOfResidentalEstate = (o1: ITypesOfResidentalEstate | null, o2: ITypesOfResidentalEstate | null): boolean =>
    this.typesOfResidentalEstateService.compareTypesOfResidentalEstate(o1, o2);

  compareStatusesOfResidentalEstate = (o1: IStatusesOfResidentalEstate | null, o2: IStatusesOfResidentalEstate | null): boolean =>
    this.statusesOfResidentalEstateService.compareStatusesOfResidentalEstate(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ residentalEstates }) => {
      this.residentalEstates = residentalEstates;
      if (residentalEstates) {
        this.updateForm(residentalEstates);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const residentalEstates = this.residentalEstatesFormService.getResidentalEstates(this.editForm);
    if (residentalEstates.id !== null) {
      this.subscribeToSaveResponse(this.residentalEstatesService.update(residentalEstates));
    } else {
      this.subscribeToSaveResponse(this.residentalEstatesService.create(residentalEstates));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResidentalEstates>>): void {
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

  protected updateForm(residentalEstates: IResidentalEstates): void {
    this.residentalEstates = residentalEstates;
    this.residentalEstatesFormService.resetForm(this.editForm, residentalEstates);

    this.addressesSharedCollection = this.addressesService.addAddressesToCollectionIfMissing<IAddresses>(
      this.addressesSharedCollection,
      residentalEstates.addressId
    );
    this.typesOfResidentalEstatesSharedCollection =
      this.typesOfResidentalEstateService.addTypesOfResidentalEstateToCollectionIfMissing<ITypesOfResidentalEstate>(
        this.typesOfResidentalEstatesSharedCollection,
        residentalEstates.typeOfResidentalEstateId
      );
    this.statusesOfResidentalEstatesSharedCollection =
      this.statusesOfResidentalEstateService.addStatusesOfResidentalEstateToCollectionIfMissing<IStatusesOfResidentalEstate>(
        this.statusesOfResidentalEstatesSharedCollection,
        residentalEstates.statusOfResidentalEstateId
      );
  }

  protected loadRelationshipsOptions(): void {
    this.addressesService
      .query()
      .pipe(map((res: HttpResponse<IAddresses[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddresses[]) =>
          this.addressesService.addAddressesToCollectionIfMissing<IAddresses>(addresses, this.residentalEstates?.addressId)
        )
      )
      .subscribe((addresses: IAddresses[]) => (this.addressesSharedCollection = addresses));

    this.typesOfResidentalEstateService
      .query()
      .pipe(map((res: HttpResponse<ITypesOfResidentalEstate[]>) => res.body ?? []))
      .pipe(
        map((typesOfResidentalEstates: ITypesOfResidentalEstate[]) =>
          this.typesOfResidentalEstateService.addTypesOfResidentalEstateToCollectionIfMissing<ITypesOfResidentalEstate>(
            typesOfResidentalEstates,
            this.residentalEstates?.typeOfResidentalEstateId
          )
        )
      )
      .subscribe(
        (typesOfResidentalEstates: ITypesOfResidentalEstate[]) => (this.typesOfResidentalEstatesSharedCollection = typesOfResidentalEstates)
      );

    this.statusesOfResidentalEstateService
      .query()
      .pipe(map((res: HttpResponse<IStatusesOfResidentalEstate[]>) => res.body ?? []))
      .pipe(
        map((statusesOfResidentalEstates: IStatusesOfResidentalEstate[]) =>
          this.statusesOfResidentalEstateService.addStatusesOfResidentalEstateToCollectionIfMissing<IStatusesOfResidentalEstate>(
            statusesOfResidentalEstates,
            this.residentalEstates?.statusOfResidentalEstateId
          )
        )
      )
      .subscribe(
        (statusesOfResidentalEstates: IStatusesOfResidentalEstate[]) =>
          (this.statusesOfResidentalEstatesSharedCollection = statusesOfResidentalEstates)
      );
  }
}
