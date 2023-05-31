import dayjs from 'dayjs/esm';

import { INonResidentalEstates, NewNonResidentalEstates } from './non-residental-estates.model';

export const sampleWithRequiredData: INonResidentalEstates = {
  id: 86955,
};

export const sampleWithPartialData: INonResidentalEstates = {
  id: 92334,
  cadastralNumber: 3380,
  totalSpace: 73316,
};

export const sampleWithFullData: INonResidentalEstates = {
  id: 39041,
  price: 33229,
  commissioningDate: dayjs('2023-05-30'),
  cadastralNumber: 32151,
  totalSpace: 74936,
};

export const sampleWithNewData: NewNonResidentalEstates = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
