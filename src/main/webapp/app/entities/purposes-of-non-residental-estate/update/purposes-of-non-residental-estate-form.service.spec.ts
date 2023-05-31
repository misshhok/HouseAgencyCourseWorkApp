import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../purposes-of-non-residental-estate.test-samples';

import { PurposesOfNonResidentalEstateFormService } from './purposes-of-non-residental-estate-form.service';

describe('PurposesOfNonResidentalEstate Form Service', () => {
  let service: PurposesOfNonResidentalEstateFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PurposesOfNonResidentalEstateFormService);
  });

  describe('Service methods', () => {
    describe('createPurposesOfNonResidentalEstateFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPurposesOfNonResidentalEstateFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
          })
        );
      });

      it('passing IPurposesOfNonResidentalEstate should create a new form with FormGroup', () => {
        const formGroup = service.createPurposesOfNonResidentalEstateFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
          })
        );
      });
    });

    describe('getPurposesOfNonResidentalEstate', () => {
      it('should return NewPurposesOfNonResidentalEstate for default PurposesOfNonResidentalEstate initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPurposesOfNonResidentalEstateFormGroup(sampleWithNewData);

        const purposesOfNonResidentalEstate = service.getPurposesOfNonResidentalEstate(formGroup) as any;

        expect(purposesOfNonResidentalEstate).toMatchObject(sampleWithNewData);
      });

      it('should return NewPurposesOfNonResidentalEstate for empty PurposesOfNonResidentalEstate initial value', () => {
        const formGroup = service.createPurposesOfNonResidentalEstateFormGroup();

        const purposesOfNonResidentalEstate = service.getPurposesOfNonResidentalEstate(formGroup) as any;

        expect(purposesOfNonResidentalEstate).toMatchObject({});
      });

      it('should return IPurposesOfNonResidentalEstate', () => {
        const formGroup = service.createPurposesOfNonResidentalEstateFormGroup(sampleWithRequiredData);

        const purposesOfNonResidentalEstate = service.getPurposesOfNonResidentalEstate(formGroup) as any;

        expect(purposesOfNonResidentalEstate).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPurposesOfNonResidentalEstate should not enable id FormControl', () => {
        const formGroup = service.createPurposesOfNonResidentalEstateFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPurposesOfNonResidentalEstate should disable id FormControl', () => {
        const formGroup = service.createPurposesOfNonResidentalEstateFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
