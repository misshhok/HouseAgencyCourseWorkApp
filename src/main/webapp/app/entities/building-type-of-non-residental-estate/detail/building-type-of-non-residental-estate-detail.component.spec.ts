import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BuildingTypeOfNonResidentalEstateDetailComponent } from './building-type-of-non-residental-estate-detail.component';

describe('BuildingTypeOfNonResidentalEstate Management Detail Component', () => {
  let comp: BuildingTypeOfNonResidentalEstateDetailComponent;
  let fixture: ComponentFixture<BuildingTypeOfNonResidentalEstateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BuildingTypeOfNonResidentalEstateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ buildingTypeOfNonResidentalEstate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BuildingTypeOfNonResidentalEstateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BuildingTypeOfNonResidentalEstateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load buildingTypeOfNonResidentalEstate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.buildingTypeOfNonResidentalEstate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
