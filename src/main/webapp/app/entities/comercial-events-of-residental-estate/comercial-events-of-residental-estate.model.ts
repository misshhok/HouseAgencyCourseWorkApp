import dayjs from 'dayjs/esm';
import { ITypesOfCommercialEvents } from 'app/entities/types-of-commercial-events/types-of-commercial-events.model';
import { IClients } from 'app/entities/clients/clients.model';
import { IResidentalEstates } from 'app/entities/residental-estates/residental-estates.model';

export interface IComercialEventsOfResidentalEstate {
  id: number;
  agentNotes?: string | null;
  dateOfEvent?: dayjs.Dayjs | null;
  typeOfCommercialEventId?: Pick<ITypesOfCommercialEvents, 'id'> | null;
  clientId?: Pick<IClients, 'id'> | null;
  residentalEstateId?: Pick<IResidentalEstates, 'id'> | null;
}

export type NewComercialEventsOfResidentalEstate = Omit<IComercialEventsOfResidentalEstate, 'id'> & { id: null };
