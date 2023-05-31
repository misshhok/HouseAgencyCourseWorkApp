import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBuildingTypeOfNonResidentalEstate, NewBuildingTypeOfNonResidentalEstate } from '../building-type-of-non-residental-estate.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBuildingTypeOfNonResidentalEstate for edit and NewBuildingTypeOfNonResidentalEstateFormGroupInput for create.
 */
type BuildingTypeOfNonResidentalEstateFormGroupInput =
  | IBuildingTypeOfNonResidentalEstate
  | PartialWithRequiredKeyOf<NewBuildingTypeOfNonResidentalEstate>;

type BuildingTypeOfNonResidentalEstateFormDefaults = Pick<NewBuildingTypeOfNonResidentalEstate, 'id'>;

type BuildingTypeOfNonResidentalEstateFormGroupContent = {
  id: FormControl<IBuildingTypeOfNonResidentalEstate['id'] | NewBuildingTypeOfNonResidentalEstate['id']>;
  title: FormControl<IBuildingTypeOfNonResidentalEstate['title']>;
};

export type BuildingTypeOfNonResidentalEstateFormGroup = FormGroup<BuildingTypeOfNonResidentalEstateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BuildingTypeOfNonResidentalEstateFormService {
  createBuildingTypeOfNonResidentalEstateFormGroup(
    buildingTypeOfNonResidentalEstate: BuildingTypeOfNonResidentalEstateFormGroupInput = { id: null }
  ): BuildingTypeOfNonResidentalEstateFormGroup {
    const buildingTypeOfNonResidentalEstateRawValue = {
      ...this.getFormDefaults(),
      ...buildingTypeOfNonResidentalEstate,
    };
    return new FormGroup<BuildingTypeOfNonResidentalEstateFormGroupContent>({
      id: new FormControl(
        { value: buildingTypeOfNonResidentalEstateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(buildingTypeOfNonResidentalEstateRawValue.title),
    });
  }

  getBuildingTypeOfNonResidentalEstate(
    form: BuildingTypeOfNonResidentalEstateFormGroup
  ): IBuildingTypeOfNonResidentalEstate | NewBuildingTypeOfNonResidentalEstate {
    return form.getRawValue() as IBuildingTypeOfNonResidentalEstate | NewBuildingTypeOfNonResidentalEstate;
  }

  resetForm(
    form: BuildingTypeOfNonResidentalEstateFormGroup,
    buildingTypeOfNonResidentalEstate: BuildingTypeOfNonResidentalEstateFormGroupInput
  ): void {
    const buildingTypeOfNonResidentalEstateRawValue = { ...this.getFormDefaults(), ...buildingTypeOfNonResidentalEstate };
    form.reset(
      {
        ...buildingTypeOfNonResidentalEstateRawValue,
        id: { value: buildingTypeOfNonResidentalEstateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BuildingTypeOfNonResidentalEstateFormDefaults {
    return {
      id: null,
    };
  }
}
