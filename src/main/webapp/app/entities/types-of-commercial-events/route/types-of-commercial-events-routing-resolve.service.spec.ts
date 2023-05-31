import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITypesOfCommercialEvents } from '../types-of-commercial-events.model';
import { TypesOfCommercialEventsService } from '../service/types-of-commercial-events.service';

import { TypesOfCommercialEventsRoutingResolveService } from './types-of-commercial-events-routing-resolve.service';

describe('TypesOfCommercialEvents routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TypesOfCommercialEventsRoutingResolveService;
  let service: TypesOfCommercialEventsService;
  let resultTypesOfCommercialEvents: ITypesOfCommercialEvents | null | undefined;

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
    routingResolveService = TestBed.inject(TypesOfCommercialEventsRoutingResolveService);
    service = TestBed.inject(TypesOfCommercialEventsService);
    resultTypesOfCommercialEvents = undefined;
  });

  describe('resolve', () => {
    it('should return ITypesOfCommercialEvents returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypesOfCommercialEvents = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTypesOfCommercialEvents).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypesOfCommercialEvents = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTypesOfCommercialEvents).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITypesOfCommercialEvents>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypesOfCommercialEvents = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTypesOfCommercialEvents).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
