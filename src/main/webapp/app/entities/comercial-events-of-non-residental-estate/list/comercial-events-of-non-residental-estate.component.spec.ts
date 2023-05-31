import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ComercialEventsOfNonResidentalEstateService } from '../service/comercial-events-of-non-residental-estate.service';

import { ComercialEventsOfNonResidentalEstateComponent } from './comercial-events-of-non-residental-estate.component';
import SpyInstance = jest.SpyInstance;

describe('ComercialEventsOfNonResidentalEstate Management Component', () => {
  let comp: ComercialEventsOfNonResidentalEstateComponent;
  let fixture: ComponentFixture<ComercialEventsOfNonResidentalEstateComponent>;
  let service: ComercialEventsOfNonResidentalEstateService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          { path: 'comercial-events-of-non-residental-estate', component: ComercialEventsOfNonResidentalEstateComponent },
        ]),
        HttpClientTestingModule,
      ],
      declarations: [ComercialEventsOfNonResidentalEstateComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ComercialEventsOfNonResidentalEstateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ComercialEventsOfNonResidentalEstateComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ComercialEventsOfNonResidentalEstateService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.comercialEventsOfNonResidentalEstates?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to comercialEventsOfNonResidentalEstateService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getComercialEventsOfNonResidentalEstateIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getComercialEventsOfNonResidentalEstateIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });

  it('should load a page', () => {
    // WHEN
    comp.navigateToPage(1);

    // THEN
    expect(routerNavigateSpy).toHaveBeenCalled();
  });

  it('should calculate the sort attribute for an id', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['id,desc'] }));
  });

  it('should calculate the sort attribute for a non-id attribute', () => {
    // GIVEN
    comp.predicate = 'name';

    // WHEN
    comp.navigateToWithComponentValues();

    // THEN
    expect(routerNavigateSpy).toHaveBeenLastCalledWith(
      expect.anything(),
      expect.objectContaining({
        queryParams: expect.objectContaining({
          sort: ['name,asc'],
        }),
      })
    );
  });

  it('should calculate the filter attribute', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ 'someId.in': ['dc4279ea-cfb9-11ec-9d64-0242ac120002'] }));
  });
});
