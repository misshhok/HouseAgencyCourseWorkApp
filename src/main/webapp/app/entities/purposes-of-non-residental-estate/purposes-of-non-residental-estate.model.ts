export interface IPurposesOfNonResidentalEstate {
  id: number;
  title?: string | null;
}

export type NewPurposesOfNonResidentalEstate = Omit<IPurposesOfNonResidentalEstate, 'id'> & { id: null };
