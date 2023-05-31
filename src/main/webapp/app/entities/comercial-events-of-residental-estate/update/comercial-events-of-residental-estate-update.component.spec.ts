import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ComercialEventsOfResidentalEstateFormService } from './comercial-events-of-residental-estate-form.service';
import { ComercialEventsOfResidentalEstateService } from '../service/comercial-events-of-residental-estate.service';
import { IComercialEventsOfResidentalEstate } from '../comercial-events-of-residental-estate.model';
import { ITypesOfCommercialEvents } from 'app/entities/types-of-commercial-events/types-of-commercial-events.model';
import { TypesOfCommercialEventsService } from 'app/entities/types-of-commercial-events/service/types-of-commercial-events.service';
import { IClients } from 'app/entities/clients/clients.model';
import { ClientsService } from 'app/entities/clients/service/clients.service';
import { IResidentalEstates } from 'app/entities/residental-estates/residental-estates.model';
import { ResidentalEstatesService } from 'app/entities/residental-estates/service/residental-estates.service';

import { ComercialEventsOfResidentalEstateUpdateComponent } from './comercial-events-of-residental-estate-update.component';

describe('ComercialEventsOfResidentalEstate Management Update Component', () => {
  let comp: ComercialEventsOfResidentalEstateUpdateComponent;
  let fixture: ComponentFixture<ComercialEventsOfResidentalEstateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let comercialEventsOfResidentalEstateFormService: ComercialEventsOfResidentalEstateFormService;
  let comercialEventsOfResidentalEstateService: ComercialEventsOfResidentalEstateService;
  let typesOfCommercialEventsService: TypesOfCommercialEventsService;
  let clientsService: ClientsService;
  let residentalEstatesService: ResidentalEstatesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ComercialEventsOfResidentalEstateUpdateComponent],
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
      .overrideTemplate(ComercialEventsOfResidentalEstateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ComercialEventsOfResidentalEstateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    comercialEventsOfResidentalEstateFormService = TestBed.inject(ComercialEventsOfResidentalEstateFormService);
    comercialEventsOfResidentalEstateService = TestBed.inject(ComercialEventsOfResidentalEstateService);
    typesOfCommercialEventsService = TestBed.inject(TypesOfCommercialEventsService);
    clientsService = TestBed.inject(ClientsService);
    residentalEstatesService = TestBed.inject(ResidentalEstatesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TypesOfCommercialEvents query and add missing value', () => {
      const comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate = { id: 456 };
      const typeOfCommercialEventId: ITypesOfCommercialEvents = { id: 45364 };
      comercialEventsOfResidentalEstate.typeOfCommercialEventId = typeOfCommercialEventId;

      const typesOfCommercialEventsCollection: ITypesOfCommercialEvents[] = [{ id: 50244 }];
      jest
        .spyOn(typesOfCommercialEventsService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: typesOfCommercialEventsCollection })));
      const additionalTypesOfCommercialEvents = [typeOfCommercialEventId];
      const expectedCollection: ITypesOfCommercialEvents[] = [...additionalTypesOfCommercialEvents, ...typesOfCommercialEventsCollection];
      jest.spyOn(typesOfCommercialEventsService, 'addTypesOfCommercialEventsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ comercialEventsOfResidentalEstate });
      comp.ngOnInit();

      expect(typesOfCommercialEventsService.query).toHaveBeenCalled();
      expect(typesOfCommercialEventsService.addTypesOfCommercialEventsToCollectionIfMissing).toHaveBeenCalledWith(
        typesOfCommercialEventsCollection,
        ...additionalTypesOfCommercialEvents.map(expect.objectContaining)
      );
      expect(comp.typesOfCommercialEventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Clients query and add missing value', () => {
      const comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate = { id: 456 };
      const clientId: IClients = { id: 39495 };
      comercialEventsOfResidentalEstate.clientId = clientId;

      const clientsCollection: IClients[] = [{ id: 45455 }];
      jest.spyOn(clientsService, 'query').mockReturnValue(of(new HttpResponse({ body: clientsCollection })));
      const additionalClients = [clientId];
      const expectedCollection: IClients[] = [...additionalClients, ...clientsCollection];
      jest.spyOn(clientsService, 'addClientsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ comercialEventsOfResidentalEstate });
      comp.ngOnInit();

      expect(clientsService.query).toHaveBeenCalled();
      expect(clientsService.addClientsToCollectionIfMissing).toHaveBeenCalledWith(
        clientsCollection,
        ...additionalClients.map(expect.objectContaining)
      );
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResidentalEstates query and add missing value', () => {
      const comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate = { id: 456 };
      const residentalEstateId: IResidentalEstates = { id: 65548 };
      comercialEventsOfResidentalEstate.residentalEstateId = residentalEstateId;

      const residentalEstatesCollection: IResidentalEstates[] = [{ id: 1704 }];
      jest.spyOn(residentalEstatesService, 'query').mockReturnValue(of(new HttpResponse({ body: residentalEstatesCollection })));
      const additionalResidentalEstates = [residentalEstateId];
      const expectedCollection: IResidentalEstates[] = [...additionalResidentalEstates, ...residentalEstatesCollection];
      jest.spyOn(residentalEstatesService, 'addResidentalEstatesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ comercialEventsOfResidentalEstate });
      comp.ngOnInit();

      expect(residentalEstatesService.query).toHaveBeenCalled();
      expect(residentalEstatesService.addResidentalEstatesToCollectionIfMissing).toHaveBeenCalledWith(
        residentalEstatesCollection,
        ...additionalResidentalEstates.map(expect.objectContaining)
      );
      expect(comp.residentalEstatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate = { id: 456 };
      const typeOfCommercialEventId: ITypesOfCommercialEvents = { id: 71110 };
      comercialEventsOfResidentalEstate.typeOfCommercialEventId = typeOfCommercialEventId;
      const clientId: IClients = { id: 39255 };
      comercialEventsOfResidentalEstate.clientId = clientId;
      const residentalEstateId: IResidentalEstates = { id: 96268 };
      comercialEventsOfResidentalEstate.residentalEstateId = residentalEstateId;

      activatedRoute.data = of({ comercialEventsOfResidentalEstate });
      comp.ngOnInit();

      expect(comp.typesOfCommercialEventsSharedCollection).toContain(typeOfCommercialEventId);
      expect(comp.clientsSharedCollection).toContain(clientId);
      expect(comp.residentalEstatesSharedCollection).toContain(residentalEstateId);
      expect(comp.comercialEventsOfResidentalEstate).toEqual(comercialEventsOfResidentalEstate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComercialEventsOfResidentalEstate>>();
      const comercialEventsOfResidentalEstate = { id: 123 };
      jest
        .spyOn(comercialEventsOfResidentalEstateFormService, 'getComercialEventsOfResidentalEstate')
        .mockReturnValue(comercialEventsOfResidentalEstate);
      jest.spyOn(comercialEventsOfResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comercialEventsOfResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comercialEventsOfResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(comercialEventsOfResidentalEstateFormService.getComercialEventsOfResidentalEstate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(comercialEventsOfResidentalEstateService.update).toHaveBeenCalledWith(
        expect.objectContaining(comercialEventsOfResidentalEstate)
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComercialEventsOfResidentalEstate>>();
      const comercialEventsOfResidentalEstate = { id: 123 };
      jest.spyOn(comercialEventsOfResidentalEstateFormService, 'getComercialEventsOfResidentalEstate').mockReturnValue({ id: null });
      jest.spyOn(comercialEventsOfResidentalEstateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comercialEventsOfResidentalEstate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comercialEventsOfResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(comercialEventsOfResidentalEstateFormService.getComercialEventsOfResidentalEstate).toHaveBeenCalled();
      expect(comercialEventsOfResidentalEstateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComercialEventsOfResidentalEstate>>();
      const comercialEventsOfResidentalEstate = { id: 123 };
      jest.spyOn(comercialEventsOfResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comercialEventsOfResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(comercialEventsOfResidentalEstateService.update).toHaveBeenCalled();
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

    describe('compareClients', () => {
      it('Should forward to clientsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(clientsService, 'compareClients');
        comp.compareClients(entity, entity2);
        expect(clientsService.compareClients).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareResidentalEstates', () => {
      it('Should forward to residentalEstatesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(residentalEstatesService, 'compareResidentalEstates');
        comp.compareResidentalEstates(entity, entity2);
        expect(residentalEstatesService.compareResidentalEstates).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
