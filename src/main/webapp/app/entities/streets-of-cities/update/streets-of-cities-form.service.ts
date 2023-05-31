import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStreetsOfCities, NewStreetsOfCities } from '../streets-of-cities.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStreetsOfCities for edit and NewStreetsOfCitiesFormGroupInput for create.
 */
type StreetsOfCitiesFormGroupInput = IStreetsOfCities | PartialWithRequiredKeyOf<NewStreetsOfCities>;

type StreetsOfCitiesFormDefaults = Pick<NewStreetsOfCities, 'id'>;

type StreetsOfCitiesFormGroupContent = {
  id: FormControl<IStreetsOfCities['id'] | NewStreetsOfCities['id']>;
  title: FormControl<IStreetsOfCities['title']>;
  cityId: FormControl<IStreetsOfCities['cityId']>;
};

export type StreetsOfCitiesFormGroup = FormGroup<StreetsOfCitiesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StreetsOfCitiesFormService {
  createStreetsOfCitiesFormGroup(streetsOfCities: StreetsOfCitiesFormGroupInput = { id: null }): StreetsOfCitiesFormGroup {
    const streetsOfCitiesRawValue = {
      ...this.getFormDefaults(),
      ...streetsOfCities,
    };
    return new FormGroup<StreetsOfCitiesFormGroupContent>({
      id: new FormControl(
        { value: streetsOfCitiesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(streetsOfCitiesRawValue.title),
      cityId: new FormControl(streetsOfCitiesRawValue.cityId),
    });
  }

  getStreetsOfCities(form: StreetsOfCitiesFormGroup): IStreetsOfCities | NewStreetsOfCities {
    return form.getRawValue() as IStreetsOfCities | NewStreetsOfCities;
  }

  resetForm(form: StreetsOfCitiesFormGroup, streetsOfCities: StreetsOfCitiesFormGroupInput): void {
    const streetsOfCitiesRawValue = { ...this.getFormDefaults(), ...streetsOfCities };
    form.reset(
      {
        ...streetsOfCitiesRawValue,
        id: { value: streetsOfCitiesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StreetsOfCitiesFormDefaults {
    return {
      id: null,
    };
  }
}
