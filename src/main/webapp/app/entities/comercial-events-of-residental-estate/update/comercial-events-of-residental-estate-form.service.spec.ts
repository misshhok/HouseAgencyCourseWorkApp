import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../comercial-events-of-residental-estate.test-samples';

import { ComercialEventsOfResidentalEstateFormService } from './comercial-events-of-residental-estate-form.service';

describe('ComercialEventsOfResidentalEstate Form Service', () => {
  let service: ComercialEventsOfResidentalEstateFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComercialEventsOfResidentalEstateFormService);
  });

  describe('Service methods', () => {
    describe('createComercialEventsOfResidentalEstateFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createComercialEventsOfResidentalEstateFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            agentNotes: expect.any(Object),
            dateOfEvent: expect.any(Object),
            typeOfCommercialEventId: expect.any(Object),
            clientId: expect.any(Object),
            residentalEstateId: expect.any(Object),
          })
        );
      });

      it('passing IComercialEventsOfResidentalEstate should create a new form with FormGroup', () => {
        const formGroup = service.createComercialEventsOfResidentalEstateFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            agentNotes: expect.any(Object),
            dateOfEvent: expect.any(Object),
            typeOfCommercialEventId: expect.any(Object),
            clientId: expect.any(Object),
            residentalEstateId: expect.any(Object),
          })
        );
      });
    });

    describe('getComercialEventsOfResidentalEstate', () => {
      it('should return NewComercialEventsOfResidentalEstate for default ComercialEventsOfResidentalEstate initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createComercialEventsOfResidentalEstateFormGroup(sampleWithNewData);

        const comercialEventsOfResidentalEstate = service.getComercialEventsOfResidentalEstate(formGroup) as any;

        expect(comercialEventsOfResidentalEstate).toMatchObject(sampleWithNewData);
      });

      it('should return NewComercialEventsOfResidentalEstate for empty ComercialEventsOfResidentalEstate initial value', () => {
        const formGroup = service.createComercialEventsOfResidentalEstateFormGroup();

        const comercialEventsOfResidentalEstate = service.getComercialEventsOfResidentalEstate(formGroup) as any;

        expect(comercialEventsOfResidentalEstate).toMatchObject({});
      });

      it('should return IComercialEventsOfResidentalEstate', () => {
        const formGroup = service.createComercialEventsOfResidentalEstateFormGroup(sampleWithRequiredData);

        const comercialEventsOfResidentalEstate = service.getComercialEventsOfResidentalEstate(formGroup) as any;

        expect(comercialEventsOfResidentalEstate).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IComercialEventsOfResidentalEstate should not enable id FormControl', () => {
        const formGroup = service.createComercialEventsOfResidentalEstateFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewComercialEventsOfResidentalEstate should disable id FormControl', () => {
        const formGroup = service.createComercialEventsOfResidentalEstateFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
