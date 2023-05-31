import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPurposesOfNonResidentalEstate, NewPurposesOfNonResidentalEstate } from '../purposes-of-non-residental-estate.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPurposesOfNonResidentalEstate for edit and NewPurposesOfNonResidentalEstateFormGroupInput for create.
 */
type PurposesOfNonResidentalEstateFormGroupInput =
  | IPurposesOfNonResidentalEstate
  | PartialWithRequiredKeyOf<NewPurposesOfNonResidentalEstate>;

type PurposesOfNonResidentalEstateFormDefaults = Pick<NewPurposesOfNonResidentalEstate, 'id'>;

type PurposesOfNonResidentalEstateFormGroupContent = {
  id: FormControl<IPurposesOfNonResidentalEstate['id'] | NewPurposesOfNonResidentalEstate['id']>;
  title: FormControl<IPurposesOfNonResidentalEstate['title']>;
};

export type PurposesOfNonResidentalEstateFormGroup = FormGroup<PurposesOfNonResidentalEstateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PurposesOfNonResidentalEstateFormService {
  createPurposesOfNonResidentalEstateFormGroup(
    purposesOfNonResidentalEstate: PurposesOfNonResidentalEstateFormGroupInput = { id: null }
  ): PurposesOfNonResidentalEstateFormGroup {
    const purposesOfNonResidentalEstateRawValue = {
      ...this.getFormDefaults(),
      ...purposesOfNonResidentalEstate,
    };
    return new FormGroup<PurposesOfNonResidentalEstateFormGroupContent>({
      id: new FormControl(
        { value: purposesOfNonResidentalEstateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(purposesOfNonResidentalEstateRawValue.title),
    });
  }

  getPurposesOfNonResidentalEstate(
    form: PurposesOfNonResidentalEstateFormGroup
  ): IPurposesOfNonResidentalEstate | NewPurposesOfNonResidentalEstate {
    return form.getRawValue() as IPurposesOfNonResidentalEstate | NewPurposesOfNonResidentalEstate;
  }

  resetForm(
    form: PurposesOfNonResidentalEstateFormGroup,
    purposesOfNonResidentalEstate: PurposesOfNonResidentalEstateFormGroupInput
  ): void {
    const purposesOfNonResidentalEstateRawValue = { ...this.getFormDefaults(), ...purposesOfNonResidentalEstate };
    form.reset(
      {
        ...purposesOfNonResidentalEstateRawValue,
        id: { value: purposesOfNonResidentalEstateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PurposesOfNonResidentalEstateFormDefaults {
    return {
      id: null,
    };
  }
}
