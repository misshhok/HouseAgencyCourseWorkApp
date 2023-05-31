import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../residental-estates.test-samples';

import { ResidentalEstatesFormService } from './residental-estates-form.service';

describe('ResidentalEstates Form Service', () => {
  let service: ResidentalEstatesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResidentalEstatesFormService);
  });

  describe('Service methods', () => {
    describe('createResidentalEstatesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResidentalEstatesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            livingSpace: expect.any(Object),
            cadastralNumber: expect.any(Object),
            commissioningDate: expect.any(Object),
            numberOfRooms: expect.any(Object),
            furnishType: expect.any(Object),
            hasBalcony: expect.any(Object),
            bathroomType: expect.any(Object),
            ceilingHeight: expect.any(Object),
            price: expect.any(Object),
            addressId: expect.any(Object),
            typeOfResidentalEstateId: expect.any(Object),
            statusOfResidentalEstateId: expect.any(Object),
          })
        );
      });

      it('passing IResidentalEstates should create a new form with FormGroup', () => {
        const formGroup = service.createResidentalEstatesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            livingSpace: expect.any(Object),
            cadastralNumber: expect.any(Object),
            commissioningDate: expect.any(Object),
            numberOfRooms: expect.any(Object),
            furnishType: expect.any(Object),
            hasBalcony: expect.any(Object),
            bathroomType: expect.any(Object),
            ceilingHeight: expect.any(Object),
            price: expect.any(Object),
            addressId: expect.any(Object),
            typeOfResidentalEstateId: expect.any(Object),
            statusOfResidentalEstateId: expect.any(Object),
          })
        );
      });
    });

    describe('getResidentalEstates', () => {
      it('should return NewResidentalEstates for default ResidentalEstates initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createResidentalEstatesFormGroup(sampleWithNewData);

        const residentalEstates = service.getResidentalEstates(formGroup) as any;

        expect(residentalEstates).toMatchObject(sampleWithNewData);
      });

      it('should return NewResidentalEstates for empty ResidentalEstates initial value', () => {
        const formGroup = service.createResidentalEstatesFormGroup();

        const residentalEstates = service.getResidentalEstates(formGroup) as any;

        expect(residentalEstates).toMatchObject({});
      });

      it('should return IResidentalEstates', () => {
        const formGroup = service.createResidentalEstatesFormGroup(sampleWithRequiredData);

        const residentalEstates = service.getResidentalEstates(formGroup) as any;

        expect(residentalEstates).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResidentalEstates should not enable id FormControl', () => {
        const formGroup = service.createResidentalEstatesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResidentalEstates should disable id FormControl', () => {
        const formGroup = service.createResidentalEstatesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
