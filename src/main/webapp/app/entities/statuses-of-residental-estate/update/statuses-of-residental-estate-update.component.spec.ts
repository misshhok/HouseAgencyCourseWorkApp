import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StatusesOfResidentalEstateFormService } from './statuses-of-residental-estate-form.service';
import { StatusesOfResidentalEstateService } from '../service/statuses-of-residental-estate.service';
import { IStatusesOfResidentalEstate } from '../statuses-of-residental-estate.model';

import { StatusesOfResidentalEstateUpdateComponent } from './statuses-of-residental-estate-update.component';

describe('StatusesOfResidentalEstate Management Update Component', () => {
  let comp: StatusesOfResidentalEstateUpdateComponent;
  let fixture: ComponentFixture<StatusesOfResidentalEstateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let statusesOfResidentalEstateFormService: StatusesOfResidentalEstateFormService;
  let statusesOfResidentalEstateService: StatusesOfResidentalEstateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StatusesOfResidentalEstateUpdateComponent],
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
      .overrideTemplate(StatusesOfResidentalEstateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StatusesOfResidentalEstateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    statusesOfResidentalEstateFormService = TestBed.inject(StatusesOfResidentalEstateFormService);
    statusesOfResidentalEstateService = TestBed.inject(StatusesOfResidentalEstateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const statusesOfResidentalEstate: IStatusesOfResidentalEstate = { id: 456 };

      activatedRoute.data = of({ statusesOfResidentalEstate });
      comp.ngOnInit();

      expect(comp.statusesOfResidentalEstate).toEqual(statusesOfResidentalEstate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatusesOfResidentalEstate>>();
      const statusesOfResidentalEstate = { id: 123 };
      jest.spyOn(statusesOfResidentalEstateFormService, 'getStatusesOfResidentalEstate').mockReturnValue(statusesOfResidentalEstate);
      jest.spyOn(statusesOfResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statusesOfResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statusesOfResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(statusesOfResidentalEstateFormService.getStatusesOfResidentalEstate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(statusesOfResidentalEstateService.update).toHaveBeenCalledWith(expect.objectContaining(statusesOfResidentalEstate));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatusesOfResidentalEstate>>();
      const statusesOfResidentalEstate = { id: 123 };
      jest.spyOn(statusesOfResidentalEstateFormService, 'getStatusesOfResidentalEstate').mockReturnValue({ id: null });
      jest.spyOn(statusesOfResidentalEstateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statusesOfResidentalEstate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statusesOfResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(statusesOfResidentalEstateFormService.getStatusesOfResidentalEstate).toHaveBeenCalled();
      expect(statusesOfResidentalEstateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatusesOfResidentalEstate>>();
      const statusesOfResidentalEstate = { id: 123 };
      jest.spyOn(statusesOfResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statusesOfResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(statusesOfResidentalEstateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
