import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStatusesOfResidentalEstate, NewStatusesOfResidentalEstate } from '../statuses-of-residental-estate.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStatusesOfResidentalEstate for edit and NewStatusesOfResidentalEstateFormGroupInput for create.
 */
type StatusesOfResidentalEstateFormGroupInput = IStatusesOfResidentalEstate | PartialWithRequiredKeyOf<NewStatusesOfResidentalEstate>;

type StatusesOfResidentalEstateFormDefaults = Pick<NewStatusesOfResidentalEstate, 'id'>;

type StatusesOfResidentalEstateFormGroupContent = {
  id: FormControl<IStatusesOfResidentalEstate['id'] | NewStatusesOfResidentalEstate['id']>;
  title: FormControl<IStatusesOfResidentalEstate['title']>;
};

export type StatusesOfResidentalEstateFormGroup = FormGroup<StatusesOfResidentalEstateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StatusesOfResidentalEstateFormService {
  createStatusesOfResidentalEstateFormGroup(
    statusesOfResidentalEstate: StatusesOfResidentalEstateFormGroupInput = { id: null }
  ): StatusesOfResidentalEstateFormGroup {
    const statusesOfResidentalEstateRawValue = {
      ...this.getFormDefaults(),
      ...statusesOfResidentalEstate,
    };
    return new FormGroup<StatusesOfResidentalEstateFormGroupContent>({
      id: new FormControl(
        { value: statusesOfResidentalEstateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(statusesOfResidentalEstateRawValue.title),
    });
  }

  getStatusesOfResidentalEstate(form: StatusesOfResidentalEstateFormGroup): IStatusesOfResidentalEstate | NewStatusesOfResidentalEstate {
    return form.getRawValue() as IStatusesOfResidentalEstate | NewStatusesOfResidentalEstate;
  }

  resetForm(form: StatusesOfResidentalEstateFormGroup, statusesOfResidentalEstate: StatusesOfResidentalEstateFormGroupInput): void {
    const statusesOfResidentalEstateRawValue = { ...this.getFormDefaults(), ...statusesOfResidentalEstate };
    form.reset(
      {
        ...statusesOfResidentalEstateRawValue,
        id: { value: statusesOfResidentalEstateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StatusesOfResidentalEstateFormDefaults {
    return {
      id: null,
    };
  }
}
