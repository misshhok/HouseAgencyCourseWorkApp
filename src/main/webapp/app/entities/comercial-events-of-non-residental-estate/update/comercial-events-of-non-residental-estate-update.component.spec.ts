import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ComercialEventsOfNonResidentalEstateFormService } from './comercial-events-of-non-residental-estate-form.service';
import { ComercialEventsOfNonResidentalEstateService } from '../service/comercial-events-of-non-residental-estate.service';
import { IComercialEventsOfNonResidentalEstate } from '../comercial-events-of-non-residental-estate.model';
import { ITypesOfCommercialEvents } from 'app/entities/types-of-commercial-events/types-of-commercial-events.model';
import { TypesOfCommercialEventsService } from 'app/entities/types-of-commercial-events/service/types-of-commercial-events.service';
import { INonResidentalEstates } from 'app/entities/non-residental-estates/non-residental-estates.model';
import { NonResidentalEstatesService } from 'app/entities/non-residental-estates/service/non-residental-estates.service';
import { IClients } from 'app/entities/clients/clients.model';
import { ClientsService } from 'app/entities/clients/service/clients.service';

import { ComercialEventsOfNonResidentalEstateUpdateComponent } from './comercial-events-of-non-residental-estate-update.component';

describe('ComercialEventsOfNonResidentalEstate Management Update Component', () => {
  let comp: ComercialEventsOfNonResidentalEstateUpdateComponent;
  let fixture: ComponentFixture<ComercialEventsOfNonResidentalEstateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let comercialEventsOfNonResidentalEstateFormService: ComercialEventsOfNonResidentalEstateFormService;
  let comercialEventsOfNonResidentalEstateService: ComercialEventsOfNonResidentalEstateService;
  let typesOfCommercialEventsService: TypesOfCommercialEventsService;
  let nonResidentalEstatesService: NonResidentalEstatesService;
  let clientsService: ClientsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ComercialEventsOfNonResidentalEstateUpdateComponent],
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
      .overrideTemplate(ComercialEventsOfNonResidentalEstateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ComercialEventsOfNonResidentalEstateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    comercialEventsOfNonResidentalEstateFormService = TestBed.inject(ComercialEventsOfNonResidentalEstateFormService);
    comercialEventsOfNonResidentalEstateService = TestBed.inject(ComercialEventsOfNonResidentalEstateService);
    typesOfCommercialEventsService = TestBed.inject(TypesOfCommercialEventsService);
    nonResidentalEstatesService = TestBed.inject(NonResidentalEstatesService);
    clientsService = TestBed.inject(ClientsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TypesOfCommercialEvents query and add missing value', () => {
      const comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate = { id: 456 };
      const typeOfCommercialEventId: ITypesOfCommercialEvents = { id: 15044 };
      comercialEventsOfNonResidentalEstate.typeOfCommercialEventId = typeOfCommercialEventId;

      const typesOfCommercialEventsCollection: ITypesOfCommercialEvents[] = [{ id: 78190 }];
      jest
        .spyOn(typesOfCommercialEventsService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: typesOfCommercialEventsCollection })));
      const additionalTypesOfCommercialEvents = [typeOfCommercialEventId];
      const expectedCollection: ITypesOfCommercialEvents[] = [...additionalTypesOfCommercialEvents, ...typesOfCommercialEventsCollection];
      jest.spyOn(typesOfCommercialEventsService, 'addTypesOfCommercialEventsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ comercialEventsOfNonResidentalEstate });
      comp.ngOnInit();

      expect(typesOfCommercialEventsService.query).toHaveBeenCalled();
      expect(typesOfCommercialEventsService.addTypesOfCommercialEventsToCollectionIfMissing).toHaveBeenCalledWith(
        typesOfCommercialEventsCollection,
        ...additionalTypesOfCommercialEvents.map(expect.objectContaining)
      );
      expect(comp.typesOfCommercialEventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NonResidentalEstates query and add missing value', () => {
      const comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate = { id: 456 };
      const nonResidentalEstateId: INonResidentalEstates = { id: 5504 };
      comercialEventsOfNonResidentalEstate.nonResidentalEstateId = nonResidentalEstateId;

      const nonResidentalEstatesCollection: INonResidentalEstates[] = [{ id: 58199 }];
      jest.spyOn(nonResidentalEstatesService, 'query').mockReturnValue(of(new HttpResponse({ body: nonResidentalEstatesCollection })));
      const additionalNonResidentalEstates = [nonResidentalEstateId];
      const expectedCollection: INonResidentalEstates[] = [...additionalNonResidentalEstates, ...nonResidentalEstatesCollection];
      jest.spyOn(nonResidentalEstatesService, 'addNonResidentalEstatesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ comercialEventsOfNonResidentalEstate });
      comp.ngOnInit();

      expect(nonResidentalEstatesService.query).toHaveBeenCalled();
      expect(nonResidentalEstatesService.addNonResidentalEstatesToCollectionIfMissing).toHaveBeenCalledWith(
        nonResidentalEstatesCollection,
        ...additionalNonResidentalEstates.map(expect.objectContaining)
      );
      expect(comp.nonResidentalEstatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Clients query and add missing value', () => {
      const comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate = { id: 456 };
      const clientId: IClients = { id: 83011 };
      comercialEventsOfNonResidentalEstate.clientId = clientId;

      const clientsCollection: IClients[] = [{ id: 19601 }];
      jest.spyOn(clientsService, 'query').mockReturnValue(of(new HttpResponse({ body: clientsCollection })));
      const additionalClients = [clientId];
      const expectedCollection: IClients[] = [...additionalClients, ...clientsCollection];
      jest.spyOn(clientsService, 'addClientsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ comercialEventsOfNonResidentalEstate });
      comp.ngOnInit();

      expect(clientsService.query).toHaveBeenCalled();
      expect(clientsService.addClientsToCollectionIfMissing).toHaveBeenCalledWith(
        clientsCollection,
        ...additionalClients.map(expect.objectContaining)
      );
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate = { id: 456 };
      const typeOfCommercialEventId: ITypesOfCommercialEvents = { id: 48569 };
      comercialEventsOfNonResidentalEstate.typeOfCommercialEventId = typeOfCommercialEventId;
      const nonResidentalEstateId: INonResidentalEstates = { id: 18730 };
      comercialEventsOfNonResidentalEstate.nonResidentalEstateId = nonResidentalEstateId;
      const clientId: IClients = { id: 36528 };
      comercialEventsOfNonResidentalEstate.clientId = clientId;

      activatedRoute.data = of({ comercialEventsOfNonResidentalEstate });
      comp.ngOnInit();

      expect(comp.typesOfCommercialEventsSharedCollection).toContain(typeOfCommercialEventId);
      expect(comp.nonResidentalEstatesSharedCollection).toContain(nonResidentalEstateId);
      expect(comp.clientsSharedCollection).toContain(clientId);
      expect(comp.comercialEventsOfNonResidentalEstate).toEqual(comercialEventsOfNonResidentalEstate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComercialEventsOfNonResidentalEstate>>();
      const comercialEventsOfNonResidentalEstate = { id: 123 };
      jest
        .spyOn(comercialEventsOfNonResidentalEstateFormService, 'getComercialEventsOfNonResidentalEstate')
        .mockReturnValue(comercialEventsOfNonResidentalEstate);
      jest.spyOn(comercialEventsOfNonResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comercialEventsOfNonResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comercialEventsOfNonResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(comercialEventsOfNonResidentalEstateFormService.getComercialEventsOfNonResidentalEstate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(comercialEventsOfNonResidentalEstateService.update).toHaveBeenCalledWith(
        expect.objectContaining(comercialEventsOfNonResidentalEstate)
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComercialEventsOfNonResidentalEstate>>();
      const comercialEventsOfNonResidentalEstate = { id: 123 };
      jest.spyOn(comercialEventsOfNonResidentalEstateFormService, 'getComercialEventsOfNonResidentalEstate').mockReturnValue({ id: null });
      jest.spyOn(comercialEventsOfNonResidentalEstateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comercialEventsOfNonResidentalEstate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comercialEventsOfNonResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(comercialEventsOfNonResidentalEstateFormService.getComercialEventsOfNonResidentalEstate).toHaveBeenCalled();
      expect(comercialEventsOfNonResidentalEstateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComercialEventsOfNonResidentalEstate>>();
      const comercialEventsOfNonResidentalEstate = { id: 123 };
      jest.spyOn(comercialEventsOfNonResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comercialEventsOfNonResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(comercialEventsOfNonResidentalEstateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTypesOfCommercialEvents', () => {
      it('Should forward to typesOfCommercialEventsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(typesOfCommercialEventsService, 'compareTypesOfCommercialEvents');
        comp.compareTypesOfCommercialEvents(entity, entity2);
        expect(typesOfCommercialEventsService.compareTypesOfCommercialEvents).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareNonResidentalEstates', () => {
      it('Should forward to nonResidentalEstatesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(nonResidentalEstatesService, 'compareNonResidentalEstates');
        comp.compareNonResidentalEstates(entity, entity2);
        expect(nonResidentalEstatesService.compareNonResidentalEstates).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareClients', () => {
      it('Should forward to clientsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(clientsService, 'compareClients');
        comp.compareClients(entity, entity2);
        expect(clientsService.compareClients).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
