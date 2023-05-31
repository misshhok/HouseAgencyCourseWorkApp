import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TypesOfCommercialEventsDetailComponent } from './types-of-commercial-events-detail.component';

describe('TypesOfCommercialEvents Management Detail Component', () => {
  let comp: TypesOfCommercialEventsDetailComponent;
  let fixture: ComponentFixture<TypesOfCommercialEventsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TypesOfCommercialEventsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ typesOfCommercialEvents: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TypesOfCommercialEventsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TypesOfCommercialEventsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load typesOfCommercialEvents on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.typesOfCommercialEvents).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
