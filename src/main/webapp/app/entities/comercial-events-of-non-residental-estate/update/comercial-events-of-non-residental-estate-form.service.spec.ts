import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../comercial-events-of-non-residental-estate.test-samples';

import { ComercialEventsOfNonResidentalEstateFormService } from './comercial-events-of-non-residental-estate-form.service';

describe('ComercialEventsOfNonResidentalEstate Form Service', () => {
  let service: ComercialEventsOfNonResidentalEstateFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComercialEventsOfNonResidentalEstateFormService);
  });

  describe('Service methods', () => {
    describe('createComercialEventsOfNonResidentalEstateFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createComercialEventsOfNonResidentalEstateFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            agentNotes: expect.any(Object),
            dateOfEvent: expect.any(Object),
            typeOfCommercialEventId: expect.any(Object),
            nonResidentalEstateId: expect.any(Object),
            clientId: expect.any(Object),
          })
        );
      });

      it('passing IComercialEventsOfNonResidentalEstate should create a new form with FormGroup', () => {
        const formGroup = service.createComercialEventsOfNonResidentalEstateFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            agentNotes: expect.any(Object),
            dateOfEvent: expect.any(Object),
            typeOfCommercialEventId: expect.any(Object),
            nonResidentalEstateId: expect.any(Object),
            clientId: expect.any(Object),
          })
        );
      });
    });

    describe('getComercialEventsOfNonResidentalEstate', () => {
      it('should return NewComercialEventsOfNonResidentalEstate for default ComercialEventsOfNonResidentalEstate initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createComercialEventsOfNonResidentalEstateFormGroup(sampleWithNewData);

        const comercialEventsOfNonResidentalEstate = service.getComercialEventsOfNonResidentalEstate(formGroup) as any;

        expect(comercialEventsOfNonResidentalEstate).toMatchObject(sampleWithNewData);
      });

      it('should return NewComercialEventsOfNonResidentalEstate for empty ComercialEventsOfNonResidentalEstate initial value', () => {
        const formGroup = service.createComercialEventsOfNonResidentalEstateFormGroup();

        const comercialEventsOfNonResidentalEstate = service.getComercialEventsOfNonResidentalEstate(formGroup) as any;

        expect(comercialEventsOfNonResidentalEstate).toMatchObject({});
      });

      it('should return IComercialEventsOfNonResidentalEstate', () => {
        const formGroup = service.createComercialEventsOfNonResidentalEstateFormGroup(sampleWithRequiredData);

        const comercialEventsOfNonResidentalEstate = service.getComercialEventsOfNonResidentalEstate(formGroup) as any;

        expect(comercialEventsOfNonResidentalEstate).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IComercialEventsOfNonResidentalEstate should not enable id FormControl', () => {
        const formGroup = service.createComercialEventsOfNonResidentalEstateFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewComercialEventsOfNonResidentalEstate should disable id FormControl', () => {
        const formGroup = service.createComercialEventsOfNonResidentalEstateFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
