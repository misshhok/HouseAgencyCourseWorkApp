import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NonResidentalEstatesDetailComponent } from './non-residental-estates-detail.component';

describe('NonResidentalEstates Management Detail Component', () => {
  let comp: NonResidentalEstatesDetailComponent;
  let fixture: ComponentFixture<NonResidentalEstatesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NonResidentalEstatesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ nonResidentalEstates: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NonResidentalEstatesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NonResidentalEstatesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load nonResidentalEstates on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.nonResidentalEstates).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
