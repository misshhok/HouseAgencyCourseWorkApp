export interface IBuildingTypeOfNonResidentalEstate {
  id: number;
  title?: string | null;
}

export type NewBuildingTypeOfNonResidentalEstate = Omit<IBuildingTypeOfNonResidentalEstate, 'id'> & { id: null };
