import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BuildingTypeOfNonResidentalEstateFormService } from './building-type-of-non-residental-estate-form.service';
import { BuildingTypeOfNonResidentalEstateService } from '../service/building-type-of-non-residental-estate.service';
import { IBuildingTypeOfNonResidentalEstate } from '../building-type-of-non-residental-estate.model';

import { BuildingTypeOfNonResidentalEstateUpdateComponent } from './building-type-of-non-residental-estate-update.component';

describe('BuildingTypeOfNonResidentalEstate Management Update Component', () => {
  let comp: BuildingTypeOfNonResidentalEstateUpdateComponent;
  let fixture: ComponentFixture<BuildingTypeOfNonResidentalEstateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let buildingTypeOfNonResidentalEstateFormService: BuildingTypeOfNonResidentalEstateFormService;
  let buildingTypeOfNonResidentalEstateService: BuildingTypeOfNonResidentalEstateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BuildingTypeOfNonResidentalEstateUpdateComponent],
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
      .overrideTemplate(BuildingTypeOfNonResidentalEstateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BuildingTypeOfNonResidentalEstateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    buildingTypeOfNonResidentalEstateFormService = TestBed.inject(BuildingTypeOfNonResidentalEstateFormService);
    buildingTypeOfNonResidentalEstateService = TestBed.inject(BuildingTypeOfNonResidentalEstateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const buildingTypeOfNonResidentalEstate: IBuildingTypeOfNonResidentalEstate = { id: 456 };

      activatedRoute.data = of({ buildingTypeOfNonResidentalEstate });
      comp.ngOnInit();

      expect(comp.buildingTypeOfNonResidentalEstate).toEqual(buildingTypeOfNonResidentalEstate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBuildingTypeOfNonResidentalEstate>>();
      const buildingTypeOfNonResidentalEstate = { id: 123 };
      jest
        .spyOn(buildingTypeOfNonResidentalEstateFormService, 'getBuildingTypeOfNonResidentalEstate')
        .mockReturnValue(buildingTypeOfNonResidentalEstate);
      jest.spyOn(buildingTypeOfNonResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ buildingTypeOfNonResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: buildingTypeOfNonResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(buildingTypeOfNonResidentalEstateFormService.getBuildingTypeOfNonResidentalEstate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(buildingTypeOfNonResidentalEstateService.update).toHaveBeenCalledWith(
        expect.objectContaining(buildingTypeOfNonResidentalEstate)
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBuildingTypeOfNonResidentalEstate>>();
      const buildingTypeOfNonResidentalEstate = { id: 123 };
      jest.spyOn(buildingTypeOfNonResidentalEstateFormService, 'getBuildingTypeOfNonResidentalEstate').mockReturnValue({ id: null });
      jest.spyOn(buildingTypeOfNonResidentalEstateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ buildingTypeOfNonResidentalEstate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: buildingTypeOfNonResidentalEstate }));
      saveSubject.complete();

      // THEN
      expect(buildingTypeOfNonResidentalEstateFormService.getBuildingTypeOfNonResidentalEstate).toHaveBeenCalled();
      expect(buildingTypeOfNonResidentalEstateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBuildingTypeOfNonResidentalEstate>>();
      const buildingTypeOfNonResidentalEstate = { id: 123 };
      jest.spyOn(buildingTypeOfNonResidentalEstateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ buildingTypeOfNonResidentalEstate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(buildingTypeOfNonResidentalEstateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
