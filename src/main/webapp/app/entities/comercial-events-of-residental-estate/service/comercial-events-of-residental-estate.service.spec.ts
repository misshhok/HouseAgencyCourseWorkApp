import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IComercialEventsOfResidentalEstate } from '../comercial-events-of-residental-estate.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../comercial-events-of-residental-estate.test-samples';

import {
  ComercialEventsOfResidentalEstateService,
  RestComercialEventsOfResidentalEstate,
} from './comercial-events-of-residental-estate.service';

const requireRestSample: RestComercialEventsOfResidentalEstate = {
  ...sampleWithRequiredData,
  dateOfEvent: sampleWithRequiredData.dateOfEvent?.format(DATE_FORMAT),
};

describe('ComercialEventsOfResidentalEstate Service', () => {
  let service: ComercialEventsOfResidentalEstateService;
  let httpMock: HttpTestingController;
  let expectedResult: IComercialEventsOfResidentalEstate | IComercialEventsOfResidentalEstate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ComercialEventsOfResidentalEstateService);
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

    it('should create a ComercialEventsOfResidentalEstate', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const comercialEventsOfResidentalEstate = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(comercialEventsOfResidentalEstate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ComercialEventsOfResidentalEstate', () => {
      const comercialEventsOfResidentalEstate = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(comercialEventsOfResidentalEstate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ComercialEventsOfResidentalEstate', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ComercialEventsOfResidentalEstate', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ComercialEventsOfResidentalEstate', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addComercialEventsOfResidentalEstateToCollectionIfMissing', () => {
      it('should add a ComercialEventsOfResidentalEstate to an empty array', () => {
        const comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate = sampleWithRequiredData;
        expectedResult = service.addComercialEventsOfResidentalEstateToCollectionIfMissing([], comercialEventsOfResidentalEstate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comercialEventsOfResidentalEstate);
      });

      it('should not add a ComercialEventsOfResidentalEstate to an array that contains it', () => {
        const comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate = sampleWithRequiredData;
        const comercialEventsOfResidentalEstateCollection: IComercialEventsOfResidentalEstate[] = [
          {
            ...comercialEventsOfResidentalEstate,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addComercialEventsOfResidentalEstateToCollectionIfMissing(
          comercialEventsOfResidentalEstateCollection,
          comercialEventsOfResidentalEstate
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ComercialEventsOfResidentalEstate to an array that doesn't contain it", () => {
        const comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate = sampleWithRequiredData;
        const comercialEventsOfResidentalEstateCollection: IComercialEventsOfResidentalEstate[] = [sampleWithPartialData];
        expectedResult = service.addComercialEventsOfResidentalEstateToCollectionIfMissing(
          comercialEventsOfResidentalEstateCollection,
          comercialEventsOfResidentalEstate
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comercialEventsOfResidentalEstate);
      });

      it('should add only unique ComercialEventsOfResidentalEstate to an array', () => {
        const comercialEventsOfResidentalEstateArray: IComercialEventsOfResidentalEstate[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const comercialEventsOfResidentalEstateCollection: IComercialEventsOfResidentalEstate[] = [sampleWithRequiredData];
        expectedResult = service.addComercialEventsOfResidentalEstateToCollectionIfMissing(
          comercialEventsOfResidentalEstateCollection,
          ...comercialEventsOfResidentalEstateArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate = sampleWithRequiredData;
        const comercialEventsOfResidentalEstate2: IComercialEventsOfResidentalEstate = sampleWithPartialData;
        expectedResult = service.addComercialEventsOfResidentalEstateToCollectionIfMissing(
          [],
          comercialEventsOfResidentalEstate,
          comercialEventsOfResidentalEstate2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comercialEventsOfResidentalEstate);
        expect(expectedResult).toContain(comercialEventsOfResidentalEstate2);
      });

      it('should accept null and undefined values', () => {
        const comercialEventsOfResidentalEstate: IComercialEventsOfResidentalEstate = sampleWithRequiredData;
        expectedResult = service.addComercialEventsOfResidentalEstateToCollectionIfMissing(
          [],
          null,
          comercialEventsOfResidentalEstate,
          undefined
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comercialEventsOfResidentalEstate);
      });

      it('should return initial array if no ComercialEventsOfResidentalEstate is added', () => {
        const comercialEventsOfResidentalEstateCollection: IComercialEventsOfResidentalEstate[] = [sampleWithRequiredData];
        expectedResult = service.addComercialEventsOfResidentalEstateToCollectionIfMissing(
          comercialEventsOfResidentalEstateCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(comercialEventsOfResidentalEstateCollection);
      });
    });

    describe('compareComercialEventsOfResidentalEstate', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareComercialEventsOfResidentalEstate(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareComercialEventsOfResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareComercialEventsOfResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareComercialEventsOfResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareComercialEventsOfResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareComercialEventsOfResidentalEstate(entity1, entity2);
        const compareResult2 = service.compareComercialEventsOfResidentalEstate(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
