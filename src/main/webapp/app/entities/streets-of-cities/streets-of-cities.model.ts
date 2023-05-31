import { ICities } from 'app/entities/cities/cities.model';

export interface IStreetsOfCities {
  id: number;
  title?: string | null;
  cityId?: Pick<ICities, 'id'> | null;
}

export type NewStreetsOfCities = Omit<IStreetsOfCities, 'id'> & { id: null };
