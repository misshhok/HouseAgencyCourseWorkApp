import { ICities, NewCities } from './cities.model';

export const sampleWithRequiredData: ICities = {
  id: 76794,
};

export const sampleWithPartialData: ICities = {
  id: 33151,
  title: 'Сейшельские',
};

export const sampleWithFullData: ICities = {
  id: 24484,
  title: 'infrastructures Checking action-items',
};

export const sampleWithNewData: NewCities = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
