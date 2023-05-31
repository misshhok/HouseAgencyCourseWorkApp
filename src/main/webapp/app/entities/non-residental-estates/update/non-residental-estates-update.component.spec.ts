import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NonResidentalEstatesFormService } from './non-residental-estates-form.service';
import { NonResidentalEstatesService } from '../service/non-residental-estates.service';
import { INonResidentalEstates } from '../non-residental-estates.model';
import { IPurposesOfNonResidentalEstate } from 'app/entities/purposes-of-non-residental-estate/purposes-of-non-residental-estate.model';
import { PurposesOfNonResidentalEstateService } from 'app/entities/purposes-of-non-residental-estate/service/purposes-of-non-residental-estate.service';
import { IBuildingTypeOfNonResidentalEstate } from 'app/entities/building-type-of-non-residental-estate/building-type-of-non-residental-estate.model';
import { BuildingTypeOfNonResidentalEstateService } from 'app/entities/building-type-of-non-residental-estate/service/building-type-of-non-residental-estate.service';
import { IAddresses } from 'app/entities/addresses/addresses.model';
import { AddressesService } from 'app/entities/addresses/service/addresses.service';

import { NonResidentalEstatesUpdateComponent } from './non-residental-estates-update.component';

describe('NonResidentalEstates Management Update Component', () => {
  let comp: NonResidentalEstatesUpdateComponent;
  let fixture: ComponentFixture<NonResidentalEstatesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nonResidentalEstatesFormService: NonResidentalEstatesFormService;
  let nonResidentalEstatesService: NonResidentalEstatesService;
  let purposesOfNonResidentalEstateService: PurposesOfNonResidentalEstateService;
  let buildingTypeOfNonResidentalEstateService: BuildingTypeOfNonResidentalEstateService;
  let addressesService: AddressesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NonResidentalEstatesUpdateComponent],
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
      .overrideTemplate(NonResidentalEstatesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NonResidentalEstatesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nonResidentalEstatesFormService = TestBed.inject(NonResidentalEstatesFormService);
    nonResidentalEstatesService = TestBed.inject(NonResidentalEstatesService);
    purposesOfNonResidentalEstateService = TestBed.inject(PurposesOfNonResidentalEstateService);
    buildingTypeOfNonResidentalEstateService = TestBed.inject(BuildingTypeOfNonResidentalEstateService);
    addressesService = TestBed.inject(AddressesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PurposesOfNonResidentalEstate query and add missing value', () => {
      const nonResidentalEstates: INonResidentalEstates = { id: 456 };
      const purposeOfNonResidentalEstateId: IPurposesOfNonResidentalEstate = { id: 40845 };
      nonResidentalEstates.purposeOfNonResidentalEstateId = purposeOfNonResidentalEstateId;

      const purposesOfNonResidentalEstateCollection: IPurposesOfNonResidentalEstate[] = [{ id: 12431 }];
      jest
        .spyOn(purposesOfNonResidentalEstateService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: purposesOfNonResidentalEstateCollection })));
      const additionalPurposesOfNonResidentalEstates = [purposeOfNonResidentalEstateId];
      const expectedCollection: IPurposesOfNonResidentalEstate[] = [
        ...additionalPurposesOfNonResidentalEstates,
        ...purposesOfNonResidentalEstateCollection,
      ];
      jest
        .spyOn(purposesOfNonResidentalEstateService, 'addPurposesOfNonResidentalEstateToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nonResidentalEstates });
      comp.ngOnInit();

      expect(purposesOfNonResidentalEstateService.query).toHaveBeenCalled();
      expect(purposesOfNonResidentalEstateService.addPurposesOfNonResidentalEstateToCollectionIfMissing).toHaveBeenCalledWith(
        purposesOfNonResidentalEstateCollection,
        ...additionalPurposesOfNonResidentalEstates.map(expect.objectContaining)
      );
      expect(comp.purposesOfNonResidentalEstatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call BuildingTypeOfNonResidentalEstate query and add missing value', () => {
      const nonResidentalEstates: INonResidentalEstates = { id: 456 };
      const buildingTypeOfNonResidentalEstateId: IBuildingTypeOfNonResidentalEstate = { id: 36063 };
      nonResidentalEstates.buildingTypeOfNonResidentalEstateId = buildingTypeOfNonResidentalEstateId;

      const buildingTypeOfNonResidentalEstateCollection: IBuildingTypeOfNonResidentalEstate[] = [{ id: 70315 }];
      jest
        .spyOn(buildingTypeOfNonResidentalEstateService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: buildingTypeOfNonResidentalEstateCollection })));
      const additionalBuildingTypeOfNonResidentalEstates = [buildingTypeOfNonResidentalEstateId];
      const expectedCollection: IBuildingTypeOfNonResidentalEstate[] = [
        ...additionalBuildingTypeOfNonResidentalEstates,
        ...buildingTypeOfNonResidentalEstateCollection,
      ];
      jest
        .spyOn(buildingTypeOfNonResidentalEstateService, 'addBuildingTypeOfNonResidentalEstateToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nonResidentalEstates });
      comp.ngOnInit();

      expect(buildingTypeOfNonResidentalEstateService.query).toHaveBeenCalled();
      expect(buildingTypeOfNonResidentalEstateService.addBuildingTypeOfNonResidentalEstateToCollectionIfMissing).toHaveBeenCalledWith(
        buildingTypeOfNonResidentalEstateCollection,
        ...additionalBuildingTypeOfNonResidentalEstates.map(expect.objectContaining)
      );
      expect(comp.buildingTypeOfNonResidentalEstatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Addresses query and add missing value', () => {
      const nonResidentalEstates: INonResidentalEstates = { id: 456 };
      const addressId: IAddresses = { id: 35747 };
      nonResidentalEstates.addressId = addressId;

      const addressesCollection: IAddresses[] = [{ id: 55766 }];
      jest.spyOn(addressesService, 'query').mockReturnValue(of(new HttpResponse({ body: addressesCollection })));
      const additionalAddresses = [addressId];
      const expectedCollection: IAddresses[] = [...additionalAddresses, ...addressesCollection];
      jest.spyOn(addressesService, 'addAddressesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nonResidentalEstates });
      comp.ngOnInit();

      expect(addressesService.query).toHaveBeenCalled();
      expect(addressesService.addAddressesToCollectionIfMissing).toHaveBeenCalledWith(
        addressesCollection,
        ...additionalAddresses.map(expect.objectContaining)
      );
      expect(comp.addressesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const nonResidentalEstates: INonResidentalEstates = { id: 456 };
      const purposeOfNonResidentalEstateId: IPurposesOfNonResidentalEstate = { id: 40141 };
      nonResidentalEstates.purposeOfNonResidentalEstateId = purposeOfNonResidentalEstateId;
      const buildingTypeOfNonResidentalEstateId: IBuildingTypeOfNonResidentalEstate = { id: 25638 };
      nonResidentalEstates.buildingTypeOfNonResidentalEstateId = buildingTypeOfNonResidentalEstateId;
      const addressId: IAddresses = { id: 79867 };
      nonResidentalEstates.addressId = addressId;

      activatedRoute.data = of({ nonResidentalEstates });
      comp.ngOnInit();

      expect(comp.purposesOfNonResidentalEstatesSharedCollection).toContain(purposeOfNonResidentalEstateId);
      expect(comp.buildingTypeOfNonResidentalEstatesSharedCollection).toContain(buildingTypeOfNonResidentalEstateId);
      expect(comp.addressesSharedCollection).toContain(addressId);
      expect(comp.nonResidentalEstates).toEqual(nonResidentalEstates);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INonResidentalEstates>>();
      const nonResidentalEstates = { id: 123 };
      jest.spyOn(nonResidentalEstatesFormService, 'getNonResidentalEstates').mockReturnValue(nonResidentalEstates);
      jest.spyOn(nonResidentalEstatesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nonResidentalEstates });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nonResidentalEstates }));
      saveSubject.complete();

      // THEN
      expect(nonResidentalEstatesFormService.getNonResidentalEstates).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(nonResidentalEstatesService.update).toHaveBeenCalledWith(expect.objectContaining(nonResidentalEstates));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INonResidentalEstates>>();
      const nonResidentalEstates = { id: 123 };
      jest.spyOn(nonResidentalEstatesFormService, 'getNonResidentalEstates').mockReturnValue({ id: null });
      jest.spyOn(nonResidentalEstatesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nonResidentalEstates: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nonResidentalEstates }));
      saveSubject.complete();

      // THEN
      expect(nonResidentalEstatesFormService.getNonResidentalEstates).toHaveBeenCalled();
      expect(nonResidentalEstatesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INonResidentalEstates>>();
      const nonResidentalEstates = { id: 123 };
      jest.spyOn(nonResidentalEstatesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nonResidentalEstates });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nonResidentalEstatesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePurposesOfNonResidentalEstate', () => {
      it('Should forward to purposesOfNonResidentalEstateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(purposesOfNonResidentalEstateService, 'comparePurposesOfNonResidentalEstate');
        comp.comparePurposesOfNonResidentalEstate(entity, entity2);
        expect(purposesOfNonResidentalEstateService.comparePurposesOfNonResidentalEstate).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareBuildingTypeOfNonResidentalEstate', () => {
      it('Should forward to buildingTypeOfNonResidentalEstateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(buildingTypeOfNonResidentalEstateService, 'compareBuildingTypeOfNonResidentalEstate');
        comp.compareBuildingTypeOfNonResidentalEstate(entity, entity2);
        expect(buildingTypeOfNonResidentalEstateService.compareBuildingTypeOfNonResidentalEstate).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAddresses', () => {
      it('Should forward to addressesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(addressesService, 'compareAddresses');
        comp.compareAddresses(entity, entity2);
        expect(addressesService.compareAddresses).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
