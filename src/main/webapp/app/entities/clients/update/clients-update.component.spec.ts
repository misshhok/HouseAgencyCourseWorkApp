import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ClientsFormService } from './clients-form.service';
import { ClientsService } from '../service/clients.service';
import { IClients } from '../clients.model';

import { ClientsUpdateComponent } from './clients-update.component';

describe('Clients Management Update Component', () => {
  let comp: ClientsUpdateComponent;
  let fixture: ComponentFixture<ClientsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let clientsFormService: ClientsFormService;
  let clientsService: ClientsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ClientsUpdateComponent],
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
      .overrideTemplate(ClientsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClientsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    clientsFormService = TestBed.inject(ClientsFormService);
    clientsService = TestBed.inject(ClientsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const clients: IClients = { id: 456 };

      activatedRoute.data = of({ clients });
      comp.ngOnInit();

      expect(comp.clients).toEqual(clients);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClients>>();
      const clients = { id: 123 };
      jest.spyOn(clientsFormService, 'getClients').mockReturnValue(clients);
      jest.spyOn(clientsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clients });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clients }));
      saveSubject.complete();

      // THEN
      expect(clientsFormService.getClients).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(clientsService.update).toHaveBeenCalledWith(expect.objectContaining(clients));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClients>>();
      const clients = { id: 123 };
      jest.spyOn(clientsFormService, 'getClients').mockReturnValue({ id: null });
      jest.spyOn(clientsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clients: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clients }));
      saveSubject.complete();

      // THEN
      expect(clientsFormService.getClients).toHaveBeenCalled();
      expect(clientsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClients>>();
      const clients = { id: 123 };
      jest.spyOn(clientsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clients });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(clientsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
