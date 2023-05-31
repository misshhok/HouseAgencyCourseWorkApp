import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cities.test-samples';

import { CitiesFormService } from './cities-form.service';

describe('Cities Form Service', () => {
  let service: CitiesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CitiesFormService);
  });

  describe('Service methods', () => {
    describe('createCitiesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCitiesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
          })
        );
      });

      it('passing ICities should create a new form with FormGroup', () => {
        const formGroup = service.createCitiesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
          })
        );
      });
    });

    describe('getCities', () => {
      it('should return NewCities for default Cities initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCitiesFormGroup(sampleWithNewData);

        const cities = service.getCities(formGroup) as any;

        expect(cities).toMatchObject(sampleWithNewData);
      });

      it('should return NewCities for empty Cities initial value', () => {
        const formGroup = service.createCitiesFormGroup();

        const cities = service.getCities(formGroup) as any;

        expect(cities).toMatchObject({});
      });

      it('should return ICities', () => {
        const formGroup = service.createCitiesFormGroup(sampleWithRequiredData);

        const cities = service.getCities(formGroup) as any;

        expect(cities).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICities should not enable id FormControl', () => {
        const formGroup = service.createCitiesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCities should disable id FormControl', () => {
        const formGroup = service.createCitiesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
