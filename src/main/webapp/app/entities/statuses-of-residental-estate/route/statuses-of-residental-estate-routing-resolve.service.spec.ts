import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IStatusesOfResidentalEstate } from '../statuses-of-residental-estate.model';
import { StatusesOfResidentalEstateService } from '../service/statuses-of-residental-estate.service';

import { StatusesOfResidentalEstateRoutingResolveService } from './statuses-of-residental-estate-routing-resolve.service';

describe('StatusesOfResidentalEstate routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: StatusesOfResidentalEstateRoutingResolveService;
  let service: StatusesOfResidentalEstateService;
  let resultStatusesOfResidentalEstate: IStatusesOfResidentalEstate | null | undefined;

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
    routingResolveService = TestBed.inject(StatusesOfResidentalEstateRoutingResolveService);
    service = TestBed.inject(StatusesOfResidentalEstateService);
    resultStatusesOfResidentalEstate = undefined;
  });

  describe('resolve', () => {
    it('should return IStatusesOfResidentalEstate returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusesOfResidentalEstate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStatusesOfResidentalEstate).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusesOfResidentalEstate = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultStatusesOfResidentalEstate).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IStatusesOfResidentalEstate>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusesOfResidentalEstate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStatusesOfResidentalEstate).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
