import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ComercialEventsOfNonResidentalEstateDetailComponent } from './comercial-events-of-non-residental-estate-detail.component';

describe('ComercialEventsOfNonResidentalEstate Management Detail Component', () => {
  let comp: ComercialEventsOfNonResidentalEstateDetailComponent;
  let fixture: ComponentFixture<ComercialEventsOfNonResidentalEstateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ComercialEventsOfNonResidentalEstateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ comercialEventsOfNonResidentalEstate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ComercialEventsOfNonResidentalEstateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ComercialEventsOfNonResidentalEstateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load comercialEventsOfNonResidentalEstate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.comercialEventsOfNonResidentalEstate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
