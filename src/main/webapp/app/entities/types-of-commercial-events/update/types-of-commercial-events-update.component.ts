import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TypesOfCommercialEventsFormService, TypesOfCommercialEventsFormGroup } from './types-of-commercial-events-form.service';
import { ITypesOfCommercialEvents } from '../types-of-commercial-events.model';
import { TypesOfCommercialEventsService } from '../service/types-of-commercial-events.service';

@Component({
  selector: 'jhi-types-of-commercial-events-update',
  templateUrl: './types-of-commercial-events-update.component.html',
})
export class TypesOfCommercialEventsUpdateComponent implements OnInit {
  isSaving = false;
  typesOfCommercialEvents: ITypesOfCommercialEvents | null = null;

  editForm: TypesOfCommercialEventsFormGroup = this.typesOfCommercialEventsFormService.createTypesOfCommercialEventsFormGroup();

  constructor(
    protected typesOfCommercialEventsService: TypesOfCommercialEventsService,
    protected typesOfCommercialEventsFormService: TypesOfCommercialEventsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typesOfCommercialEvents }) => {
      this.typesOfCommercialEvents = typesOfCommercialEvents;
      if (typesOfCommercialEvents) {
        this.updateForm(typesOfCommercialEvents);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typesOfCommercialEvents = this.typesOfCommercialEventsFormService.getTypesOfCommercialEvents(this.editForm);
    if (typesOfCommercialEvents.id !== null) {
      this.subscribeToSaveResponse(this.typesOfCommercialEventsService.update(typesOfCommercialEvents));
    } else {
      this.subscribeToSaveResponse(this.typesOfCommercialEventsService.create(typesOfCommercialEvents));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypesOfCommercialEvents>>): void {
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

  protected updateForm(typesOfCommercialEvents: ITypesOfCommercialEvents): void {
    this.typesOfCommercialEvents = typesOfCommercialEvents;
    this.typesOfCommercialEventsFormService.resetForm(this.editForm, typesOfCommercialEvents);
  }
}
