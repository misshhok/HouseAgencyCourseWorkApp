import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBuildingTypeOfNonResidentalEstate } from '../building-type-of-non-residental-estate.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../building-type-of-non-residental-estate.test-samples';

import { BuildingTypeOfNonResidentalEstateService } from './building-type-of-non-residental-estate.service';

const requireRestSample: IBuildingTypeOfNonResidentalEstate = {
  ...sampleWithRequiredData,
};

describe('BuildingTypeOfNonResidentalEstate Service', () => {
  let service: BuildingTypeOfNonResidentalEstateService;
  let httpMock: HttpTestingController;
  let expectedResult: IBuildingTypeOfNonResidentalEstate | IBuildingTypeOfNonResidentalEstate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BuildingTypeOfNonResidentalEstateService);
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

    it('should create a BuildingTypeOfNonResidentalEstate', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const buildingTypeOfNonResidentalEstate = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(buildingTypeOfNonResidentalEstate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BuildingTypeOfNonResidentalEstate', () => {
      const buildingTypeOfNonResidentalEstate = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(buildingTypeOfNonResidentalEstate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BuildingTypeOfNonResidentalEstate', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BuildingTypeOfNonResidentalEstate', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BuildingTypeOfNonResidentalEstate', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBuildingTypeOfNonResidentalEstateToCollectionIfMissing', () => {
      it('should add a BuildingTypeOfNonResidentalEstate to an empty array', () => {
        const buildingTypeOfNonResidentalEstate: IBuildingTypeOfNonResidentalEstate = sampleWithRequiredData;
        expectedResult = service.addBuildingTypeOfNonResidentalEstateToCollectionIfMissing([], buildingTypeOfNonResidentalEstate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(buildingTypeOfNonResidentalEstate);
      });

      it('should not add a BuildingTypeOfNonResidentalEstate to an array that contains it', () => {
        const buildingTypeOfNonResidentalEstate: IBuildingTypeOfNonResidentalEstate = sampleWithRequiredData;
        const buildingTypeOfNonResidentalEstateCollection: IBuildingTypeOfNonResidentalEstate[] = [
          {
            ...buildingTypeOfNonResidentalEstate,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBuildingTypeOfNonResidentalEstateToCollectionIfMissing(
          buildingTypeOfNonResidentalEstateCollection,
          buildingTypeOfNonResidentalEstate
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BuildingTypeOfNonResidentalEstate to an array that doesn't contain it", () => {
        const buildingTypeOfNonResidentalEstate: IBuildingTypeOfNonResidentalEstate = sampleWithRequiredData;
        const buildingTypeOfNonResidentalEstateCollection: IBuildingTypeOfNonResidentalEstate[] = [sampleWithPartialData];
        expectedResult = service.addBuildingTypeOfNonResidentalEstateToCollectionIfMissing(
          buildingTypeOfNonResidentalEstateCollection,
          buildingTypeOfNonResidentalEstate
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(buildingTypeOfNonResidentalEstate);
      });

      it('should add only unique BuildingTypeOfNonResidentalEstate to an array', () => {
        const buildingTypeOfNonResidentalEstateArray: IBuildingTypeOfNonResidentalEstate[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const buildingTypeOfNonResidentalEstateCollection: IBuildingTypeOfNonResidentalEstate[] = [sampleWithRequiredData];
        expectedResult = service.addBuildingTypeOfNonResidentalEstateToCollectionIfMissing(
          buildingTypeOfNonResidentalEstateCollection,
          ...buildingTypeOfNonResidentalEstateArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const buildingTypeOfNonResidentalEstate: IBuildingTypeOfNonResidentalEstate = sampleWithRequiredData;
        const buildingTypeOfNonResidentalEstate2: IBuildingTypeOfNonResidentalEstate = sampleWithPartialData;
        expectedResult = service.addBuildingTypeOfNonResidentalEstateToCollectionIfMissing(
          [],
          buildingTypeOfNonResidentalEstate,
          buildingTypeOfNonResidentalEstate2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(buildingTypeOfNonResidentalEstate);
        expect(expectedResult).toContain(buildingTypeOfNonResidentalEstate2);
      });

      it('should accept null and undefined values', () => {
        const buildingTypeOfNonResidentalEstate: IBuildingTypeOfNonResidentalEstate = sampleWithRequiredData;
        expectedResult = service.addBuildingTypeOfNonResidentalEstateToCollectionIfMissing(
          [],
          null,
          buildingTypeOfNonResidentalEstate,
          undefined
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(buildingTypeOfNonResidentalEstate);
      });

      it('should return initial array if no BuildingTypeOfNonResidentalEstate is added', () => {
        const buildingTypeOfNonResidentalEstateCollection: IBuildingTypeOfNonResidentalEstate[] = [sampleWithRequiredData];
        expectedResult = service.addBuildingTypeOfNonResidentalEstateToCollectionIfMissing(
          buildingTypeOfNonResidentalEstateCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(buildingTypeOfNonResidentalEstateCollection);
      });
    });

    describe('compareBuildingTypeOfNonResidentalEstate', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBuildingTypeOfNonResidentalEstate(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBuildingTypeOfNonResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareBuildingTypeOfNonResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBuildingTypeOfNonResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareBuildingTypeOfNonResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBuildingTypeOfNonResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareBuildingTypeOfNonResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
