import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PurposesOfNonResidentalEstateDetailComponent } from './purposes-of-non-residental-estate-detail.component';

describe('PurposesOfNonResidentalEstate Management Detail Component', () => {
  let comp: PurposesOfNonResidentalEstateDetailComponent;
  let fixture: ComponentFixture<PurposesOfNonResidentalEstateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PurposesOfNonResidentalEstateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ purposesOfNonResidentalEstate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PurposesOfNonResidentalEstateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PurposesOfNonResidentalEstateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load purposesOfNonResidentalEstate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.purposesOfNonResidentalEstate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
