import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import {
  PurposesOfNonResidentalEstateFormService,
  PurposesOfNonResidentalEstateFormGroup,
} from './purposes-of-non-residental-estate-form.service';
import { IPurposesOfNonResidentalEstate } from '../purposes-of-non-residental-estate.model';
import { PurposesOfNonResidentalEstateService } from '../service/purposes-of-non-residental-estate.service';

@Component({
  selector: 'jhi-purposes-of-non-residental-estate-update',
  templateUrl: './purposes-of-non-residental-estate-update.component.html',
})
export class PurposesOfNonResidentalEstateUpdateComponent implements OnInit {
  isSaving = false;
  purposesOfNonResidentalEstate: IPurposesOfNonResidentalEstate | null = null;

  editForm: PurposesOfNonResidentalEstateFormGroup =
    this.purposesOfNonResidentalEstateFormService.createPurposesOfNonResidentalEstateFormGroup();

  constructor(
    protected purposesOfNonResidentalEstateService: PurposesOfNonResidentalEstateService,
    protected purposesOfNonResidentalEstateFormService: PurposesOfNonResidentalEstateFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purposesOfNonResidentalEstate }) => {
      this.purposesOfNonResidentalEstate = purposesOfNonResidentalEstate;
      if (purposesOfNonResidentalEstate) {
        this.updateForm(purposesOfNonResidentalEstate);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const purposesOfNonResidentalEstate = this.purposesOfNonResidentalEstateFormService.getPurposesOfNonResidentalEstate(this.editForm);
    if (purposesOfNonResidentalEstate.id !== null) {
      this.subscribeToSaveResponse(this.purposesOfNonResidentalEstateService.update(purposesOfNonResidentalEstate));
    } else {
      this.subscribeToSaveResponse(this.purposesOfNonResidentalEstateService.create(purposesOfNonResidentalEstate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurposesOfNonResidentalEstate>>): void {
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

  protected updateForm(purposesOfNonResidentalEstate: IPurposesOfNonResidentalEstate): void {
    this.purposesOfNonResidentalEstate = purposesOfNonResidentalEstate;
    this.purposesOfNonResidentalEstateFormService.resetForm(this.editForm, purposesOfNonResidentalEstate);
  }
}
