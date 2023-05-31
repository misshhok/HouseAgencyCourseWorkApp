import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StreetsOfCitiesDetailComponent } from './streets-of-cities-detail.component';

describe('StreetsOfCities Management Detail Component', () => {
  let comp: StreetsOfCitiesDetailComponent;
  let fixture: ComponentFixture<StreetsOfCitiesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StreetsOfCitiesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ streetsOfCities: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StreetsOfCitiesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StreetsOfCitiesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load streetsOfCities on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.streetsOfCities).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
