import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import {
  ComercialEventsOfResidentalEstateFormService,
  ComercialEventsOfResidentalEstateFormGroup,
} from './comercial-events-of-residental-estate-form.service';
import { IComercialEventsOfResidentalEstate } from '../comercial-events-of-residental-estate.model';
import { ComercialEventsOfResidentalEstateService } from '../service/comercial-events-of-residental-estate.service';
import { ITypesOfCommercialEvents } from 'app/entities/types-of-commercial-events/types-of-commercial-events.model';
import { TypesOfCommercialEventsService } from 'app/entities/types-of-commercial-events/service/types-of-commercial-events.service';
import { IClients } from 'app/entities/clients/clients.model';
import { ClientsService } from 'app/entities/clients/service/clients.service';
import { IResidentalEstates } from 'app/entities/residental-estates/residental-estates.model';
import { ResidentalEstatesService } from 'app/entities/residental-estates/service/residental-estates.service';

@Component({
  selector: 'jhi-comercial-events-of-residental-estate-update',
  templateUrl: './comercial-events-of-residental-estate-update.component.html',
})
export class ComercialEventsOfResidentalEstateUpdateComponent implements OnInit {
  isSaving = false;
  comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate | null = null;

  typesOfCommercialEventsSharedCollection: ITypesOfCommercialEvents[] = [];
  clientsSharedCollection: IClients[] = [];
  residentalEstatesSharedCollection: IResidentalEstates[] = [];

  editForm: ComercialEventsOfResidentalEstateFormGroup =
    this.comercialEventsOfResidentalEstateFormService.createComercialEventsOfResidentalEstateFormGroup();

  constructor(
    protected comercialEventsOfResidentalEstateService: ComercialEventsOfResidentalEstateService,
    protected comercialEventsOfResidentalEstateFormService: ComercialEventsOfResidentalEstateFormService,
    protected typesOfCommercialEventsService: TypesOfCommercialEventsService,
    protected clientsService: ClientsService,
    protected residentalEstatesService: ResidentalEstatesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTypesOfCommercialEvents = (o1: ITypesOfCommercialEvents | null, o2: ITypesOfCommercialEvents | null): boolean =>
    this.typesOfCommercialEventsService.compareTypesOfCommercialEvents(o1, o2);

  compareClients = (o1: IClients | null, o2: IClients | null): boolean => this.clientsService.compareClients(o1, o2);

  compareResidentalEstates = (o1: IResidentalEstates | null, o2: IResidentalEstates | null): boolean =>
    this.residentalEstatesService.compareResidentalEstates(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comercialEventsOfResidentalEstate }) => {
      this.comercialEventsOfResidentalEstate = comercialEventsOfResidentalEstate;
      if (comercialEventsOfResidentalEstate) {
        this.updateForm(comercialEventsOfResidentalEstate);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comercialEventsOfResidentalEstate = this.comercialEventsOfResidentalEstateFormService.getComercialEventsOfResidentalEstate(
      this.editForm
    );
    if (comercialEventsOfResidentalEstate.id !== null) {
      this.subscribeToSaveResponse(this.comercialEventsOfResidentalEstateService.update(comercialEventsOfResidentalEstate));
    } else {
      this.subscribeToSaveResponse(this.comercialEventsOfResidentalEstateService.create(comercialEventsOfResidentalEstate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComercialEventsOfResidentalEstate>>): void {
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

  protected updateForm(comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate): void {
    this.comercialEventsOfResidentalEstate = comercialEventsOfResidentalEstate;
    this.comercialEventsOfResidentalEstateFormService.resetForm(this.editForm, comercialEventsOfResidentalEstate);

    this.typesOfCommercialEventsSharedCollection =
      this.typesOfCommercialEventsService.addTypesOfCommercialEventsToCollectionIfMissing<ITypesOfCommercialEvents>(
        this.typesOfCommercialEventsSharedCollection,
        comercialEventsOfResidentalEstate.typeOfCommercialEventId
      );
    this.clientsSharedCollection = this.clientsService.addClientsToCollectionIfMissing<IClients>(
      this.clientsSharedCollection,
      comercialEventsOfResidentalEstate.clientId
    );
    this.residentalEstatesSharedCollection = this.residentalEstatesService.addResidentalEstatesToCollectionIfMissing<IResidentalEstates>(
      this.residentalEstatesSharedCollection,
      comercialEventsOfResidentalEstate.residentalEstateId
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
            this.comercialEventsOfResidentalEstate?.typeOfCommercialEventId
          )
        )
      )
      .subscribe(
        (typesOfCommercialEvents: ITypesOfCommercialEvents[]) => (this.typesOfCommercialEventsSharedCollection = typesOfCommercialEvents)
      );

    this.clientsService
      .query()
      .pipe(map((res: HttpResponse<IClients[]>) => res.body ?? []))
      .pipe(
        map((clients: IClients[]) =>
          this.clientsService.addClientsToCollectionIfMissing<IClients>(clients, this.comercialEventsOfResidentalEstate?.clientId)
        )
      )
      .subscribe((clients: IClients[]) => (this.clientsSharedCollection = clients));

    this.residentalEstatesService
      .query()
      .pipe(map((res: HttpResponse<IResidentalEstates[]>) => res.body ?? []))
      .pipe(
        map((residentalEstates: IResidentalEstates[]) =>
          this.residentalEstatesService.addResidentalEstatesToCollectionIfMissing<IResidentalEstates>(
            residentalEstates,
            this.comercialEventsOfResidentalEstate?.residentalEstateId
          )
        )
      )
      .subscribe((residentalEstates: IResidentalEstates[]) => (this.residentalEstatesSharedCollection = residentalEstates));
  }
}
