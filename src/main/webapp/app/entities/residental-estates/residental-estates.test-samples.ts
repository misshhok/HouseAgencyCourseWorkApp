import dayjs from 'dayjs/esm';

import { IResidentalEstates, NewResidentalEstates } from './residental-estates.model';

export const sampleWithRequiredData: IResidentalEstates = {
  id: 65429,
};

export const sampleWithPartialData: IResidentalEstates = {
  id: 18078,
  commissioningDate: dayjs('2023-05-30'),
  furnishType: 'Автомобиль',
  hasBalcony: true,
  bathroomType: 'свободно-распростроняемый конденсатор',
  ceilingHeight: 63613,
};

export const sampleWithFullData: IResidentalEstates = {
  id: 78313,
  livingSpace: 36962,
  cadastralNumber: 62902,
  commissioningDate: dayjs('2023-05-30'),
  numberOfRooms: 77819,
  furnishType: 'Большой Северная Сабо',
  hasBalcony: false,
  bathroomType: 'игры',
  ceilingHeight: 70586,
  price: 86632,
};

export const sampleWithNewData: NewResidentalEstates = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
