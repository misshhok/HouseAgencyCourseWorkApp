import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../types-of-commercial-events.test-samples';

import { TypesOfCommercialEventsFormService } from './types-of-commercial-events-form.service';

describe('TypesOfCommercialEvents Form Service', () => {
  let service: TypesOfCommercialEventsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypesOfCommercialEventsFormService);
  });

  describe('Service methods', () => {
    describe('createTypesOfCommercialEventsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTypesOfCommercialEventsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            description: expect.any(Object),
            estateType: expect.any(Object),
          })
        );
      });

      it('passing ITypesOfCommercialEvents should create a new form with FormGroup', () => {
        const formGroup = service.createTypesOfCommercialEventsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            description: expect.any(Object),
            estateType: expect.any(Object),
          })
        );
      });
    });

    describe('getTypesOfCommercialEvents', () => {
      it('should return NewTypesOfCommercialEvents for default TypesOfCommercialEvents initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTypesOfCommercialEventsFormGroup(sampleWithNewData);

        const typesOfCommercialEvents = service.getTypesOfCommercialEvents(formGroup) as any;

        expect(typesOfCommercialEvents).toMatchObject(sampleWithNewData);
      });

      it('should return NewTypesOfCommercialEvents for empty TypesOfCommercialEvents initial value', () => {
        const formGroup = service.createTypesOfCommercialEventsFormGroup();

        const typesOfCommercialEvents = service.getTypesOfCommercialEvents(formGroup) as any;

        expect(typesOfCommercialEvents).toMatchObject({});
      });

      it('should return ITypesOfCommercialEvents', () => {
        const formGroup = service.createTypesOfCommercialEventsFormGroup(sampleWithRequiredData);

        const typesOfCommercialEvents = service.getTypesOfCommercialEvents(formGroup) as any;

        expect(typesOfCommercialEvents).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITypesOfCommercialEvents should not enable id FormControl', () => {
        const formGroup = service.createTypesOfCommercialEventsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTypesOfCommercialEvents should disable id FormControl', () => {
        const formGroup = service.createTypesOfCommercialEventsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
