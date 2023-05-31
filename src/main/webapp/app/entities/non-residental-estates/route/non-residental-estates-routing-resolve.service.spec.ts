import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { INonResidentalEstates } from '../non-residental-estates.model';
import { NonResidentalEstatesService } from '../service/non-residental-estates.service';

import { NonResidentalEstatesRoutingResolveService } from './non-residental-estates-routing-resolve.service';

describe('NonResidentalEstates routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NonResidentalEstatesRoutingResolveService;
  let service: NonResidentalEstatesService;
  let resultNonResidentalEstates: INonResidentalEstates | null | undefined;

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
    routingResolveService = TestBed.inject(NonResidentalEstatesRoutingResolveService);
    service = TestBed.inject(NonResidentalEstatesService);
    resultNonResidentalEstates = undefined;
  });

  describe('resolve', () => {
    it('should return INonResidentalEstates returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNonResidentalEstates = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNonResidentalEstates).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNonResidentalEstates = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNonResidentalEstates).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<INonResidentalEstates>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNonResidentalEstates = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNonResidentalEstates).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
