import dayjs from 'dayjs/esm';
import { IAddresses } from 'app/entities/addresses/addresses.model';
import { ITypesOfResidentalEstate } from 'app/entities/types-of-residental-estate/types-of-residental-estate.model';
import { IStatusesOfResidentalEstate } from 'app/entities/statuses-of-residental-estate/statuses-of-residental-estate.model';

export interface IResidentalEstates {
  id: number;
  livingSpace?: number | null;
  cadastralNumber?: number | null;
  commissioningDate?: dayjs.Dayjs | null;
  numberOfRooms?: number | null;
  furnishType?: string | null;
  hasBalcony?: boolean | null;
  bathroomType?: string | null;
  ceilingHeight?: number | null;
  price?: number | null;
  addressId?: Pick<IAddresses, 'id'> | null;
  typeOfResidentalEstateId?: Pick<ITypesOfResidentalEstate, 'id'> | null;
  statusOfResidentalEstateId?: Pick<IStatusesOfResidentalEstate, 'id'> | null;
}

export type NewResidentalEstates = Omit<IResidentalEstates, 'id'> & { id: null };
