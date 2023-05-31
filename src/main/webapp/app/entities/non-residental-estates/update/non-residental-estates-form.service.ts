import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INonResidentalEstates, NewNonResidentalEstates } from '../non-residental-estates.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INonResidentalEstates for edit and NewNonResidentalEstatesFormGroupInput for create.
 */
type NonResidentalEstatesFormGroupInput = INonResidentalEstates | PartialWithRequiredKeyOf<NewNonResidentalEstates>;

type NonResidentalEstatesFormDefaults = Pick<NewNonResidentalEstates, 'id'>;

type NonResidentalEstatesFormGroupContent = {
  id: FormControl<INonResidentalEstates['id'] | NewNonResidentalEstates['id']>;
  price: FormControl<INonResidentalEstates['price']>;
  commissioningDate: FormControl<INonResidentalEstates['commissioningDate']>;
  cadastralNumber: FormControl<INonResidentalEstates['cadastralNumber']>;
  totalSpace: FormControl<INonResidentalEstates['totalSpace']>;
  purposeOfNonResidentalEstateId: FormControl<INonResidentalEstates['purposeOfNonResidentalEstateId']>;
  buildingTypeOfNonResidentalEstateId: FormControl<INonResidentalEstates['buildingTypeOfNonResidentalEstateId']>;
  addressId: FormControl<INonResidentalEstates['addressId']>;
};

export type NonResidentalEstatesFormGroup = FormGroup<NonResidentalEstatesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NonResidentalEstatesFormService {
  createNonResidentalEstatesFormGroup(
    nonResidentalEstates: NonResidentalEstatesFormGroupInput = { id: null }
  ): NonResidentalEstatesFormGroup {
    const nonResidentalEstatesRawValue = {
      ...this.getFormDefaults(),
      ...nonResidentalEstates,
    };
    return new FormGroup<NonResidentalEstatesFormGroupContent>({
      id: new FormControl(
        { value: nonResidentalEstatesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      price: new FormControl(nonResidentalEstatesRawValue.price),
      commissioningDate: new FormControl(nonResidentalEstatesRawValue.commissioningDate),
      cadastralNumber: new FormControl(nonResidentalEstatesRawValue.cadastralNumber),
      totalSpace: new FormControl(nonResidentalEstatesRawValue.totalSpace),
      purposeOfNonResidentalEstateId: new FormControl(nonResidentalEstatesRawValue.purposeOfNonResidentalEstateId),
      buildingTypeOfNonResidentalEstateId: new FormControl(nonResidentalEstatesRawValue.buildingTypeOfNonResidentalEstateId),
      addressId: new FormControl(nonResidentalEstatesRawValue.addressId),
    });
  }

  getNonResidentalEstates(form: NonResidentalEstatesFormGroup): INonResidentalEstates | NewNonResidentalEstates {
    return form.getRawValue() as INonResidentalEstates | NewNonResidentalEstates;
  }

  resetForm(form: NonResidentalEstatesFormGroup, nonResidentalEstates: NonResidentalEstatesFormGroupInput): void {
    const nonResidentalEstatesRawValue = { ...this.getFormDefaults(), ...nonResidentalEstates };
    form.reset(
      {
        ...nonResidentalEstatesRawValue,
        id: { value: nonResidentalEstatesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NonResidentalEstatesFormDefaults {
    return {
      id: null,
    };
  }
}
