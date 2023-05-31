export interface ICities {
  id: number;
  title?: string | null;
}

export type NewCities = Omit<ICities, 'id'> & { id: null };
