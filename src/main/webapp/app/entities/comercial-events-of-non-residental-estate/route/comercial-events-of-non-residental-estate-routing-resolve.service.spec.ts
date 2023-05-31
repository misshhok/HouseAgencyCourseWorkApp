import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IComercialEventsOfNonResidentalEstate } from '../comercial-events-of-non-residental-estate.model';
import { ComercialEventsOfNonResidentalEstateService } from '../service/comercial-events-of-non-residental-estate.service';

import { ComercialEventsOfNonResidentalEstateRoutingResolveService } from './comercial-events-of-non-residental-estate-routing-resolve.service';

describe('ComercialEventsOfNonResidentalEstate routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ComercialEventsOfNonResidentalEstateRoutingResolveService;
  let service: ComercialEventsOfNonResidentalEstateService;
  let resultComercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(ComercialEventsOfNonResidentalEstateRoutingResolveService);
    service = TestBed.inject(ComercialEventsOfNonResidentalEstateService);
    resultComercialEventsOfNonResidentalEstate = undefined;
  });

  describe('resolve', () => {
    it('should return IComercialEventsOfNonResidentalEstate returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultComercialEventsOfNonResidentalEstate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultComercialEventsOfNonResidentalEstate).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultComercialEventsOfNonResidentalEstate = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultComercialEventsOfNonResidentalEstate).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IComercialEventsOfNonResidentalEstate>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultComercialEventsOfNonResidentalEstate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultComercialEventsOfNonResidentalEstate).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
