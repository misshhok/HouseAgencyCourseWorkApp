import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import {
  ComercialEventsOfNonResidentalEstateFormService,
  ComercialEventsOfNonResidentalEstateFormGroup,
} from './comercial-events-of-non-residental-estate-form.service';
import { IComercialEventsOfNonResidentalEstate } from '../comercial-events-of-non-residental-estate.model';
import { ComercialEventsOfNonResidentalEstateService } from '../service/comercial-events-of-non-residental-estate.service';
import { ITypesOfCommercialEvents } from 'app/entities/types-of-commercial-events/types-of-commercial-events.model';
import { TypesOfCommercialEventsService } from 'app/entities/types-of-commercial-events/service/types-of-commercial-events.service';
import { INonResidentalEstates } from 'app/entities/non-residental-estates/non-residental-estates.model';
import { NonResidentalEstatesService } from 'app/entities/non-residental-estates/service/non-residental-estates.service';
import { IClients } from 'app/entities/clients/clients.model';
import { ClientsService } from 'app/entities/clients/service/clients.service';

@Component({
  selector: 'jhi-comercial-events-of-non-residental-estate-update',
  templateUrl: './comercial-events-of-non-residental-estate-update.component.html',
})
export class ComercialEventsOfNonResidentalEstateUpdateComponent implements OnInit {
  isSaving = false;
  comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate | null = null;

  typesOfCommercialEventsSharedCollection: ITypesOfCommercialEvents[] = [];
  nonResidentalEstatesSharedCollection: INonResidentalEstates[] = [];
  clientsSharedCollection: IClients[] = [];

  editForm: ComercialEventsOfNonResidentalEstateFormGroup =
    this.comercialEventsOfNonResidentalEstateFormService.createComercialEventsOfNonResidentalEstateFormGroup();

  constructor(
    protected comercialEventsOfNonResidentalEstateService: ComercialEventsOfNonResidentalEstateService,
    protected comercialEventsOfNonResidentalEstateFormService: ComercialEventsOfNonResidentalEstateFormService,
    protected typesOfCommercialEventsService: TypesOfCommercialEventsService,
    protected nonResidentalEstatesService: NonResidentalEstatesService,
    protected clientsService: ClientsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTypesOfCommercialEvents = (o1: ITypesOfCommercialEvents | null, o2: ITypesOfCommercialEvents | null): boolean =>
    this.typesOfCommercialEventsService.compareTypesOfCommercialEvents(o1, o2);

  compareNonResidentalEstates = (o1: INonResidentalEstates | null, o2: INonResidentalEstates | null): boolean =>
    this.nonResidentalEstatesService.compareNonResidentalEstates(o1, o2);

  compareClients = (o1: IClients | null, o2: IClients | null): boolean => this.clientsService.compareClients(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comercialEventsOfNonResidentalEstate }) => {
      this.comercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstate;
      if (comercialEventsOfNonResidentalEstate) {
        this.updateForm(comercialEventsOfNonResidentalEstate);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comercialEventsOfNonResidentalEstate =
      this.comercialEventsOfNonResidentalEstateFormService.getComercialEventsOfNonResidentalEstate(this.editForm);
    if (comercialEventsOfNonResidentalEstate.id !== null) {
      this.subscribeToSaveResponse(this.comercialEventsOfNonResidentalEstateService.update(comercialEventsOfNonResidentalEstate));
    } else {
      this.subscribeToSaveResponse(this.comercialEventsOfNonResidentalEstateService.create(comercialEventsOfNonResidentalEstate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComercialEventsOfNonResidentalEstate>>): void {
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

  protected updateForm(comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate): void {
    this.comercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstate;
    this.comercialEventsOfNonResidentalEstateFormService.resetForm(this.editForm, comercialEventsOfNonResidentalEstate);

    this.typesOfCommercialEventsSharedCollection =
      this.typesOfCommercialEventsService.addTypesOfCommercialEventsToCollectionIfMissing<ITypesOfCommercialEvents>(
        this.typesOfCommercialEventsSharedCollection,
        comercialEventsOfNonResidentalEstate.typeOfCommercialEventId
      );
    this.nonResidentalEstatesSharedCollection =
      this.nonResidentalEstatesService.addNonResidentalEstatesToCollectionIfMissing<INonResidentalEstates>(
        this.nonResidentalEstatesSharedCollection,
        comercialEventsOfNonResidentalEstate.nonResidentalEstateId
      );
    this.clientsSharedCollection = this.clientsService.addClientsToCollectionIfMissing<IClients>(
      this.clientsSharedCollection,
      comercialEventsOfNonResidentalEstate.clientId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.typesOfCommercialEventsService
      .query()
      .pipe(map((res: HttpResponse<ITypesOfCommercialEvents[]>) => res.body ?? []))
      .pipe(
        map((typesOfCommercialEvents: ITypesOfCommercialEvents[]) =>
          this.typesOfCommercialEventsService.addTypesOfCommercialEventsToCollectionIfMissing<ITypesOfCommercialEvents>(
            typesOfCommercialEvents,
            this.comercialEventsOfNonResidentalEstate?.typeOfCommercialEventId
          )
        )
      )
      .subscribe(
        (typesOfCommercialEvents: ITypesOfCommercialEvents[]) => (this.typesOfCommercialEventsSharedCollection = typesOfCommercialEvents)
      );

    this.nonResidentalEstatesService
      .query()
      .pipe(map((res: HttpResponse<INonResidentalEstates[]>) => res.body ?? []))
      .pipe(
        map((nonResidentalEstates: INonResidentalEstates[]) =>
          this.nonResidentalEstatesService.addNonResidentalEstatesToCollectionIfMissing<INonResidentalEstates>(
            nonResidentalEstates,
            this.comercialEventsOfNonResidentalEstate?.nonResidentalEstateId
          )
        )
      )
      .subscribe((nonResidentalEstates: INonResidentalEstates[]) => (this.nonResidentalEstatesSharedCollection = nonResidentalEstates));

    this.clientsService
      .query()
      .pipe(map((res: HttpResponse<IClients[]>) => res.body ?? []))
      .pipe(
        map((clients: IClients[]) =>
          this.clientsService.addClientsToCollectionIfMissing<IClients>(clients, this.comercialEventsOfNonResidentalEstate?.clientId)
        )
      )
      .subscribe((clients: IClients[]) => (this.clientsSharedCollection = clients));
  }
}
