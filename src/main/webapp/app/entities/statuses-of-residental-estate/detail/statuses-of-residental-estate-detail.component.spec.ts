import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StatusesOfResidentalEstateDetailComponent } from './statuses-of-residental-estate-detail.component';

describe('StatusesOfResidentalEstate Management Detail Component', () => {
  let comp: StatusesOfResidentalEstateDetailComponent;
  let fixture: ComponentFixture<StatusesOfResidentalEstateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StatusesOfResidentalEstateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ statusesOfResidentalEstate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StatusesOfResidentalEstateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StatusesOfResidentalEstateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load statusesOfResidentalEstate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.statusesOfResidentalEstate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
