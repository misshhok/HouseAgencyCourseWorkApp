import { IStreetsOfCities } from 'app/entities/streets-of-cities/streets-of-cities.model';

export interface IAddresses {
  id: number;
  houseNumber?: number | null;
  streetOfCityId?: Pick<IStreetsOfCities, 'id'> | null;
}

export type NewAddresses = Omit<IAddresses, 'id'> & { id: null };
