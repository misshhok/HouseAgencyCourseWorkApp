import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResidentalEstatesFormService } from './residental-estates-form.service';
import { ResidentalEstatesService } from '../service/residental-estates.service';
import { IResidentalEstates } from '../residental-estates.model';
import { IAddresses } from 'app/entities/addresses/addresses.model';
import { AddressesService } from 'app/entities/addresses/service/addresses.service';
import { ITypesOfResidentalEstate } from 'app/entities/types-of-residental-estate/types-of-residental-estate.model';
import { TypesOfResidentalEstateService } from 'app/entities/types-of-residental-estate/service/types-of-residental-estate.service';
import { IStatusesOfResidentalEstate } from 'app/entities/statuses-of-residental-estate/statuses-of-residental-estate.model';
import { StatusesOfResidentalEstateService } from 'app/entities/statuses-of-residental-estate/service/statuses-of-residental-estate.service';

import { ResidentalEstatesUpdateComponent } from './residental-estates-update.component';

describe('ResidentalEstates Management Update Component', () => {
  let comp: ResidentalEstatesUpdateComponent;
  let fixture: ComponentFixture<ResidentalEstatesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let residentalEstatesFormService: ResidentalEstatesFormService;
  let residentalEstatesService: ResidentalEstatesService;
  let addressesService: AddressesService;
  let typesOfResidentalEstateService: TypesOfResidentalEstateService;
  let statusesOfResidentalEstateService: StatusesOfResidentalEstateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResidentalEstatesUpdateComponent],
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
      .overrideTemplate(ResidentalEstatesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResidentalEstatesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    residentalEstatesFormService = TestBed.inject(ResidentalEstatesFormService);
    residentalEstatesService = TestBed.inject(ResidentalEstatesService);
    addressesService = TestBed.inject(AddressesService);
    typesOfResidentalEstateService = TestBed.inject(TypesOfResidentalEstateService);
    statusesOfResidentalEstateService = TestBed.inject(StatusesOfResidentalEstateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Addresses query and add missing value', () => {
      const residentalEstates: IResidentalEstates = { id: 456 };
      const addressId: IAddresses = { id: 98905 };
      residentalEstates.addressId = addressId;

      const addressesCollection: IAddresses[] = [{ id: 68509 }];
      jest.spyOn(addressesService, 'query').mockReturnValue(of(new HttpResponse({ body: addressesCollection })));
      const additionalAddresses = [addressId];
      const expectedCollection: IAddresses[] = [...additionalAddresses, ...addressesCollection];
      jest.spyOn(addressesService, 'addAddressesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ residentalEstates });
      comp.ngOnInit();

      expect(addressesService.query).toHaveBeenCalled();
      expect(addressesService.addAddressesToCollectionIfMissing).toHaveBeenCalledWith(
        addressesCollection,
        ...additionalAddresses.map(expect.objectContaining)
      );
      expect(comp.addressesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TypesOfResidentalEstate query and add missing value', () => {
      const residentalEstates: IResidentalEstates = { id: 456 };
      const typeOfResidentalEstateId: ITypesOfResidentalEstate = { id: 5805 };
      residentalEstates.typeOfResidentalEstateId = typeOfResidentalEstateId;

      const typesOfResidentalEstateCollection: ITypesOfResidentalEstate[] = [{ id: 1312 }];
      jest
        .spyOn(typesOfResidentalEstateService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: typesOfResidentalEstateCollection })));
      const additionalTypesOfResidentalEstates = [typeOfResidentalEstateId];
      const expectedCollection: ITypesOfResidentalEstate[] = [...additionalTypesOfResidentalEstates, ...typesOfResidentalEstateCollection];
      jest.spyOn(typesOfResidentalEstateService, 'addTypesOfResidentalEstateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ residentalEstates });
      comp.ngOnInit();

      expect(typesOfResidentalEstateService.query).toHaveBeenCalled();
      expect(typesOfResidentalEstateService.addTypesOfResidentalEstateToCollectionIfMissing).toHaveBeenCalledWith(
        typesOfResidentalEstateCollection,
        ...additionalTypesOfResidentalEstates.map(expect.objectContaining)
      );
      expect(comp.typesOfResidentalEstatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call StatusesOfResidentalEstate query and add missing value', () => {
      const residentalEstates: IResidentalEstates = { id: 456 };
      const statusOfResidentalEstateId: IStatusesOfResidentalEstate = { id: 84002 };
      residentalEstates.statusOfResidentalEstateId = statusOfResidentalEstateId;

      const statusesOfResidentalEstateCollection: IStatusesOfResidentalEstate[] = [{ id: 68436 }];
      jest
        .spyOn(statusesOfResidentalEstateService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: statusesOfResidentalEstateCollection })));
      const additionalStatusesOfResidentalEstates = [statusOfResidentalEstateId];
      const expectedCollection: IStatusesOfResidentalEstate[] = [
        ...additionalStatusesOfResidentalEstates,
        ...statusesOfResidentalEstateCollection,
      ];
      jest
        .spyOn(statusesOfResidentalEstateService, 'addStatusesOfResidentalEstateToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ residentalEstates });
      comp.ngOnInit();

      expect(statusesOfResidentalEstateService.query).toHaveBeenCalled();
      expect(statusesOfResidentalEstateService.addStatusesOfResidentalEstateToCollectionIfMissing).toHaveBeenCalledWith(
        statusesOfResidentalEstateCollection,
        ...additionalStatusesOfResidentalEstates.map(expect.objectContaining)
      );
      expect(comp.statusesOfResidentalEstatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const residentalEstates: IResidentalEstates = { id: 456 };
      const addressId: IAddresses = { id: 92263 };
      residentalEstates.addressId = addressId;
      const typeOfResidentalEstateId: ITypesOfResidentalEstate = { id: 99994 };
      residentalEstates.typeOfResidentalEstateId = typeOfResidentalEstateId;
      const statusOfResidentalEstateId: IStatusesOfResidentalEstate = { id: 19252 };
      residentalEstates.statusOfResidentalEstateId = statusOfResidentalEstateId;

      activatedRoute.data = of({ residentalEstates });
      comp.ngOnInit();

      expect(comp.addressesSharedCollection).toContain(addressId);
      expect(comp.typesOfResidentalEstatesSharedCollection).toContain(typeOfResidentalEstateId);
      expect(comp.statusesOfResidentalEstatesSharedCollection).toContain(statusOfResidentalEstateId);
      expect(comp.residentalEstates).toEqual(residentalEstates);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResidentalEstates>>();
      const residentalEstates = { id: 123 };
      jest.spyOn(residentalEstatesFormService, 'getResidentalEstates').mockReturnValue(residentalEstates);
      jest.spyOn(residentalEstatesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ residentalEstates });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: residentalEstates }));
      saveSubject.complete();

      // THEN
      expect(residentalEstatesFormService.getResidentalEstates).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(residentalEstatesService.update).toHaveBeenCalledWith(expect.objectContaining(residentalEstates));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResidentalEstates>>();
      const residentalEstates = { id: 123 };
      jest.spyOn(residentalEstatesFormService, 'getResidentalEstates').mockReturnValue({ id: null });
      jest.spyOn(residentalEstatesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ residentalEstates: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: residentalEstates }));
      saveSubject.complete();

      // THEN
      expect(residentalEstatesFormService.getResidentalEstates).toHaveBeenCalled();
      expect(residentalEstatesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResidentalEstates>>();
      const residentalEstates = { id: 123 };
      jest.spyOn(residentalEstatesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ residentalEstates });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(residentalEstatesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAddresses', () => {
      it('Should forward to addressesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(addressesService, 'compareAddresses');
        comp.compareAddresses(entity, entity2);
        expect(addressesService.compareAddresses).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTypesOfResidentalEstate', () => {
      it('Should forward to typesOfResidentalEstateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(typesOfResidentalEstateService, 'compareTypesOfResidentalEstate');
        comp.compareTypesOfResidentalEstate(entity, entity2);
        expect(typesOfResidentalEstateService.compareTypesOfResidentalEstate).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareStatusesOfResidentalEstate', () => {
      it('Should forward to statusesOfResidentalEstateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(statusesOfResidentalEstateService, 'compareStatusesOfResidentalEstate');
        comp.compareStatusesOfResidentalEstate(entity, entity2);
        expect(statusesOfResidentalEstateService.compareStatusesOfResidentalEstate).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
