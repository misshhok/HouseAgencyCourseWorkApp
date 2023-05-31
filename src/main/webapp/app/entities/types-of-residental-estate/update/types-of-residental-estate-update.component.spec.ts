import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TypesOfResidentalEstateFormService } from './types-of-residental-estate-form.service';
import { TypesOfResidentalEstateService } from '../service/types-of-residental-estate.service';
import { ITypesOfResidentalEstate } from '../types-of-residental-estate.model';

import { TypesOfResidentalEstateUpdateComponent } from './types-of-residental-estate-update.component';

describe('TypesOfResidentalEstate Management Update Component', () => {
  let comp: TypesOfResidentalEstateUpdateComponent;
  let fixture: ComponentFixture<TypesOfResidentalEstateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let typesOfResidentalEstateFormService: TypesOfResidentalEstateFormService;
  let typesOfResidentalEstateService: TypesOfResidentalEstateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TypesOfResidentalEstateUpdateComponent],
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
      .overrideTemplate(TypesOfResidentalEstateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypesOfResidentalEstateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    typesOfResidentalEstateFormService = TestBed.inject(TypesOfResidentalEstateFormService);
    typesOfResidentalEstateService = TestBed.inject(TypesOfResidentalEstateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const typesOfResidentalEstate: ITypesOfResidentalEstate = { id: 456 };

      activatedRoute.data = of({ typesOfResidentalEstate });
      comp.ngOnInit();

      expect(comp.typesOfResidentalEstate).toEqual(typesOfResidentalEstate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypesOfResidentalEstate>>();
      const typesOfResidentalEstate = { id: 123 };
      jest.spyOn(typesOfResidentalEstateFormService, 'getTypesOfResidentalEstate').mockReturnValue(typesOfResidentalEstate);
      jest.spyOn(typesOfResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typesOfResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typesOfResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(typesOfResidentalEstateFormService.getTypesOfResidentalEstate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(typesOfResidentalEstateService.update).toHaveBeenCalledWith(expect.objectContaining(typesOfResidentalEstate));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypesOfResidentalEstate>>();
      const typesOfResidentalEstate = { id: 123 };
      jest.spyOn(typesOfResidentalEstateFormService, 'getTypesOfResidentalEstate').mockReturnValue({ id: null });
      jest.spyOn(typesOfResidentalEstateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typesOfResidentalEstate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typesOfResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(typesOfResidentalEstateFormService.getTypesOfResidentalEstate).toHaveBeenCalled();
      expect(typesOfResidentalEstateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypesOfResidentalEstate>>();
      const typesOfResidentalEstate = { id: 123 };
      jest.spyOn(typesOfResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typesOfResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(typesOfResidentalEstateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
