import { ITypesOfCommercialEvents, NewTypesOfCommercialEvents } from './types-of-commercial-events.model';

export const sampleWithRequiredData: ITypesOfCommercialEvents = {
  id: 20131,
};

export const sampleWithPartialData: ITypesOfCommercialEvents = {
  id: 94008,
  estateType: 'payment скачивание',
};

export const sampleWithFullData: ITypesOfCommercialEvents = {
  id: 40888,
  title: 'Потрясающий Account маркетинговый',
  description: 'reinvent проспект',
  estateType: 'пиксель Palladium пл.',
};

export const sampleWithNewData: NewTypesOfCommercialEvents = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
