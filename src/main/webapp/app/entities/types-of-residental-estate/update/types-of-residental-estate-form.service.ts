import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITypesOfResidentalEstate, NewTypesOfResidentalEstate } from '../types-of-residental-estate.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITypesOfResidentalEstate for edit and NewTypesOfResidentalEstateFormGroupInput for create.
 */
type TypesOfResidentalEstateFormGroupInput = ITypesOfResidentalEstate | PartialWithRequiredKeyOf<NewTypesOfResidentalEstate>;

type TypesOfResidentalEstateFormDefaults = Pick<NewTypesOfResidentalEstate, 'id'>;

type TypesOfResidentalEstateFormGroupContent = {
  id: FormControl<ITypesOfResidentalEstate['id'] | NewTypesOfResidentalEstate['id']>;
  title: FormControl<ITypesOfResidentalEstate['title']>;
};

export type TypesOfResidentalEstateFormGroup = FormGroup<TypesOfResidentalEstateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TypesOfResidentalEstateFormService {
  createTypesOfResidentalEstateFormGroup(
    typesOfResidentalEstate: TypesOfResidentalEstateFormGroupInput = { id: null }
  ): TypesOfResidentalEstateFormGroup {
    const typesOfResidentalEstateRawValue = {
      ...this.getFormDefaults(),
      ...typesOfResidentalEstate,
    };
    return new FormGroup<TypesOfResidentalEstateFormGroupContent>({
      id: new FormControl(
        { value: typesOfResidentalEstateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(typesOfResidentalEstateRawValue.title),
    });
  }

  getTypesOfResidentalEstate(form: TypesOfResidentalEstateFormGroup): ITypesOfResidentalEstate | NewTypesOfResidentalEstate {
    return form.getRawValue() as ITypesOfResidentalEstate | NewTypesOfResidentalEstate;
  }

  resetForm(form: TypesOfResidentalEstateFormGroup, typesOfResidentalEstate: TypesOfResidentalEstateFormGroupInput): void {
    const typesOfResidentalEstateRawValue = { ...this.getFormDefaults(), ...typesOfResidentalEstate };
    form.reset(
      {
        ...typesOfResidentalEstateRawValue,
        id: { value: typesOfResidentalEstateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TypesOfResidentalEstateFormDefaults {
    return {
      id: null,
    };
  }
}
