import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CitiesFormService } from './cities-form.service';
import { CitiesService } from '../service/cities.service';
import { ICities } from '../cities.model';

import { CitiesUpdateComponent } from './cities-update.component';

describe('Cities Management Update Component', () => {
  let comp: CitiesUpdateComponent;
  let fixture: ComponentFixture<CitiesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let citiesFormService: CitiesFormService;
  let citiesService: CitiesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CitiesUpdateComponent],
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
      .overrideTemplate(CitiesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CitiesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    citiesFormService = TestBed.inject(CitiesFormService);
    citiesService = TestBed.inject(CitiesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cities: ICities = { id: 456 };

      activatedRoute.data = of({ cities });
      comp.ngOnInit();

      expect(comp.cities).toEqual(cities);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICities>>();
      const cities = { id: 123 };
      jest.spyOn(citiesFormService, 'getCities').mockReturnValue(cities);
      jest.spyOn(citiesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cities });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cities }));
      saveSubject.complete();

      // THEN
      expect(citiesFormService.getCities).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(citiesService.update).toHaveBeenCalledWith(expect.objectContaining(cities));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICities>>();
      const cities = { id: 123 };
      jest.spyOn(citiesFormService, 'getCities').mockReturnValue({ id: null });
      jest.spyOn(citiesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cities: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cities }));
      saveSubject.complete();

      // THEN
      expect(citiesFormService.getCities).toHaveBeenCalled();
      expect(citiesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICities>>();
      const cities = { id: 123 };
      jest.spyOn(citiesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cities });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(citiesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
