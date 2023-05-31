import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITypesOfCommercialEvents, NewTypesOfCommercialEvents } from '../types-of-commercial-events.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITypesOfCommercialEvents for edit and NewTypesOfCommercialEventsFormGroupInput for create.
 */
type TypesOfCommercialEventsFormGroupInput = ITypesOfCommercialEvents | PartialWithRequiredKeyOf<NewTypesOfCommercialEvents>;

type TypesOfCommercialEventsFormDefaults = Pick<NewTypesOfCommercialEvents, 'id'>;

type TypesOfCommercialEventsFormGroupContent = {
  id: FormControl<ITypesOfCommercialEvents['id'] | NewTypesOfCommercialEvents['id']>;
  title: FormControl<ITypesOfCommercialEvents['title']>;
  description: FormControl<ITypesOfCommercialEvents['description']>;
  estateType: FormControl<ITypesOfCommercialEvents['estateType']>;
};

export type TypesOfCommercialEventsFormGroup = FormGroup<TypesOfCommercialEventsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TypesOfCommercialEventsFormService {
  createTypesOfCommercialEventsFormGroup(
    typesOfCommercialEvents: TypesOfCommercialEventsFormGroupInput = { id: null }
  ): TypesOfCommercialEventsFormGroup {
    const typesOfCommercialEventsRawValue = {
      ...this.getFormDefaults(),
      ...typesOfCommercialEvents,
    };
    return new FormGroup<TypesOfCommercialEventsFormGroupContent>({
      id: new FormControl(
        { value: typesOfCommercialEventsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(typesOfCommercialEventsRawValue.title),
      description: new FormControl(typesOfCommercialEventsRawValue.description),
      estateType: new FormControl(typesOfCommercialEventsRawValue.estateType),
    });
  }

  getTypesOfCommercialEvents(form: TypesOfCommercialEventsFormGroup): ITypesOfCommercialEvents | NewTypesOfCommercialEvents {
    return form.getRawValue() as ITypesOfCommercialEvents | NewTypesOfCommercialEvents;
  }

  resetForm(form: TypesOfCommercialEventsFormGroup, typesOfCommercialEvents: TypesOfCommercialEventsFormGroupInput): void {
    const typesOfCommercialEventsRawValue = { ...this.getFormDefaults(), ...typesOfCommercialEvents };
    form.reset(
      {
        ...typesOfCommercialEventsRawValue,
        id: { value: typesOfCommercialEventsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TypesOfCommercialEventsFormDefaults {
    return {
      id: null,
    };
  }
}
