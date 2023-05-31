import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResidentalEstatesDetailComponent } from './residental-estates-detail.component';

describe('ResidentalEstates Management Detail Component', () => {
  let comp: ResidentalEstatesDetailComponent;
  let fixture: ComponentFixture<ResidentalEstatesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResidentalEstatesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ residentalEstates: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResidentalEstatesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResidentalEstatesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load residentalEstates on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.residentalEstates).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
