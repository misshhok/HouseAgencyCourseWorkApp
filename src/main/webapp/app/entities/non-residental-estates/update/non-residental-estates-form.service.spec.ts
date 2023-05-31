import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../non-residental-estates.test-samples';

import { NonResidentalEstatesFormService } from './non-residental-estates-form.service';

describe('NonResidentalEstates Form Service', () => {
  let service: NonResidentalEstatesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NonResidentalEstatesFormService);
  });

  describe('Service methods', () => {
    describe('createNonResidentalEstatesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNonResidentalEstatesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            price: expect.any(Object),
            commissioningDate: expect.any(Object),
            cadastralNumber: expect.any(Object),
            totalSpace: expect.any(Object),
            purposeOfNonResidentalEstateId: expect.any(Object),
            buildingTypeOfNonResidentalEstateId: expect.any(Object),
            addressId: expect.any(Object),
          })
        );
      });

      it('passing INonResidentalEstates should create a new form with FormGroup', () => {
        const formGroup = service.createNonResidentalEstatesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            price: expect.any(Object),
            commissioningDate: expect.any(Object),
            cadastralNumber: expect.any(Object),
            totalSpace: expect.any(Object),
            purposeOfNonResidentalEstateId: expect.any(Object),
            buildingTypeOfNonResidentalEstateId: expect.any(Object),
            addressId: expect.any(Object),
          })
        );
      });
    });

    describe('getNonResidentalEstates', () => {
      it('should return NewNonResidentalEstates for default NonResidentalEstates initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNonResidentalEstatesFormGroup(sampleWithNewData);

        const nonResidentalEstates = service.getNonResidentalEstates(formGroup) as any;

        expect(nonResidentalEstates).toMatchObject(sampleWithNewData);
      });

      it('should return NewNonResidentalEstates for empty NonResidentalEstates initial value', () => {
        const formGroup = service.createNonResidentalEstatesFormGroup();

        const nonResidentalEstates = service.getNonResidentalEstates(formGroup) as any;

        expect(nonResidentalEstates).toMatchObject({});
      });

      it('should return INonResidentalEstates', () => {
        const formGroup = service.createNonResidentalEstatesFormGroup(sampleWithRequiredData);

        const nonResidentalEstates = service.getNonResidentalEstates(formGroup) as any;

        expect(nonResidentalEstates).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INonResidentalEstates should not enable id FormControl', () => {
        const formGroup = service.createNonResidentalEstatesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNonResidentalEstates should disable id FormControl', () => {
        const formGroup = service.createNonResidentalEstatesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
