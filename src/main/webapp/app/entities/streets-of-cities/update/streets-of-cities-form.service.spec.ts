import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../streets-of-cities.test-samples';

import { StreetsOfCitiesFormService } from './streets-of-cities-form.service';

describe('StreetsOfCities Form Service', () => {
  let service: StreetsOfCitiesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StreetsOfCitiesFormService);
  });

  describe('Service methods', () => {
    describe('createStreetsOfCitiesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStreetsOfCitiesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            cityId: expect.any(Object),
          })
        );
      });

      it('passing IStreetsOfCities should create a new form with FormGroup', () => {
        const formGroup = service.createStreetsOfCitiesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            cityId: expect.any(Object),
          })
        );
      });
    });

    describe('getStreetsOfCities', () => {
      it('should return NewStreetsOfCities for default StreetsOfCities initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createStreetsOfCitiesFormGroup(sampleWithNewData);

        const streetsOfCities = service.getStreetsOfCities(formGroup) as any;

        expect(streetsOfCities).toMatchObject(sampleWithNewData);
      });

      it('should return NewStreetsOfCities for empty StreetsOfCities initial value', () => {
        const formGroup = service.createStreetsOfCitiesFormGroup();

        const streetsOfCities = service.getStreetsOfCities(formGroup) as any;

        expect(streetsOfCities).toMatchObject({});
      });

      it('should return IStreetsOfCities', () => {
        const formGroup = service.createStreetsOfCitiesFormGroup(sampleWithRequiredData);

        const streetsOfCities = service.getStreetsOfCities(formGroup) as any;

        expect(streetsOfCities).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStreetsOfCities should not enable id FormControl', () => {
        const formGroup = service.createStreetsOfCitiesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStreetsOfCities should disable id FormControl', () => {
        const formGroup = service.createStreetsOfCitiesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
