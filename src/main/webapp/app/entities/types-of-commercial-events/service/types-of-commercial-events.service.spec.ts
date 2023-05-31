import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITypesOfCommercialEvents } from '../types-of-commercial-events.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../types-of-commercial-events.test-samples';

import { TypesOfCommercialEventsService } from './types-of-commercial-events.service';

const requireRestSample: ITypesOfCommercialEvents = {
  ...sampleWithRequiredData,
};

describe('TypesOfCommercialEvents Service', () => {
  let service: TypesOfCommercialEventsService;
  let httpMock: HttpTestingController;
  let expectedResult: ITypesOfCommercialEvents | ITypesOfCommercialEvents[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TypesOfCommercialEventsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a TypesOfCommercialEvents', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const typesOfCommercialEvents = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(typesOfCommercialEvents).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TypesOfCommercialEvents', () => {
      const typesOfCommercialEvents = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(typesOfCommercialEvents).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TypesOfCommercialEvents', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TypesOfCommercialEvents', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TypesOfCommercialEvents', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTypesOfCommercialEventsToCollectionIfMissing', () => {
      it('should add a TypesOfCommercialEvents to an empty array', () => {
        const typesOfCommercialEvents: ITypesOfCommercialEvents = sampleWithRequiredData;
        expectedResult = service.addTypesOfCommercialEventsToCollectionIfMissing([], typesOfCommercialEvents);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typesOfCommercialEvents);
      });

      it('should not add a TypesOfCommercialEvents to an array that contains it', () => {
        const typesOfCommercialEvents: ITypesOfCommercialEvents = sampleWithRequiredData;
        const typesOfCommercialEventsCollection: ITypesOfCommercialEvents[] = [
          {
            ...typesOfCommercialEvents,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTypesOfCommercialEventsToCollectionIfMissing(
          typesOfCommercialEventsCollection,
          typesOfCommercialEvents
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TypesOfCommercialEvents to an array that doesn't contain it", () => {
        const typesOfCommercialEvents: ITypesOfCommercialEvents = sampleWithRequiredData;
        const typesOfCommercialEventsCollection: ITypesOfCommercialEvents[] = [sampleWithPartialData];
        expectedResult = service.addTypesOfCommercialEventsToCollectionIfMissing(
          typesOfCommercialEventsCollection,
          typesOfCommercialEvents
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typesOfCommercialEvents);
      });

      it('should add only unique TypesOfCommercialEvents to an array', () => {
        const typesOfCommercialEventsArray: ITypesOfCommercialEvents[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const typesOfCommercialEventsCollection: ITypesOfCommercialEvents[] = [sampleWithRequiredData];
        expectedResult = service.addTypesOfCommercialEventsToCollectionIfMissing(
          typesOfCommercialEventsCollection,
          ...typesOfCommercialEventsArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const typesOfCommercialEvents: ITypesOfCommercialEvents = sampleWithRequiredData;
        const typesOfCommercialEvents2: ITypesOfCommercialEvents = sampleWithPartialData;
        expectedResult = service.addTypesOfCommercialEventsToCollectionIfMissing([], typesOfCommercialEvents, typesOfCommercialEvents2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typesOfCommercialEvents);
        expect(expectedResult).toContain(typesOfCommercialEvents2);
      });

      it('should accept null and undefined values', () => {
        const typesOfCommercialEvents: ITypesOfCommercialEvents = sampleWithRequiredData;
        expectedResult = service.addTypesOfCommercialEventsToCollectionIfMissing([], null, typesOfCommercialEvents, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typesOfCommercialEvents);
      });

      it('should return initial array if no TypesOfCommercialEvents is added', () => {
        const typesOfCommercialEventsCollection: ITypesOfCommercialEvents[] = [sampleWithRequiredData];
        expectedResult = service.addTypesOfCommercialEventsToCollectionIfMissing(typesOfCommercialEventsCollection, undefined, null);
        expect(expectedResult).toEqual(typesOfCommercialEventsCollection);
      });
    });

    describe('compareTypesOfCommercialEvents', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTypesOfCommercialEvents(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTypesOfCommercialEvents(entity1, entity2);
        const compareResult2 = service.compareTypesOfCommercialEvents(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTypesOfCommercialEvents(entity1, entity2);
        const compareResult2 = service.compareTypesOfCommercialEvents(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTypesOfCommercialEvents(entity1, entity2);
        const compareResult2 = service.compareTypesOfCommercialEvents(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
