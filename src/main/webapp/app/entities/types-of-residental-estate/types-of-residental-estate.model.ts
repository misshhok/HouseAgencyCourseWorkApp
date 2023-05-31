export interface ITypesOfResidentalEstate {
  id: number;
  title?: string | null;
}

export type NewTypesOfResidentalEstate = Omit<ITypesOfResidentalEstate, 'id'> & { id: null };
