import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPurposesOfNonResidentalEstate } from '../purposes-of-non-residental-estate.model';
import { PurposesOfNonResidentalEstateService } from '../service/purposes-of-non-residental-estate.service';

import { PurposesOfNonResidentalEstateRoutingResolveService } from './purposes-of-non-residental-estate-routing-resolve.service';

describe('PurposesOfNonResidentalEstate routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PurposesOfNonResidentalEstateRoutingResolveService;
  let service: PurposesOfNonResidentalEstateService;
  let resultPurposesOfNonResidentalEstate: IPurposesOfNonResidentalEstate | null | undefined;

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
    routingResolveService = TestBed.inject(PurposesOfNonResidentalEstateRoutingResolveService);
    service = TestBed.inject(PurposesOfNonResidentalEstateService);
    resultPurposesOfNonResidentalEstate = undefined;
  });

  describe('resolve', () => {
    it('should return IPurposesOfNonResidentalEstate returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPurposesOfNonResidentalEstate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPurposesOfNonResidentalEstate).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPurposesOfNonResidentalEstate = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPurposesOfNonResidentalEstate).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPurposesOfNonResidentalEstate>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPurposesOfNonResidentalEstate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPurposesOfNonResidentalEstate).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
