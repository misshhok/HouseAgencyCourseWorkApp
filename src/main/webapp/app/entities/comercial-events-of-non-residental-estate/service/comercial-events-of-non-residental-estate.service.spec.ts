import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IComercialEventsOfNonResidentalEstate } from '../comercial-events-of-non-residental-estate.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../comercial-events-of-non-residental-estate.test-samples';

import {
  ComercialEventsOfNonResidentalEstateService,
  RestComercialEventsOfNonResidentalEstate,
} from './comercial-events-of-non-residental-estate.service';

const requireRestSample: RestComercialEventsOfNonResidentalEstate = {
  ...sampleWithRequiredData,
  dateOfEvent: sampleWithRequiredData.dateOfEvent?.format(DATE_FORMAT),
};

describe('ComercialEventsOfNonResidentalEstate Service', () => {
  let service: ComercialEventsOfNonResidentalEstateService;
  let httpMock: HttpTestingController;
  let expectedResult: IComercialEventsOfNonResidentalEstate | IComercialEventsOfNonResidentalEstate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ComercialEventsOfNonResidentalEstateService);
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

    it('should create a ComercialEventsOfNonResidentalEstate', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const comercialEventsOfNonResidentalEstate = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(comercialEventsOfNonResidentalEstate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ComercialEventsOfNonResidentalEstate', () => {
      const comercialEventsOfNonResidentalEstate = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(comercialEventsOfNonResidentalEstate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ComercialEventsOfNonResidentalEstate', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ComercialEventsOfNonResidentalEstate', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ComercialEventsOfNonResidentalEstate', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addComercialEventsOfNonResidentalEstateToCollectionIfMissing', () => {
      it('should add a ComercialEventsOfNonResidentalEstate to an empty array', () => {
        const comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate = sampleWithRequiredData;
        expectedResult = service.addComercialEventsOfNonResidentalEstateToCollectionIfMissing([], comercialEventsOfNonResidentalEstate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comercialEventsOfNonResidentalEstate);
      });

      it('should not add a ComercialEventsOfNonResidentalEstate to an array that contains it', () => {
        const comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate = sampleWithRequiredData;
        const comercialEventsOfNonResidentalEstateCollection: IComercialEventsOfNonResidentalEstate[] = [
          {
            ...comercialEventsOfNonResidentalEstate,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addComercialEventsOfNonResidentalEstateToCollectionIfMissing(
          comercialEventsOfNonResidentalEstateCollection,
          comercialEventsOfNonResidentalEstate
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ComercialEventsOfNonResidentalEstate to an array that doesn't contain it", () => {
        const comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate = sampleWithRequiredData;
        const comercialEventsOfNonResidentalEstateCollection: IComercialEventsOfNonResidentalEstate[] = [sampleWithPartialData];
        expectedResult = service.addComercialEventsOfNonResidentalEstateToCollectionIfMissing(
          comercialEventsOfNonResidentalEstateCollection,
          comercialEventsOfNonResidentalEstate
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comercialEventsOfNonResidentalEstate);
      });

      it('should add only unique ComercialEventsOfNonResidentalEstate to an array', () => {
        const comercialEventsOfNonResidentalEstateArray: IComercialEventsOfNonResidentalEstate[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const comercialEventsOfNonResidentalEstateCollection: IComercialEventsOfNonResidentalEstate[] = [sampleWithRequiredData];
        expectedResult = service.addComercialEventsOfNonResidentalEstateToCollectionIfMissing(
          comercialEventsOfNonResidentalEstateCollection,
          ...comercialEventsOfNonResidentalEstateArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate = sampleWithRequiredData;
        const comercialEventsOfNonResidentalEstate2: IComercialEventsOfNonResidentalEstate = sampleWithPartialData;
        expectedResult = service.addComercialEventsOfNonResidentalEstateToCollectionIfMissing(
          [],
          comercialEventsOfNonResidentalEstate,
          comercialEventsOfNonResidentalEstate2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comercialEventsOfNonResidentalEstate);
        expect(expectedResult).toContain(comercialEventsOfNonResidentalEstate2);
      });

      it('should accept null and undefined values', () => {
        const comercialEventsOfNonResidentalEstate: IComercialEventsOfNonResidentalEstate = sampleWithRequiredData;
        expectedResult = service.addComercialEventsOfNonResidentalEstateToCollectionIfMissing(
          [],
          null,
          comercialEventsOfNonResidentalEstate,
          undefined
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comercialEventsOfNonResidentalEstate);
      });

      it('should return initial array if no ComercialEventsOfNonResidentalEstate is added', () => {
        const comercialEventsOfNonResidentalEstateCollection: IComercialEventsOfNonResidentalEstate[] = [sampleWithRequiredData];
        expectedResult = service.addComercialEventsOfNonResidentalEstateToCollectionIfMissing(
          comercialEventsOfNonResidentalEstateCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(comercialEventsOfNonResidentalEstateCollection);
      });
    });

    describe('compareComercialEventsOfNonResidentalEstate', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareComercialEventsOfNonResidentalEstate(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareComercialEventsOfNonResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareComercialEventsOfNonResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareComercialEventsOfNonResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareComercialEventsOfNonResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareComercialEventsOfNonResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareComercialEventsOfNonResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
