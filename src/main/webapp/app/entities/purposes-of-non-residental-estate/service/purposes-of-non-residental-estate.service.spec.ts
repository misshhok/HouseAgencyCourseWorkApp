import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPurposesOfNonResidentalEstate } from '../purposes-of-non-residental-estate.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../purposes-of-non-residental-estate.test-samples';

import { PurposesOfNonResidentalEstateService } from './purposes-of-non-residental-estate.service';

const requireRestSample: IPurposesOfNonResidentalEstate = {
  ...sampleWithRequiredData,
};

describe('PurposesOfNonResidentalEstate Service', () => {
  let service: PurposesOfNonResidentalEstateService;
  let httpMock: HttpTestingController;
  let expectedResult: IPurposesOfNonResidentalEstate | IPurposesOfNonResidentalEstate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PurposesOfNonResidentalEstateService);
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

    it('should create a PurposesOfNonResidentalEstate', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const purposesOfNonResidentalEstate = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(purposesOfNonResidentalEstate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PurposesOfNonResidentalEstate', () => {
      const purposesOfNonResidentalEstate = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(purposesOfNonResidentalEstate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PurposesOfNonResidentalEstate', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PurposesOfNonResidentalEstate', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PurposesOfNonResidentalEstate', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPurposesOfNonResidentalEstateToCollectionIfMissing', () => {
      it('should add a PurposesOfNonResidentalEstate to an empty array', () => {
        const purposesOfNonResidentalEstate: IPurposesOfNonResidentalEstate = sampleWithRequiredData;
        expectedResult = service.addPurposesOfNonResidentalEstateToCollectionIfMissing([], purposesOfNonResidentalEstate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(purposesOfNonResidentalEstate);
      });

      it('should not add a PurposesOfNonResidentalEstate to an array that contains it', () => {
        const purposesOfNonResidentalEstate: IPurposesOfNonResidentalEstate = sampleWithRequiredData;
        const purposesOfNonResidentalEstateCollection: IPurposesOfNonResidentalEstate[] = [
          {
            ...purposesOfNonResidentalEstate,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPurposesOfNonResidentalEstateToCollectionIfMissing(
          purposesOfNonResidentalEstateCollection,
          purposesOfNonResidentalEstate
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PurposesOfNonResidentalEstate to an array that doesn't contain it", () => {
        const purposesOfNonResidentalEstate: IPurposesOfNonResidentalEstate = sampleWithRequiredData;
        const purposesOfNonResidentalEstateCollection: IPurposesOfNonResidentalEstate[] = [sampleWithPartialData];
        expectedResult = service.addPurposesOfNonResidentalEstateToCollectionIfMissing(
          purposesOfNonResidentalEstateCollection,
          purposesOfNonResidentalEstate
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(purposesOfNonResidentalEstate);
      });

      it('should add only unique PurposesOfNonResidentalEstate to an array', () => {
        const purposesOfNonResidentalEstateArray: IPurposesOfNonResidentalEstate[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const purposesOfNonResidentalEstateCollection: IPurposesOfNonResidentalEstate[] = [sampleWithRequiredData];
        expectedResult = service.addPurposesOfNonResidentalEstateToCollectionIfMissing(
          purposesOfNonResidentalEstateCollection,
          ...purposesOfNonResidentalEstateArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const purposesOfNonResidentalEstate: IPurposesOfNonResidentalEstate = sampleWithRequiredData;
        const purposesOfNonResidentalEstate2: IPurposesOfNonResidentalEstate = sampleWithPartialData;
        expectedResult = service.addPurposesOfNonResidentalEstateToCollectionIfMissing(
          [],
          purposesOfNonResidentalEstate,
          purposesOfNonResidentalEstate2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(purposesOfNonResidentalEstate);
        expect(expectedResult).toContain(purposesOfNonResidentalEstate2);
      });

      it('should accept null and undefined values', () => {
        const purposesOfNonResidentalEstate: IPurposesOfNonResidentalEstate = sampleWithRequiredData;
        expectedResult = service.addPurposesOfNonResidentalEstateToCollectionIfMissing([], null, purposesOfNonResidentalEstate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(purposesOfNonResidentalEstate);
      });

      it('should return initial array if no PurposesOfNonResidentalEstate is added', () => {
        const purposesOfNonResidentalEstateCollection: IPurposesOfNonResidentalEstate[] = [sampleWithRequiredData];
        expectedResult = service.addPurposesOfNonResidentalEstateToCollectionIfMissing(
          purposesOfNonResidentalEstateCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(purposesOfNonResidentalEstateCollection);
      });
    });

    describe('comparePurposesOfNonResidentalEstate', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePurposesOfNonResidentalEstate(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePurposesOfNonResidentalEstate(entity1, entity2);
        const compareResult2 = service.comparePurposesOfNonResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePurposesOfNonResidentalEstate(entity1, entity2);
        const compareResult2 = service.comparePurposesOfNonResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePurposesOfNonResidentalEstate(entity1, entity2);
        const compareResult2 = service.comparePurposesOfNonResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
