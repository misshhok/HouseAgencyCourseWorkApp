import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AddressesFormService, AddressesFormGroup } from './addresses-form.service';
import { IAddresses } from '../addresses.model';
import { AddressesService } from '../service/addresses.service';
import { IStreetsOfCities } from 'app/entities/streets-of-cities/streets-of-cities.model';
import { StreetsOfCitiesService } from 'app/entities/streets-of-cities/service/streets-of-cities.service';

@Component({
  selector: 'jhi-addresses-update',
  templateUrl: './addresses-update.component.html',
})
export class AddressesUpdateComponent implements OnInit {
  isSaving = false;
  addresses: IAddresses | null = null;

  streetsOfCitiesSharedCollection: IStreetsOfCities[] = [];

  editForm: AddressesFormGroup = this.addressesFormService.createAddressesFormGroup();

  constructor(
    protected addressesService: AddressesService,
    protected addressesFormService: AddressesFormService,
    protected streetsOfCitiesService: StreetsOfCitiesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareStreetsOfCities = (o1: IStreetsOfCities | null, o2: IStreetsOfCities | null): boolean =>
    this.streetsOfCitiesService.compareStreetsOfCities(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ addresses }) => {
      this.addresses = addresses;
      if (addresses) {
        this.updateForm(addresses);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const addresses = this.addressesFormService.getAddresses(this.editForm);
    if (addresses.id !== null) {
      this.subscribeToSaveResponse(this.addressesService.update(addresses));
    } else {
      this.subscribeToSaveResponse(this.addressesService.create(addresses));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddresses>>): void {
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

  protected updateForm(addresses: IAddresses): void {
    this.addresses = addresses;
    this.addressesFormService.resetForm(this.editForm, addresses);

    this.streetsOfCitiesSharedCollection = this.streetsOfCitiesService.addStreetsOfCitiesToCollectionIfMissing<IStreetsOfCities>(
      this.streetsOfCitiesSharedCollection,
      addresses.streetOfCityId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.streetsOfCitiesService
      .query()
      .pipe(map((res: HttpResponse<IStreetsOfCities[]>) => res.body ?? []))
      .pipe(
        map((streetsOfCities: IStreetsOfCities[]) =>
          this.streetsOfCitiesService.addStreetsOfCitiesToCollectionIfMissing<IStreetsOfCities>(
            streetsOfCities,
            this.addresses?.streetOfCityId
          )
        )
      )
      .subscribe((streetsOfCities: IStreetsOfCities[]) => (this.streetsOfCitiesSharedCollection = streetsOfCities));
  }
}
