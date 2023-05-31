import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITypesOfResidentalEstate } from '../types-of-residental-estate.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../types-of-residental-estate.test-samples';

import { TypesOfResidentalEstateService } from './types-of-residental-estate.service';

const requireRestSample: ITypesOfResidentalEstate = {
  ...sampleWithRequiredData,
};

describe('TypesOfResidentalEstate Service', () => {
  let service: TypesOfResidentalEstateService;
  let httpMock: HttpTestingController;
  let expectedResult: ITypesOfResidentalEstate | ITypesOfResidentalEstate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TypesOfResidentalEstateService);
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

    it('should create a TypesOfResidentalEstate', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const typesOfResidentalEstate = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(typesOfResidentalEstate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TypesOfResidentalEstate', () => {
      const typesOfResidentalEstate = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(typesOfResidentalEstate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TypesOfResidentalEstate', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TypesOfResidentalEstate', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TypesOfResidentalEstate', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTypesOfResidentalEstateToCollectionIfMissing', () => {
      it('should add a TypesOfResidentalEstate to an empty array', () => {
        const typesOfResidentalEstate: ITypesOfResidentalEstate = sampleWithRequiredData;
        expectedResult = service.addTypesOfResidentalEstateToCollectionIfMissing([], typesOfResidentalEstate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typesOfResidentalEstate);
      });

      it('should not add a TypesOfResidentalEstate to an array that contains it', () => {
        const typesOfResidentalEstate: ITypesOfResidentalEstate = sampleWithRequiredData;
        const typesOfResidentalEstateCollection: ITypesOfResidentalEstate[] = [
          {
            ...typesOfResidentalEstate,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTypesOfResidentalEstateToCollectionIfMissing(
          typesOfResidentalEstateCollection,
          typesOfResidentalEstate
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TypesOfResidentalEstate to an array that doesn't contain it", () => {
        const typesOfResidentalEstate: ITypesOfResidentalEstate = sampleWithRequiredData;
        const typesOfResidentalEstateCollection: ITypesOfResidentalEstate[] = [sampleWithPartialData];
        expectedResult = service.addTypesOfResidentalEstateToCollectionIfMissing(
          typesOfResidentalEstateCollection,
          typesOfResidentalEstate
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typesOfResidentalEstate);
      });

      it('should add only unique TypesOfResidentalEstate to an array', () => {
        const typesOfResidentalEstateArray: ITypesOfResidentalEstate[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const typesOfResidentalEstateCollection: ITypesOfResidentalEstate[] = [sampleWithRequiredData];
        expectedResult = service.addTypesOfResidentalEstateToCollectionIfMissing(
          typesOfResidentalEstateCollection,
          ...typesOfResidentalEstateArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const typesOfResidentalEstate: ITypesOfResidentalEstate = sampleWithRequiredData;
        const typesOfResidentalEstate2: ITypesOfResidentalEstate = sampleWithPartialData;
        expectedResult = service.addTypesOfResidentalEstateToCollectionIfMissing([], typesOfResidentalEstate, typesOfResidentalEstate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typesOfResidentalEstate);
        expect(expectedResult).toContain(typesOfResidentalEstate2);
      });

      it('should accept null and undefined values', () => {
        const typesOfResidentalEstate: ITypesOfResidentalEstate = sampleWithRequiredData;
        expectedResult = service.addTypesOfResidentalEstateToCollectionIfMissing([], null, typesOfResidentalEstate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typesOfResidentalEstate);
      });

      it('should return initial array if no TypesOfResidentalEstate is added', () => {
        const typesOfResidentalEstateCollection: ITypesOfResidentalEstate[] = [sampleWithRequiredData];
        expectedResult = service.addTypesOfResidentalEstateToCollectionIfMissing(typesOfResidentalEstateCollection, undefined, null);
        expect(expectedResult).toEqual(typesOfResidentalEstateCollection);
      });
    });

    describe('compareTypesOfResidentalEstate', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTypesOfResidentalEstate(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTypesOfResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareTypesOfResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTypesOfResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareTypesOfResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTypesOfResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareTypesOfResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
