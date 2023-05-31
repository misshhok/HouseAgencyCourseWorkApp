import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TypesOfResidentalEstateFormService, TypesOfResidentalEstateFormGroup } from './types-of-residental-estate-form.service';
import { ITypesOfResidentalEstate } from '../types-of-residental-estate.model';
import { TypesOfResidentalEstateService } from '../service/types-of-residental-estate.service';

@Component({
  selector: 'jhi-types-of-residental-estate-update',
  templateUrl: './types-of-residental-estate-update.component.html',
})
export class TypesOfResidentalEstateUpdateComponent implements OnInit {
  isSaving = false;
  typesOfResidentalEstate: ITypesOfResidentalEstate | null = null;

  editForm: TypesOfResidentalEstateFormGroup = this.typesOfResidentalEstateFormService.createTypesOfResidentalEstateFormGroup();

  constructor(
    protected typesOfResidentalEstateService: TypesOfResidentalEstateService,
    protected typesOfResidentalEstateFormService: TypesOfResidentalEstateFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typesOfResidentalEstate }) => {
      this.typesOfResidentalEstate = typesOfResidentalEstate;
      if (typesOfResidentalEstate) {
        this.updateForm(typesOfResidentalEstate);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typesOfResidentalEstate = this.typesOfResidentalEstateFormService.getTypesOfResidentalEstate(this.editForm);
    if (typesOfResidentalEstate.id !== null) {
      this.subscribeToSaveResponse(this.typesOfResidentalEstateService.update(typesOfResidentalEstate));
    } else {
      this.subscribeToSaveResponse(this.typesOfResidentalEstateService.create(typesOfResidentalEstate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypesOfResidentalEstate>>): void {
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

  protected updateForm(typesOfResidentalEstate: ITypesOfResidentalEstate): void {
    this.typesOfResidentalEstate = typesOfResidentalEstate;
    this.typesOfResidentalEstateFormService.resetForm(this.editForm, typesOfResidentalEstate);
  }
}
