import { IAddresses, NewAddresses } from './addresses.model';

export const sampleWithRequiredData: IAddresses = {
  id: 97445,
};

export const sampleWithPartialData: IAddresses = {
  id: 61113,
  houseNumber: 70484,
};

export const sampleWithFullData: IAddresses = {
  id: 69295,
  houseNumber: 49354,
};

export const sampleWithNewData: NewAddresses = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
