import dayjs from 'dayjs/esm';

import { IComercialEventsOfResidentalEstate, NewComercialEventsOfResidentalEstate } from './comercial-events-of-residental-estate.model';

export const sampleWithRequiredData: IComercialEventsOfResidentalEstate = {
  id: 44056,
};

export const sampleWithPartialData: IComercialEventsOfResidentalEstate = {
  id: 98411,
  agentNotes: 'Optional улица Главный',
  dateOfEvent: dayjs('2023-05-30'),
};

export const sampleWithFullData: IComercialEventsOfResidentalEstate = {
  id: 74326,
  agentNotes: 'Fiji улица лимонный',
  dateOfEvent: dayjs('2023-05-30'),
};

export const sampleWithNewData: NewComercialEventsOfResidentalEstate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
