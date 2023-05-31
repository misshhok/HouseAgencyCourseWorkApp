import { IPurposesOfNonResidentalEstate, NewPurposesOfNonResidentalEstate } from './purposes-of-non-residental-estate.model';

export const sampleWithRequiredData: IPurposesOfNonResidentalEstate = {
  id: 1205,
};

export const sampleWithPartialData: IPurposesOfNonResidentalEstate = {
  id: 69714,
};

export const sampleWithFullData: IPurposesOfNonResidentalEstate = {
  id: 24775,
  title: 'task-force',
};

export const sampleWithNewData: NewPurposesOfNonResidentalEstate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
