import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IResidentalEstates } from '../residental-estates.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../residental-estates.test-samples';

import { ResidentalEstatesService, RestResidentalEstates } from './residental-estates.service';

const requireRestSample: RestResidentalEstates = {
  ...sampleWithRequiredData,
  commissioningDate: sampleWithRequiredData.commissioningDate?.format(DATE_FORMAT),
};

describe('ResidentalEstates Service', () => {
  let service: ResidentalEstatesService;
  let httpMock: HttpTestingController;
  let expectedResult: IResidentalEstates | IResidentalEstates[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResidentalEstatesService);
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

    it('should create a ResidentalEstates', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const residentalEstates = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(residentalEstates).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResidentalEstates', () => {
      const residentalEstates = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(residentalEstates).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResidentalEstates', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResidentalEstates', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ResidentalEstates', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResidentalEstatesToCollectionIfMissing', () => {
      it('should add a ResidentalEstates to an empty array', () => {
        const residentalEstates: IResidentalEstates = sampleWithRequiredData;
        expectedResult = service.addResidentalEstatesToCollectionIfMissing([], residentalEstates);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(residentalEstates);
      });

      it('should not add a ResidentalEstates to an array that contains it', () => {
        const residentalEstates: IResidentalEstates = sampleWithRequiredData;
        const residentalEstatesCollection: IResidentalEstates[] = [
          {
            ...residentalEstates,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResidentalEstatesToCollectionIfMissing(residentalEstatesCollection, residentalEstates);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResidentalEstates to an array that doesn't contain it", () => {
        const residentalEstates: IResidentalEstates = sampleWithRequiredData;
        const residentalEstatesCollection: IResidentalEstates[] = [sampleWithPartialData];
        expectedResult = service.addResidentalEstatesToCollectionIfMissing(residentalEstatesCollection, residentalEstates);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(residentalEstates);
      });

      it('should add only unique ResidentalEstates to an array', () => {
        const residentalEstatesArray: IResidentalEstates[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const residentalEstatesCollection: IResidentalEstates[] = [sampleWithRequiredData];
        expectedResult = service.addResidentalEstatesToCollectionIfMissing(residentalEstatesCollection, ...residentalEstatesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const residentalEstates: IResidentalEstates = sampleWithRequiredData;
        const residentalEstates2: IResidentalEstates = sampleWithPartialData;
        expectedResult = service.addResidentalEstatesToCollectionIfMissing([], residentalEstates, residentalEstates2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(residentalEstates);
        expect(expectedResult).toContain(residentalEstates2);
      });

      it('should accept null and undefined values', () => {
        const residentalEstates: IResidentalEstates = sampleWithRequiredData;
        expectedResult = service.addResidentalEstatesToCollectionIfMissing([], null, residentalEstates, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(residentalEstates);
      });

      it('should return initial array if no ResidentalEstates is added', () => {
        const residentalEstatesCollection: IResidentalEstates[] = [sampleWithRequiredData];
        expectedResult = service.addResidentalEstatesToCollectionIfMissing(residentalEstatesCollection, undefined, null);
        expect(expectedResult).toEqual(residentalEstatesCollection);
      });
    });

    describe('compareResidentalEstates', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResidentalEstates(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareResidentalEstates(entity1, entity2);
        const compareResult2 = service.compareResidentalEstates(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareResidentalEstates(entity1, entity2);
        const compareResult2 = service.compareResidentalEstates(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareResidentalEstates(entity1, entity2);
        const compareResult2 = service.compareResidentalEstates(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
