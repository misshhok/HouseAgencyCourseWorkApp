import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PurposesOfNonResidentalEstateFormService } from './purposes-of-non-residental-estate-form.service';
import { PurposesOfNonResidentalEstateService } from '../service/purposes-of-non-residental-estate.service';
import { IPurposesOfNonResidentalEstate } from '../purposes-of-non-residental-estate.model';

import { PurposesOfNonResidentalEstateUpdateComponent } from './purposes-of-non-residental-estate-update.component';

describe('PurposesOfNonResidentalEstate Management Update Component', () => {
  let comp: PurposesOfNonResidentalEstateUpdateComponent;
  let fixture: ComponentFixture<PurposesOfNonResidentalEstateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let purposesOfNonResidentalEstateFormService: PurposesOfNonResidentalEstateFormService;
  let purposesOfNonResidentalEstateService: PurposesOfNonResidentalEstateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PurposesOfNonResidentalEstateUpdateComponent],
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
      .overrideTemplate(PurposesOfNonResidentalEstateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PurposesOfNonResidentalEstateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    purposesOfNonResidentalEstateFormService = TestBed.inject(PurposesOfNonResidentalEstateFormService);
    purposesOfNonResidentalEstateService = TestBed.inject(PurposesOfNonResidentalEstateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const purposesOfNonResidentalEstate: IPurposesOfNonResidentalEstate = { id: 456 };

      activatedRoute.data = of({ purposesOfNonResidentalEstate });
      comp.ngOnInit();

      expect(comp.purposesOfNonResidentalEstate).toEqual(purposesOfNonResidentalEstate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPurposesOfNonResidentalEstate>>();
      const purposesOfNonResidentalEstate = { id: 123 };
      jest
        .spyOn(purposesOfNonResidentalEstateFormService, 'getPurposesOfNonResidentalEstate')
        .mockReturnValue(purposesOfNonResidentalEstate);
      jest.spyOn(purposesOfNonResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purposesOfNonResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: purposesOfNonResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(purposesOfNonResidentalEstateFormService.getPurposesOfNonResidentalEstate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(purposesOfNonResidentalEstateService.update).toHaveBeenCalledWith(expect.objectContaining(purposesOfNonResidentalEstate));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPurposesOfNonResidentalEstate>>();
      const purposesOfNonResidentalEstate = { id: 123 };
      jest.spyOn(purposesOfNonResidentalEstateFormService, 'getPurposesOfNonResidentalEstate').mockReturnValue({ id: null });
      jest.spyOn(purposesOfNonResidentalEstateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purposesOfNonResidentalEstate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: purposesOfNonResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(purposesOfNonResidentalEstateFormService.getPurposesOfNonResidentalEstate).toHaveBeenCalled();
      expect(purposesOfNonResidentalEstateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPurposesOfNonResidentalEstate>>();
      const purposesOfNonResidentalEstate = { id: 123 };
      jest.spyOn(purposesOfNonResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purposesOfNonResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(purposesOfNonResidentalEstateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
