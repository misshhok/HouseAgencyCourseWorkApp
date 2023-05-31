import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../types-of-residental-estate.test-samples';

import { TypesOfResidentalEstateFormService } from './types-of-residental-estate-form.service';

describe('TypesOfResidentalEstate Form Service', () => {
  let service: TypesOfResidentalEstateFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypesOfResidentalEstateFormService);
  });

  describe('Service methods', () => {
    describe('createTypesOfResidentalEstateFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTypesOfResidentalEstateFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
          })
        );
      });

      it('passing ITypesOfResidentalEstate should create a new form with FormGroup', () => {
        const formGroup = service.createTypesOfResidentalEstateFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
          })
        );
      });
    });

    describe('getTypesOfResidentalEstate', () => {
      it('should return NewTypesOfResidentalEstate for default TypesOfResidentalEstate initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTypesOfResidentalEstateFormGroup(sampleWithNewData);

        const typesOfResidentalEstate = service.getTypesOfResidentalEstate(formGroup) as any;

        expect(typesOfResidentalEstate).toMatchObject(sampleWithNewData);
      });

      it('should return NewTypesOfResidentalEstate for empty TypesOfResidentalEstate initial value', () => {
        const formGroup = service.createTypesOfResidentalEstateFormGroup();

        const typesOfResidentalEstate = service.getTypesOfResidentalEstate(formGroup) as any;

        expect(typesOfResidentalEstate).toMatchObject({});
      });

      it('should return ITypesOfResidentalEstate', () => {
        const formGroup = service.createTypesOfResidentalEstateFormGroup(sampleWithRequiredData);

        const typesOfResidentalEstate = service.getTypesOfResidentalEstate(formGroup) as any;

        expect(typesOfResidentalEstate).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITypesOfResidentalEstate should not enable id FormControl', () => {
        const formGroup = service.createTypesOfResidentalEstateFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTypesOfResidentalEstate should disable id FormControl', () => {
        const formGroup = service.createTypesOfResidentalEstateFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
