import { IClients, NewClients } from './clients.model';

export const sampleWithRequiredData: IClients = {
  id: 65832,
};

export const sampleWithPartialData: IClients = {
  id: 46359,
};

export const sampleWithFullData: IClients = {
  id: 65259,
  name: 'deposit',
  surename: 'Региональный impactful',
  patronymic: 'компьютеры зеленый 24/7',
  phoneNumber: 'Card Стальной Кожанный',
};

export const sampleWithNewData: NewClients = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
