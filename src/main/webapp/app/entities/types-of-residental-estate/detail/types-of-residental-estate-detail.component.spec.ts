import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TypesOfResidentalEstateDetailComponent } from './types-of-residental-estate-detail.component';

describe('TypesOfResidentalEstate Management Detail Component', () => {
  let comp: TypesOfResidentalEstateDetailComponent;
  let fixture: ComponentFixture<TypesOfResidentalEstateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TypesOfResidentalEstateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ typesOfResidentalEstate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TypesOfResidentalEstateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TypesOfResidentalEstateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load typesOfResidentalEstate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.typesOfResidentalEstate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
