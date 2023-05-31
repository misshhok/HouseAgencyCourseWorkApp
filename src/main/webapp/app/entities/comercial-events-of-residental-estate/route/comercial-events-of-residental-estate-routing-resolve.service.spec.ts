import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IComercialEventsOfResidentalEstate } from '../comercial-events-of-residental-estate.model';
import { ComercialEventsOfResidentalEstateService } from '../service/comercial-events-of-residental-estate.service';

import { ComercialEventsOfResidentalEstateRoutingResolveService } from './comercial-events-of-residental-estate-routing-resolve.service';

describe('ComercialEventsOfResidentalEstate routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ComercialEventsOfResidentalEstateRoutingResolveService;
  let service: ComercialEventsOfResidentalEstateService;
  let resultComercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate | null | undefined;

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
    routingResolveService = TestBed.inject(ComercialEventsOfResidentalEstateRoutingResolveService);
    service = TestBed.inject(ComercialEventsOfResidentalEstateService);
    resultComercialEventsOfResidentalEstate = undefined;
  });

  describe('resolve', () => {
    it('should return IComercialEventsOfResidentalEstate returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultComercialEventsOfResidentalEstate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultComercialEventsOfResidentalEstate).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultComercialEventsOfResidentalEstate = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultComercialEventsOfResidentalEstate).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IComercialEventsOfResidentalEstate>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultComercialEventsOfResidentalEstate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultComercialEventsOfResidentalEstate).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
