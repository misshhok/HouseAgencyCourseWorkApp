import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { StatusesOfResidentalEstateFormService, StatusesOfResidentalEstateFormGroup } from './statuses-of-residental-estate-form.service';
import { IStatusesOfResidentalEstate } from '../statuses-of-residental-estate.model';
import { StatusesOfResidentalEstateService } from '../service/statuses-of-residental-estate.service';

@Component({
  selector: 'jhi-statuses-of-residental-estate-update',
  templateUrl: './statuses-of-residental-estate-update.component.html',
})
export class StatusesOfResidentalEstateUpdateComponent implements OnInit {
  isSaving = false;
  statusesOfResidentalEstate: IStatusesOfResidentalEstate | null = null;

  editForm: StatusesOfResidentalEstateFormGroup = this.statusesOfResidentalEstateFormService.createStatusesOfResidentalEstateFormGroup();

  constructor(
    protected statusesOfResidentalEstateService: StatusesOfResidentalEstateService,
    protected statusesOfResidentalEstateFormService: StatusesOfResidentalEstateFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusesOfResidentalEstate }) => {
      this.statusesOfResidentalEstate = statusesOfResidentalEstate;
      if (statusesOfResidentalEstate) {
        this.updateForm(statusesOfResidentalEstate);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statusesOfResidentalEstate = this.statusesOfResidentalEstateFormService.getStatusesOfResidentalEstate(this.editForm);
    if (statusesOfResidentalEstate.id !== null) {
      this.subscribeToSaveResponse(this.statusesOfResidentalEstateService.update(statusesOfResidentalEstate));
    } else {
      this.subscribeToSaveResponse(this.statusesOfResidentalEstateService.create(statusesOfResidentalEstate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatusesOfResidentalEstate>>): void {
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

  protected updateForm(statusesOfResidentalEstate: IStatusesOfResidentalEstate): void {
    this.statusesOfResidentalEstate = statusesOfResidentalEstate;
    this.statusesOfResidentalEstateFormService.resetForm(this.editForm, statusesOfResidentalEstate);
  }
}
