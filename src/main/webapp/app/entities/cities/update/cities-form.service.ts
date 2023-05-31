import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICities, NewCities } from '../cities.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICities for edit and NewCitiesFormGroupInput for create.
 */
type CitiesFormGroupInput = ICities | PartialWithRequiredKeyOf<NewCities>;

type CitiesFormDefaults = Pick<NewCities, 'id'>;

type CitiesFormGroupContent = {
  id: FormControl<ICities['id'] | NewCities['id']>;
  title: FormControl<ICities['title']>;
};

export type CitiesFormGroup = FormGroup<CitiesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CitiesFormService {
  createCitiesFormGroup(cities: CitiesFormGroupInput = { id: null }): CitiesFormGroup {
    const citiesRawValue = {
      ...this.getFormDefaults(),
      ...cities,
    };
    return new FormGroup<CitiesFormGroupContent>({
      id: new FormControl(
        { value: citiesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(citiesRawValue.title),
    });
  }

  getCities(form: CitiesFormGroup): ICities | NewCities {
    return form.getRawValue() as ICities | NewCities;
  }

  resetForm(form: CitiesFormGroup, cities: CitiesFormGroupInput): void {
    const citiesRawValue = { ...this.getFormDefaults(), ...cities };
    form.reset(
      {
        ...citiesRawValue,
        id: { value: citiesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CitiesFormDefaults {
    return {
      id: null,
    };
  }
}
