export interface ITypesOfCommercialEvents {
  id: number;
  title?: string | null;
  description?: string | null;
  estateType?: string | null;
}

export type NewTypesOfCommercialEvents = Omit<ITypesOfCommercialEvents, 'id'> & { id: null };
