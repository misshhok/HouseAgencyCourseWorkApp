import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IResidentalEstates, NewResidentalEstates } from '../residental-estates.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResidentalEstates for edit and NewResidentalEstatesFormGroupInput for create.
 */
type ResidentalEstatesFormGroupInput = IResidentalEstates | PartialWithRequiredKeyOf<NewResidentalEstates>;

type ResidentalEstatesFormDefaults = Pick<NewResidentalEstates, 'id' | 'hasBalcony'>;

type ResidentalEstatesFormGroupContent = {
  id: FormControl<IResidentalEstates['id'] | NewResidentalEstates['id']>;
  livingSpace: FormControl<IResidentalEstates['livingSpace']>;
  cadastralNumber: FormControl<IResidentalEstates['cadastralNumber']>;
  commissioningDate: FormControl<IResidentalEstates['commissioningDate']>;
  numberOfRooms: FormControl<IResidentalEstates['numberOfRooms']>;
  furnishType: FormControl<IResidentalEstates['furnishType']>;
  hasBalcony: FormControl<IResidentalEstates['hasBalcony']>;
  bathroomType: FormControl<IResidentalEstates['bathroomType']>;
  ceilingHeight: FormControl<IResidentalEstates['ceilingHeight']>;
  price: FormControl<IResidentalEstates['price']>;
  addressId: FormControl<IResidentalEstates['addressId']>;
  typeOfResidentalEstateId: FormControl<IResidentalEstates['typeOfResidentalEstateId']>;
  statusOfResidentalEstateId: FormControl<IResidentalEstates['statusOfResidentalEstateId']>;
};

export type ResidentalEstatesFormGroup = FormGroup<ResidentalEstatesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResidentalEstatesFormService {
  createResidentalEstatesFormGroup(residentalEstates: ResidentalEstatesFormGroupInput = { id: null }): ResidentalEstatesFormGroup {
    const residentalEstatesRawValue = {
      ...this.getFormDefaults(),
      ...residentalEstates,
    };
    return new FormGroup<ResidentalEstatesFormGroupContent>({
      id: new FormControl(
        { value: residentalEstatesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      livingSpace: new FormControl(residentalEstatesRawValue.livingSpace),
      cadastralNumber: new FormControl(residentalEstatesRawValue.cadastralNumber),
      commissioningDate: new FormControl(residentalEstatesRawValue.commissioningDate),
      numberOfRooms: new FormControl(residentalEstatesRawValue.numberOfRooms),
      furnishType: new FormControl(residentalEstatesRawValue.furnishType),
      hasBalcony: new FormControl(residentalEstatesRawValue.hasBalcony),
      bathroomType: new FormControl(residentalEstatesRawValue.bathroomType),
      ceilingHeight: new FormControl(residentalEstatesRawValue.ceilingHeight),
      price: new FormControl(residentalEstatesRawValue.price),
      addressId: new FormControl(residentalEstatesRawValue.addressId),
      typeOfResidentalEstateId: new FormControl(residentalEstatesRawValue.typeOfResidentalEstateId),
      statusOfResidentalEstateId: new FormControl(residentalEstatesRawValue.statusOfResidentalEstateId),
    });
  }

  getResidentalEstates(form: ResidentalEstatesFormGroup): IResidentalEstates | NewResidentalEstates {
    return form.getRawValue() as IResidentalEstates | NewResidentalEstates;
  }

  resetForm(form: ResidentalEstatesFormGroup, residentalEstates: ResidentalEstatesFormGroupInput): void {
    const residentalEstatesRawValue = { ...this.getFormDefaults(), ...residentalEstates };
    form.reset(
      {
        ...residentalEstatesRawValue,
        id: { value: residentalEstatesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ResidentalEstatesFormDefaults {
    return {
      id: null,
      hasBalcony: false,
    };
  }
}
