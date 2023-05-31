import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import {
  IComercialEventsOfNonResidentalEstate,
  NewComercialEventsOfNonResidentalEstate,
} from '../comercial-events-of-non-residental-estate.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IComercialEventsOfNonResidentalEstate for edit and NewComercialEventsOfNonResidentalEstateFormGroupInput for create.
 */
type ComercialEventsOfNonResidentalEstateFormGroupInput =
  | IComercialEventsOfNonResidentalEstate
  | PartialWithRequiredKeyOf<NewComercialEventsOfNonResidentalEstate>;

type ComercialEventsOfNonResidentalEstateFormDefaults = Pick<NewComercialEventsOfNonResidentalEstate, 'id'>;

type ComercialEventsOfNonResidentalEstateFormGroupContent = {
  id: FormControl<IComercialEventsOfNonResidentalEstate['id'] | NewComercialEventsOfNonResidentalEstate['id']>;
  agentNotes: FormControl<IComercialEventsOfNonResidentalEstate['agentNotes']>;
  dateOfEvent: FormControl<IComercialEventsOfNonResidentalEstate['dateOfEvent']>;
  typeOfCommercialEventId: FormControl<IComercialEventsOfNonResidentalEstate['typeOfCommercialEventId']>;
  nonResidentalEstateId: FormControl<IComercialEventsOfNonResidentalEstate['nonResidentalEstateId']>;
  clientId: FormControl<IComercialEventsOfNonResidentalEstate['clientId']>;
};

export type ComercialEventsOfNonResidentalEstateFormGroup = FormGroup<ComercialEventsOfNonResidentalEstateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ComercialEventsOfNonResidentalEstateFormService {
  createComercialEventsOfNonResidentalEstateFormGroup(
    comercialEventsOfNonResidentalEstate: ComercialEventsOfNonResidentalEstateFormGroupInput = { id: null }
  ): ComercialEventsOfNonResidentalEstateFormGroup {
    const comercialEventsOfNonResidentalEstateRawValue = {
      ...this.getFormDefaults(),
      ...comercialEventsOfNonResidentalEstate,
    };
    return new FormGroup<ComercialEventsOfNonResidentalEstateFormGroupContent>({
      id: new FormControl(
        { value: comercialEventsOfNonResidentalEstateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      agentNotes: new FormControl(comercialEventsOfNonResidentalEstateRawValue.agentNotes),
      dateOfEvent: new FormControl(comercialEventsOfNonResidentalEstateRawValue.dateOfEvent),
      typeOfCommercialEventId: new FormControl(comercialEventsOfNonResidentalEstateRawValue.typeOfCommercialEventId),
      nonResidentalEstateId: new FormControl(comercialEventsOfNonResidentalEstateRawValue.nonResidentalEstateId),
      clientId: new FormControl(comercialEventsOfNonResidentalEstateRawValue.clientId),
    });
  }

  getComercialEventsOfNonResidentalEstate(
    form: ComercialEventsOfNonResidentalEstateFormGroup
  ): IComercialEventsOfNonResidentalEstate | NewComercialEventsOfNonResidentalEstate {
    return form.getRawValue() as IComercialEventsOfNonResidentalEstate | NewComercialEventsOfNonResidentalEstate;
  }

  resetForm(
    form: ComercialEventsOfNonResidentalEstateFormGroup,
    comercialEventsOfNonResidentalEstate: ComercialEventsOfNonResidentalEstateFormGroupInput
  ): void {
    const comercialEventsOfNonResidentalEstateRawValue = { ...this.getFormDefaults(), ...comercialEventsOfNonResidentalEstate };
    form.reset(
      {
        ...comercialEventsOfNonResidentalEstateRawValue,
        id: { value: comercialEventsOfNonResidentalEstateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ComercialEventsOfNonResidentalEstateFormDefaults {
    return {
      id: null,
    };
  }
}
