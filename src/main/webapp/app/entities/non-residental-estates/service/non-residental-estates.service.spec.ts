import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { INonResidentalEstates } from '../non-residental-estates.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../non-residental-estates.test-samples';

import { NonResidentalEstatesService, RestNonResidentalEstates } from './non-residental-estates.service';

const requireRestSample: RestNonResidentalEstates = {
  ...sampleWithRequiredData,
  commissioningDate: sampleWithRequiredData.commissioningDate?.format(DATE_FORMAT),
};

describe('NonResidentalEstates Service', () => {
  let service: NonResidentalEstatesService;
  let httpMock: HttpTestingController;
  let expectedResult: INonResidentalEstates | INonResidentalEstates[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NonResidentalEstatesService);
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

    it('should create a NonResidentalEstates', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const nonResidentalEstates = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(nonResidentalEstates).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NonResidentalEstates', () => {
      const nonResidentalEstates = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(nonResidentalEstates).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NonResidentalEstates', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NonResidentalEstates', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NonResidentalEstates', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNonResidentalEstatesToCollectionIfMissing', () => {
      it('should add a NonResidentalEstates to an empty array', () => {
        const nonResidentalEstates: INonResidentalEstates = sampleWithRequiredData;
        expectedResult = service.addNonResidentalEstatesToCollectionIfMissing([], nonResidentalEstates);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nonResidentalEstates);
      });

      it('should not add a NonResidentalEstates to an array that contains it', () => {
        const nonResidentalEstates: INonResidentalEstates = sampleWithRequiredData;
        const nonResidentalEstatesCollection: INonResidentalEstates[] = [
          {
            ...nonResidentalEstates,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNonResidentalEstatesToCollectionIfMissing(nonResidentalEstatesCollection, nonResidentalEstates);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NonResidentalEstates to an array that doesn't contain it", () => {
        const nonResidentalEstates: INonResidentalEstates = sampleWithRequiredData;
        const nonResidentalEstatesCollection: INonResidentalEstates[] = [sampleWithPartialData];
        expectedResult = service.addNonResidentalEstatesToCollectionIfMissing(nonResidentalEstatesCollection, nonResidentalEstates);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nonResidentalEstates);
      });

      it('should add only unique NonResidentalEstates to an array', () => {
        const nonResidentalEstatesArray: INonResidentalEstates[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const nonResidentalEstatesCollection: INonResidentalEstates[] = [sampleWithRequiredData];
        expectedResult = service.addNonResidentalEstatesToCollectionIfMissing(nonResidentalEstatesCollection, ...nonResidentalEstatesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nonResidentalEstates: INonResidentalEstates = sampleWithRequiredData;
        const nonResidentalEstates2: INonResidentalEstates = sampleWithPartialData;
        expectedResult = service.addNonResidentalEstatesToCollectionIfMissing([], nonResidentalEstates, nonResidentalEstates2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nonResidentalEstates);
        expect(expectedResult).toContain(nonResidentalEstates2);
      });

      it('should accept null and undefined values', () => {
        const nonResidentalEstates: INonResidentalEstates = sampleWithRequiredData;
        expectedResult = service.addNonResidentalEstatesToCollectionIfMissing([], null, nonResidentalEstates, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nonResidentalEstates);
      });

      it('should return initial array if no NonResidentalEstates is added', () => {
        const nonResidentalEstatesCollection: INonResidentalEstates[] = [sampleWithRequiredData];
        expectedResult = service.addNonResidentalEstatesToCollectionIfMissing(nonResidentalEstatesCollection, undefined, null);
        expect(expectedResult).toEqual(nonResidentalEstatesCollection);
      });
    });

    describe('compareNonResidentalEstates', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNonResidentalEstates(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNonResidentalEstates(entity1, entity2);
        const compareResult2 = service.compareNonResidentalEstates(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNonResidentalEstates(entity1, entity2);
        const compareResult2 = service.compareNonResidentalEstates(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNonResidentalEstates(entity1, entity2);
        const compareResult2 = service.compareNonResidentalEstates(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
