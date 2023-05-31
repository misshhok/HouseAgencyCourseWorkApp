import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TypesOfCommercialEventsFormService } from './types-of-commercial-events-form.service';
import { TypesOfCommercialEventsService } from '../service/types-of-commercial-events.service';
import { ITypesOfCommercialEvents } from '../types-of-commercial-events.model';

import { TypesOfCommercialEventsUpdateComponent } from './types-of-commercial-events-update.component';

describe('TypesOfCommercialEvents Management Update Component', () => {
  let comp: TypesOfCommercialEventsUpdateComponent;
  let fixture: ComponentFixture<TypesOfCommercialEventsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let typesOfCommercialEventsFormService: TypesOfCommercialEventsFormService;
  let typesOfCommercialEventsService: TypesOfCommercialEventsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TypesOfCommercialEventsUpdateComponent],
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
      .overrideTemplate(TypesOfCommercialEventsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypesOfCommercialEventsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    typesOfCommercialEventsFormService = TestBed.inject(TypesOfCommercialEventsFormService);
    typesOfCommercialEventsService = TestBed.inject(TypesOfCommercialEventsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const typesOfCommercialEvents: ITypesOfCommercialEvents = { id: 456 };

      activatedRoute.data = of({ typesOfCommercialEvents });
      comp.ngOnInit();

      expect(comp.typesOfCommercialEvents).toEqual(typesOfCommercialEvents);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypesOfCommercialEvents>>();
      const typesOfCommercialEvents = { id: 123 };
      jest.spyOn(typesOfCommercialEventsFormService, 'getTypesOfCommercialEvents').mockReturnValue(typesOfCommercialEvents);
      jest.spyOn(typesOfCommercialEventsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typesOfCommercialEvents });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typesOfCommercialEvents }));
      saveSubject.complete();

      // THEN
      expect(typesOfCommercialEventsFormService.getTypesOfCommercialEvents).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(typesOfCommercialEventsService.update).toHaveBeenCalledWith(expect.objectContaining(typesOfCommercialEvents));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypesOfCommercialEvents>>();
      const typesOfCommercialEvents = { id: 123 };
      jest.spyOn(typesOfCommercialEventsFormService, 'getTypesOfCommercialEvents').mockReturnValue({ id: null });
      jest.spyOn(typesOfCommercialEventsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typesOfCommercialEvents: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typesOfCommercialEvents }));
      saveSubject.complete();

      // THEN
      expect(typesOfCommercialEventsFormService.getTypesOfCommercialEvents).toHaveBeenCalled();
      expect(typesOfCommercialEventsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypesOfCommercialEvents>>();
      const typesOfCommercialEvents = { id: 123 };
      jest.spyOn(typesOfCommercialEventsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typesOfCommercialEvents });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(typesOfCommercialEventsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
