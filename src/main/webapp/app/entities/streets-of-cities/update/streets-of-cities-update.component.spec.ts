import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StreetsOfCitiesFormService } from './streets-of-cities-form.service';
import { StreetsOfCitiesService } from '../service/streets-of-cities.service';
import { IStreetsOfCities } from '../streets-of-cities.model';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';

import { StreetsOfCitiesUpdateComponent } from './streets-of-cities-update.component';

describe('StreetsOfCities Management Update Component', () => {
  let comp: StreetsOfCitiesUpdateComponent;
  let fixture: ComponentFixture<StreetsOfCitiesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let streetsOfCitiesFormService: StreetsOfCitiesFormService;
  let streetsOfCitiesService: StreetsOfCitiesService;
  let citiesService: CitiesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StreetsOfCitiesUpdateComponent],
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
      .overrideTemplate(StreetsOfCitiesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StreetsOfCitiesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    streetsOfCitiesFormService = TestBed.inject(StreetsOfCitiesFormService);
    streetsOfCitiesService = TestBed.inject(StreetsOfCitiesService);
    citiesService = TestBed.inject(CitiesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cities query and add missing value', () => {
      const streetsOfCities: IStreetsOfCities = { id: 456 };
      const cityId: ICities = { id: 38017 };
      streetsOfCities.cityId = cityId;

      const citiesCollection: ICities[] = [{ id: 48777 }];
      jest.spyOn(citiesService, 'query').mockReturnValue(of(new HttpResponse({ body: citiesCollection })));
      const additionalCities = [cityId];
      const expectedCollection: ICities[] = [...additionalCities, ...citiesCollection];
      jest.spyOn(citiesService, 'addCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ streetsOfCities });
      comp.ngOnInit();

      expect(citiesService.query).toHaveBeenCalled();
      expect(citiesService.addCitiesToCollectionIfMissing).toHaveBeenCalledWith(
        citiesCollection,
        ...additionalCities.map(expect.objectContaining)
      );
      expect(comp.citiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const streetsOfCities: IStreetsOfCities = { id: 456 };
      const cityId: ICities = { id: 31250 };
      streetsOfCities.cityId = cityId;

      activatedRoute.data = of({ streetsOfCities });
      comp.ngOnInit();

      expect(comp.citiesSharedCollection).toContain(cityId);
      expect(comp.streetsOfCities).toEqual(streetsOfCities);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStreetsOfCities>>();
      const streetsOfCities = { id: 123 };
      jest.spyOn(streetsOfCitiesFormService, 'getStreetsOfCities').mockReturnValue(streetsOfCities);
      jest.spyOn(streetsOfCitiesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ streetsOfCities });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: streetsOfCities }));
      saveSubject.complete();

      // THEN
      expect(streetsOfCitiesFormService.getStreetsOfCities).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(streetsOfCitiesService.update).toHaveBeenCalledWith(expect.objectContaining(streetsOfCities));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStreetsOfCities>>();
      const streetsOfCities = { id: 123 };
      jest.spyOn(streetsOfCitiesFormService, 'getStreetsOfCities').mockReturnValue({ id: null });
      jest.spyOn(streetsOfCitiesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ streetsOfCities: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: streetsOfCities }));
      saveSubject.complete();

      // THEN
      expect(streetsOfCitiesFormService.getStreetsOfCities).toHaveBeenCalled();
      expect(streetsOfCitiesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStreetsOfCities>>();
      const streetsOfCities = { id: 123 };
      jest.spyOn(streetsOfCitiesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ streetsOfCities });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(streetsOfCitiesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCities', () => {
      it('Should forward to citiesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(citiesService, 'compareCities');
        comp.compareCities(entity, entity2);
        expect(citiesService.compareCities).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
