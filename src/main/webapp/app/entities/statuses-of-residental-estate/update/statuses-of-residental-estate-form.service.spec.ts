import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../statuses-of-residental-estate.test-samples';

import { StatusesOfResidentalEstateFormService } from './statuses-of-residental-estate-form.service';

describe('StatusesOfResidentalEstate Form Service', () => {
  let service: StatusesOfResidentalEstateFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatusesOfResidentalEstateFormService);
  });

  describe('Service methods', () => {
    describe('createStatusesOfResidentalEstateFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStatusesOfResidentalEstateFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
          })
        );
      });

      it('passing IStatusesOfResidentalEstate should create a new form with FormGroup', () => {
        const formGroup = service.createStatusesOfResidentalEstateFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
          })
        );
      });
    });

    describe('getStatusesOfResidentalEstate', () => {
      it('should return NewStatusesOfResidentalEstate for default StatusesOfResidentalEstate initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createStatusesOfResidentalEstateFormGroup(sampleWithNewData);

        const statusesOfResidentalEstate = service.getStatusesOfResidentalEstate(formGroup) as any;

        expect(statusesOfResidentalEstate).toMatchObject(sampleWithNewData);
      });

      it('should return NewStatusesOfResidentalEstate for empty StatusesOfResidentalEstate initial value', () => {
        const formGroup = service.createStatusesOfResidentalEstateFormGroup();

        const statusesOfResidentalEstate = service.getStatusesOfResidentalEstate(formGroup) as any;

        expect(statusesOfResidentalEstate).toMatchObject({});
      });

      it('should return IStatusesOfResidentalEstate', () => {
        const formGroup = service.createStatusesOfResidentalEstateFormGroup(sampleWithRequiredData);

        const statusesOfResidentalEstate = service.getStatusesOfResidentalEstate(formGroup) as any;

        expect(statusesOfResidentalEstate).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStatusesOfResidentalEstate should not enable id FormControl', () => {
        const formGroup = service.createStatusesOfResidentalEstateFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStatusesOfResidentalEstate should disable id FormControl', () => {
        const formGroup = service.createStatusesOfResidentalEstateFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
