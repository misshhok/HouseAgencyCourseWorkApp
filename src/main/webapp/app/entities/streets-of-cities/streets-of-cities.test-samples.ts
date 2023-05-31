import { IStreetsOfCities, NewStreetsOfCities } from './streets-of-cities.model';

export const sampleWithRequiredData: IStreetsOfCities = {
  id: 38941,
};

export const sampleWithPartialData: IStreetsOfCities = {
  id: 66387,
  title: 'СУБД Пермская',
};

export const sampleWithFullData: IStreetsOfCities = {
  id: 65214,
  title: 'transparent',
};

export const sampleWithNewData: NewStreetsOfCities = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
