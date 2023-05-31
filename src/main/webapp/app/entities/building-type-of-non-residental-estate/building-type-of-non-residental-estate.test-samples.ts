import { IBuildingTypeOfNonResidentalEstate, NewBuildingTypeOfNonResidentalEstate } from './building-type-of-non-residental-estate.model';

export const sampleWithRequiredData: IBuildingTypeOfNonResidentalEstate = {
  id: 29528,
};

export const sampleWithPartialData: IBuildingTypeOfNonResidentalEstate = {
  id: 58143,
  title: 'PNG Reduced Бутан',
};

export const sampleWithFullData: IBuildingTypeOfNonResidentalEstate = {
  id: 33680,
  title: 'Брянская',
};

export const sampleWithNewData: NewBuildingTypeOfNonResidentalEstate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
