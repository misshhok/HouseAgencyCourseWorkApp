import { IStatusesOfResidentalEstate, NewStatusesOfResidentalEstate } from './statuses-of-residental-estate.model';

export const sampleWithRequiredData: IStatusesOfResidentalEstate = {
  id: 1202,
};

export const sampleWithPartialData: IStatusesOfResidentalEstate = {
  id: 29880,
};

export const sampleWithFullData: IStatusesOfResidentalEstate = {
  id: 87110,
  title: 'Savings',
};

export const sampleWithNewData: NewStatusesOfResidentalEstate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
