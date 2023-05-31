import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ClientsFormService, ClientsFormGroup } from './clients-form.service';
import { IClients } from '../clients.model';
import { ClientsService } from '../service/clients.service';

@Component({
  selector: 'jhi-clients-update',
  templateUrl: './clients-update.component.html',
})
export class ClientsUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClients | null = null;

  editForm: ClientsFormGroup = this.clientsFormService.createClientsFormGroup();

  constructor(
    protected clientsService: ClientsService,
    protected clientsFormService: ClientsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clients }) => {
      this.clients = clients;
      if (clients) {
        this.updateForm(clients);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const clients = this.clientsFormService.getClients(this.editForm);
    if (clients.id !== null) {
      this.subscribeToSaveResponse(this.clientsService.update(clients));
    } else {
      this.subscribeToSaveResponse(this.clientsService.create(clients));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClients>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(clients: IClients): void {
    this.clients = clients;
    this.clientsFormService.resetForm(this.editForm, clients);
  }
}
