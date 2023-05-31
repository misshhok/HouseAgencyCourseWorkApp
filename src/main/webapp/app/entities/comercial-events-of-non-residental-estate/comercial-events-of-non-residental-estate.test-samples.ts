import dayjs from 'dayjs/esm';

import {
  IComercialEventsOfNonResidentalEstate,
  NewComercialEventsOfNonResidentalEstate,
} from './comercial-events-of-non-residental-estate.model';

export const sampleWithRequiredData: IComercialEventsOfNonResidentalEstate = {
  id: 75648,
};

export const sampleWithPartialData: IComercialEventsOfNonResidentalEstate = {
  id: 6371,
  dateOfEvent: dayjs('2023-05-30'),
};

export const sampleWithFullData: IComercialEventsOfNonResidentalEstate = {
  id: 35001,
  agentNotes: 'Чеченская e-services',
  dateOfEvent: dayjs('2023-05-31'),
};

export const sampleWithNewData: NewComercialEventsOfNonResidentalEstate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
