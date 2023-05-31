import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AddressesFormService } from './addresses-form.service';
import { AddressesService } from '../service/addresses.service';
import { IAddresses } from '../addresses.model';
import { IStreetsOfCities } from 'app/entities/streets-of-cities/streets-of-cities.model';
import { StreetsOfCitiesService } from 'app/entities/streets-of-cities/service/streets-of-cities.service';

import { AddressesUpdateComponent } from './addresses-update.component';

describe('Addresses Management Update Component', () => {
  let comp: AddressesUpdateComponent;
  let fixture: ComponentFixture<AddressesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let addressesFormService: AddressesFormService;
  let addressesService: AddressesService;
  let streetsOfCitiesService: StreetsOfCitiesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AddressesUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AddressesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AddressesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    addressesFormService = TestBed.inject(AddressesFormService);
    addressesService = TestBed.inject(AddressesService);
    streetsOfCitiesService = TestBed.inject(StreetsOfCitiesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call StreetsOfCities query and add missing value', () => {
      const addresses: IAddresses = { id: 456 };
      const streetOfCityId: IStreetsOfCities = { id: 92183 };
      addresses.streetOfCityId = streetOfCityId;

      const streetsOfCitiesCollection: IStreetsOfCities[] = [{ id: 15230 }];
      jest.spyOn(streetsOfCitiesService, 'query').mockReturnValue(of(new HttpResponse({ body: streetsOfCitiesCollection })));
      const additionalStreetsOfCities = [streetOfCityId];
      const expectedCollection: IStreetsOfCities[] = [...additionalStreetsOfCities, ...streetsOfCitiesCollection];
      jest.spyOn(streetsOfCitiesService, 'addStreetsOfCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ addresses });
      comp.ngOnInit();

      expect(streetsOfCitiesService.query).toHaveBeenCalled();
      expect(streetsOfCitiesService.addStreetsOfCitiesToCollectionIfMissing).toHaveBeenCalledWith(
        streetsOfCitiesCollection,
        ...additionalStreetsOfCities.map(expect.objectContaining)
      );
      expect(comp.streetsOfCitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const addresses: IAddresses = { id: 456 };
      const streetOfCityId: IStreetsOfCities = { id: 85210 };
      addresses.streetOfCityId = streetOfCityId;

      activatedRoute.data = of({ addresses });
      comp.ngOnInit();

      expect(comp.streetsOfCitiesSharedCollection).toContain(streetOfCityId);
      expect(comp.addresses).toEqual(addresses);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddresses>>();
      const addresses = { id: 123 };
      jest.spyOn(addressesFormService, 'getAddresses').mockReturnValue(addresses);
      jest.spyOn(addressesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addresses });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addresses }));
      saveSubject.complete();

      // THEN
      expect(addressesFormService.getAddresses).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(addressesService.update).toHaveBeenCalledWith(expect.objectContaining(addresses));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddresses>>();
      const addresses = { id: 123 };
      jest.spyOn(addressesFormService, 'getAddresses').mockReturnValue({ id: null });
      jest.spyOn(addressesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addresses: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addresses }));
      saveSubject.complete();

      // THEN
      expect(addressesFormService.getAddresses).toHaveBeenCalled();
      expect(addressesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddresses>>();
      const addresses = { id: 123 };
      jest.spyOn(addressesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addresses });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(addressesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareStreetsOfCities', () => {
      it('Should forward to streetsOfCitiesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(streetsOfCitiesService, 'compareStreetsOfCities');
        comp.compareStreetsOfCities(entity, entity2);
        expect(streetsOfCitiesService.compareStreetsOfCities).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
