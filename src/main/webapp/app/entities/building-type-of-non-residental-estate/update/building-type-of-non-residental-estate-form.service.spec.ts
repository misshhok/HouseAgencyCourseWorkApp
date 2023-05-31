import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../building-type-of-non-residental-estate.test-samples';

import { BuildingTypeOfNonResidentalEstateFormService } from './building-type-of-non-residental-estate-form.service';

describe('BuildingTypeOfNonResidentalEstate Form Service', () => {
  let service: BuildingTypeOfNonResidentalEstateFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BuildingTypeOfNonResidentalEstateFormService);
  });

  describe('Service methods', () => {
    describe('createBuildingTypeOfNonResidentalEstateFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBuildingTypeOfNonResidentalEstateFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
          })
        );
      });

      it('passing IBuildingTypeOfNonResidentalEstate should create a new form with FormGroup', () => {
        const formGroup = service.createBuildingTypeOfNonResidentalEstateFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
          })
        );
      });
    });

    describe('getBuildingTypeOfNonResidentalEstate', () => {
      it('should return NewBuildingTypeOfNonResidentalEstate for default BuildingTypeOfNonResidentalEstate initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBuildingTypeOfNonResidentalEstateFormGroup(sampleWithNewData);

        const buildingTypeOfNonResidentalEstate = service.getBuildingTypeOfNonResidentalEstate(formGroup) as any;

        expect(buildingTypeOfNonResidentalEstate).toMatchObject(sampleWithNewData);
      });

      it('should return NewBuildingTypeOfNonResidentalEstate for empty BuildingTypeOfNonResidentalEstate initial value', () => {
        const formGroup = service.createBuildingTypeOfNonResidentalEstateFormGroup();

        const buildingTypeOfNonResidentalEstate = service.getBuildingTypeOfNonResidentalEstate(formGroup) as any;

        expect(buildingTypeOfNonResidentalEstate).toMatchObject({});
      });

      it('should return IBuildingTypeOfNonResidentalEstate', () => {
        const formGroup = service.createBuildingTypeOfNonResidentalEstateFormGroup(sampleWithRequiredData);

        const buildingTypeOfNonResidentalEstate = service.getBuildingTypeOfNonResidentalEstate(formGroup) as any;

        expect(buildingTypeOfNonResidentalEstate).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBuildingTypeOfNonResidentalEstate should not enable id FormControl', () => {
        const formGroup = service.createBuildingTypeOfNonResidentalEstateFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBuildingTypeOfNonResidentalEstate should disable id FormControl', () => {
        const formGroup = service.createBuildingTypeOfNonResidentalEstateFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
