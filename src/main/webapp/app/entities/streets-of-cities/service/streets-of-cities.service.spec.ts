import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStreetsOfCities } from '../streets-of-cities.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../streets-of-cities.test-samples';

import { StreetsOfCitiesService } from './streets-of-cities.service';

const requireRestSample: IStreetsOfCities = {
  ...sampleWithRequiredData,
};

describe('StreetsOfCities Service', () => {
  let service: StreetsOfCitiesService;
  let httpMock: HttpTestingController;
  let expectedResult: IStreetsOfCities | IStreetsOfCities[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StreetsOfCitiesService);
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

    it('should create a StreetsOfCities', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const streetsOfCities = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(streetsOfCities).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StreetsOfCities', () => {
      const streetsOfCities = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(streetsOfCities).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StreetsOfCities', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StreetsOfCities', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a StreetsOfCities', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStreetsOfCitiesToCollectionIfMissing', () => {
      it('should add a StreetsOfCities to an empty array', () => {
        const streetsOfCities: IStreetsOfCities = sampleWithRequiredData;
        expectedResult = service.addStreetsOfCitiesToCollectionIfMissing([], streetsOfCities);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(streetsOfCities);
      });

      it('should not add a StreetsOfCities to an array that contains it', () => {
        const streetsOfCities: IStreetsOfCities = sampleWithRequiredData;
        const streetsOfCitiesCollection: IStreetsOfCities[] = [
          {
            ...streetsOfCities,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStreetsOfCitiesToCollectionIfMissing(streetsOfCitiesCollection, streetsOfCities);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StreetsOfCities to an array that doesn't contain it", () => {
        const streetsOfCities: IStreetsOfCities = sampleWithRequiredData;
        const streetsOfCitiesCollection: IStreetsOfCities[] = [sampleWithPartialData];
        expectedResult = service.addStreetsOfCitiesToCollectionIfMissing(streetsOfCitiesCollection, streetsOfCities);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(streetsOfCities);
      });

      it('should add only unique StreetsOfCities to an array', () => {
        const streetsOfCitiesArray: IStreetsOfCities[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const streetsOfCitiesCollection: IStreetsOfCities[] = [sampleWithRequiredData];
        expectedResult = service.addStreetsOfCitiesToCollectionIfMissing(streetsOfCitiesCollection, ...streetsOfCitiesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const streetsOfCities: IStreetsOfCities = sampleWithRequiredData;
        const streetsOfCities2: IStreetsOfCities = sampleWithPartialData;
        expectedResult = service.addStreetsOfCitiesToCollectionIfMissing([], streetsOfCities, streetsOfCities2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(streetsOfCities);
        expect(expectedResult).toContain(streetsOfCities2);
      });

      it('should accept null and undefined values', () => {
        const streetsOfCities: IStreetsOfCities = sampleWithRequiredData;
        expectedResult = service.addStreetsOfCitiesToCollectionIfMissing([], null, streetsOfCities, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(streetsOfCities);
      });

      it('should return initial array if no StreetsOfCities is added', () => {
        const streetsOfCitiesCollection: IStreetsOfCities[] = [sampleWithRequiredData];
        expectedResult = service.addStreetsOfCitiesToCollectionIfMissing(streetsOfCitiesCollection, undefined, null);
        expect(expectedResult).toEqual(streetsOfCitiesCollection);
      });
    });

    describe('compareStreetsOfCities', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStreetsOfCities(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStreetsOfCities(entity1, entity2);
        const compareResult2 = service.compareStreetsOfCities(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStreetsOfCities(entity1, entity2);
        const compareResult2 = service.compareStreetsOfCities(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStreetsOfCities(entity1, entity2);
        const compareResult2 = service.compareStreetsOfCities(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
