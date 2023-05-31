export interface IStatusesOfResidentalEstate {
  id: number;
  title?: string | null;
}

export type NewStatusesOfResidentalEstate = Omit<IStatusesOfResidentalEstate, 'id'> & { id: null };
