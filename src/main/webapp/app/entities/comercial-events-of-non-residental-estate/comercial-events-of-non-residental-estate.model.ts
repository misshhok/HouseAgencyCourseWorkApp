import dayjs from 'dayjs/esm';
import { ITypesOfCommercialEvents } from 'app/entities/types-of-commercial-events/types-of-commercial-events.model';
import { INonResidentalEstates } from 'app/entities/non-residental-estates/non-residental-estates.model';
import { IClients } from 'app/entities/clients/clients.model';

export interface IComercialEventsOfNonResidentalEstate {
  id: number;
  agentNotes?: string | null;
  dateOfEvent?: dayjs.Dayjs | null;
  typeOfCommercialEventId?: Pick<ITypesOfCommercialEvents, 'id'> | null;
  nonResidentalEstateId?: Pick<INonResidentalEstates, 'id'> | null;
  clientId?: Pick<IClients, 'id'> | null;
}

export type NewComercialEventsOfNonResidentalEstate = Omit<IComercialEventsOfNonResidentalEstate, 'id'> & { id: null };
