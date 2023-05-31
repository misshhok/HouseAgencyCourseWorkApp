import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICities } from '../cities.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cities.test-samples';

import { CitiesService } from './cities.service';

const requireRestSample: ICities = {
  ...sampleWithRequiredData,
};

describe('Cities Service', () => {
  let service: CitiesService;
  let httpMock: HttpTestingController;
  let expectedResult: ICities | ICities[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CitiesService);
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

    it('should create a Cities', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const cities = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cities).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Cities', () => {
      const cities = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cities).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Cities', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Cities', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Cities', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCitiesToCollectionIfMissing', () => {
      it('should add a Cities to an empty array', () => {
        const cities: ICities = sampleWithRequiredData;
        expectedResult = service.addCitiesToCollectionIfMissing([], cities);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cities);
      });

      it('should not add a Cities to an array that contains it', () => {
        const cities: ICities = sampleWithRequiredData;
        const citiesCollection: ICities[] = [
          {
            ...cities,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCitiesToCollectionIfMissing(citiesCollection, cities);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Cities to an array that doesn't contain it", () => {
        const cities: ICities = sampleWithRequiredData;
        const citiesCollection: ICities[] = [sampleWithPartialData];
        expectedResult = service.addCitiesToCollectionIfMissing(citiesCollection, cities);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cities);
      });

      it('should add only unique Cities to an array', () => {
        const citiesArray: ICities[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const citiesCollection: ICities[] = [sampleWithRequiredData];
        expectedResult = service.addCitiesToCollectionIfMissing(citiesCollection, ...citiesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cities: ICities = sampleWithRequiredData;
        const cities2: ICities = sampleWithPartialData;
        expectedResult = service.addCitiesToCollectionIfMissing([], cities, cities2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cities);
        expect(expectedResult).toContain(cities2);
      });

      it('should accept null and undefined values', () => {
        const cities: ICities = sampleWithRequiredData;
        expectedResult = service.addCitiesToCollectionIfMissing([], null, cities, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cities);
      });

      it('should return initial array if no Cities is added', () => {
        const citiesCollection: ICities[] = [sampleWithRequiredData];
        expectedResult = service.addCitiesToCollectionIfMissing(citiesCollection, undefined, null);
        expect(expectedResult).toEqual(citiesCollection);
      });
    });

    describe('compareCities', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCities(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCities(entity1, entity2);
        const compareResult2 = service.compareCities(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCities(entity1, entity2);
        const compareResult2 = service.compareCities(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCities(entity1, entity2);
        const compareResult2 = service.compareCities(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
