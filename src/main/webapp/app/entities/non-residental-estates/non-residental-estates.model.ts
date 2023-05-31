import dayjs from 'dayjs/esm';
import { IPurposesOfNonResidentalEstate } from 'app/entities/purposes-of-non-residental-estate/purposes-of-non-residental-estate.model';
import { IBuildingTypeOfNonResidentalEstate } from 'app/entities/building-type-of-non-residental-estate/building-type-of-non-residental-estate.model';
import { IAddresses } from 'app/entities/addresses/addresses.model';

export interface INonResidentalEstates {
  id: number;
  price?: number | null;
  commissioningDate?: dayjs.Dayjs | null;
  cadastralNumber?: number | null;
  totalSpace?: number | null;
  purposeOfNonResidentalEstateId?: Pick<IPurposesOfNonResidentalEstate, 'id'> | null;
  buildingTypeOfNonResidentalEstateId?: Pick<IBuildingTypeOfNonResidentalEstate, 'id'> | null;
  addressId?: Pick<IAddresses, 'id'> | null;
}

export type NewNonResidentalEstates = Omit<INonResidentalEstates, 'id'> & { id: null };
