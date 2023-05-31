import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IComercialEventsOfResidentalEstate, NewComercialEventsOfResidentalEstate } from '../comercial-events-of-residental-estate.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IComercialEventsOfResidentalEstate for edit and NewComercialEventsOfResidentalEstateFormGroupInput for create.
 */
type ComercialEventsOfResidentalEstateFormGroupInput =
  | IComercialEventsOfResidentalEstate
  | PartialWithRequiredKeyOf<NewComercialEventsOfResidentalEstate>;

type ComercialEventsOfResidentalEstateFormDefaults = Pick<NewComercialEventsOfResidentalEstate, 'id'>;

type ComercialEventsOfResidentalEstateFormGroupContent = {
  id: FormControl<IComercialEventsOfResidentalEstate['id'] | NewComercialEventsOfResidentalEstate['id']>;
  agentNotes: FormControl<IComercialEventsOfResidentalEstate['agentNotes']>;
  dateOfEvent: FormControl<IComercialEventsOfResidentalEstate['dateOfEvent']>;
  typeOfCommercialEventId: FormControl<IComercialEventsOfResidentalEstate['typeOfCommercialEventId']>;
  clientId: FormControl<IComercialEventsOfResidentalEstate['clientId']>;
  residentalEstateId: FormControl<IComercialEventsOfResidentalEstate['residentalEstateId']>;
};

export type ComercialEventsOfResidentalEstateFormGroup = FormGroup<ComercialEventsOfResidentalEstateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ComercialEventsOfResidentalEstateFormService {
  createComercialEventsOfResidentalEstateFormGroup(
    comercialEventsOfResidentalEstate: ComercialEventsOfResidentalEstateFormGroupInput = { id: null }
  ): ComercialEventsOfResidentalEstateFormGroup {
    const comercialEventsOfResidentalEstateRawValue = {
      ...this.getFormDefaults(),
      ...comercialEventsOfResidentalEstate,
    };
    return new FormGroup<ComercialEventsOfResidentalEstateFormGroupContent>({
      id: new FormControl(
        { value: comercialEventsOfResidentalEstateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      agentNotes: new FormControl(comercialEventsOfResidentalEstateRawValue.agentNotes),
      dateOfEvent: new FormControl(comercialEventsOfResidentalEstateRawValue.dateOfEvent),
      typeOfCommercialEventId: new FormControl(comercialEventsOfResidentalEstateRawValue.typeOfCommercialEventId),
      clientId: new FormControl(comercialEventsOfResidentalEstateRawValue.clientId),
      residentalEstateId: new FormControl(comercialEventsOfResidentalEstateRawValue.residentalEstateId),
    });
  }

  getComercialEventsOfResidentalEstate(
    form: ComercialEventsOfResidentalEstateFormGroup
  ): IComercialEventsOfResidentalEstate | NewComercialEventsOfResidentalEstate {
    return form.getRawValue() as IComercialEventsOfResidentalEstate | NewComercialEventsOfResidentalEstate;
  }

  resetForm(
    form: ComercialEventsOfResidentalEstateFormGroup,
    comercialEventsOfResidentalEstate: ComercialEventsOfResidentalEstateFormGroupInput
  ): void {
    const comercialEventsOfResidentalEstateRawValue = { ...this.getFormDefaults(), ...comercialEventsOfResidentalEstate };
    form.reset(
      {
        ...comercialEventsOfResidentalEstateRawValue,
        id: { value: comercialEventsOfResidentalEstateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ComercialEventsOfResidentalEstateFormDefaults {
    return {
      id: null,
    };
  }
}
