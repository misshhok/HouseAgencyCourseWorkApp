import { ITypesOfResidentalEstate, NewTypesOfResidentalEstate } from './types-of-residental-estate.model';

export const sampleWithRequiredData: ITypesOfResidentalEstate = {
  id: 98632,
};

export const sampleWithPartialData: ITypesOfResidentalEstate = {
  id: 85903,
  title: 'Checking withdrawal channels',
};

export const sampleWithFullData: ITypesOfResidentalEstate = {
  id: 7883,
  title: 'Натуральный конденсатор',
};

export const sampleWithNewData: NewTypesOfResidentalEstate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
