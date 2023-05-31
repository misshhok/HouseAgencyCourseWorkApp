export interface IClients {
  id: number;
  name?: string | null;
  surename?: string | null;
  patronymic?: string | null;
  phoneNumber?: string | null;
}

export type NewClients = Omit<IClients, 'id'> & { id: null };
