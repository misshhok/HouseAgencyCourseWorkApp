import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ComercialEventsOfResidentalEstateDetailComponent } from './comercial-events-of-residental-estate-detail.component';

describe('ComercialEventsOfResidentalEstate Management Detail Component', () => {
  let comp: ComercialEventsOfResidentalEstateDetailComponent;
  let fixture: ComponentFixture<ComercialEventsOfResidentalEstateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ComercialEventsOfResidentalEstateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ comercialEventsOfResidentalEstate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ComercialEventsOfResidentalEstateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ComercialEventsOfResidentalEstateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load comercialEventsOfResidentalEstate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.comercialEventsOfResidentalEstate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
