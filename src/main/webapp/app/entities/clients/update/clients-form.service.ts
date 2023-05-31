import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IClients, NewClients } from '../clients.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IClients for edit and NewClientsFormGroupInput for create.
 */
type ClientsFormGroupInput = IClients | PartialWithRequiredKeyOf<NewClients>;

type ClientsFormDefaults = Pick<NewClients, 'id'>;

type ClientsFormGroupContent = {
  id: FormControl<IClients['id'] | NewClients['id']>;
  name: FormControl<IClients['name']>;
  surename: FormControl<IClients['surename']>;
  patronymic: FormControl<IClients['patronymic']>;
  phoneNumber: FormControl<IClients['phoneNumber']>;
};

export type ClientsFormGroup = FormGroup<ClientsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClientsFormService {
  createClientsFormGroup(clients: ClientsFormGroupInput = { id: null }): ClientsFormGroup {
    const clientsRawValue = {
      ...this.getFormDefaults(),
      ...clients,
    };
    return new FormGroup<ClientsFormGroupContent>({
      id: new FormControl(
        { value: clientsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(clientsRawValue.name),
      surename: new FormControl(clientsRawValue.surename),
      patronymic: new FormControl(clientsRawValue.patronymic),
      phoneNumber: new FormControl(clientsRawValue.phoneNumber),
    });
  }

  getClients(form: ClientsFormGroup): IClients | NewClients {
    return form.getRawValue() as IClients | NewClients;
  }

  resetForm(form: ClientsFormGroup, clients: ClientsFormGroupInput): void {
    const clientsRawValue = { ...this.getFormDefaults(), ...clients };
    form.reset(
      {
        ...clientsRawValue,
        id: { value: clientsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ClientsFormDefaults {
    return {
      id: null,
    };
  }
}
