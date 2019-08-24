import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IClientAddress } from 'app/shared/model/client-address.model';
import { ClientAddressService } from './client-address.service';

@Component({
    selector: 'jhi-client-address-update',
    templateUrl: './client-address-update.component.html'
})
export class ClientAddressUpdateComponent implements OnInit {
    private _clientAddress: IClientAddress;
    isSaving: boolean;
    clientId: any;

    constructor(private clientAddressService: ClientAddressService, private activatedRoute: ActivatedRoute) { }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ clientAddress }) => {
            this.clientAddress = clientAddress;
        });

        this._clientAddress.clientId = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['clientId']
            ? this.activatedRoute.snapshot.params['clientId']
            : '';
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.clientAddress.id !== undefined) {
            this.subscribeToSaveResponse(this.clientAddressService.update(this.clientAddress));
        } else {
            this.subscribeToSaveResponse(this.clientAddressService.create(this.clientAddress));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IClientAddress>>) {
        result.subscribe((res: HttpResponse<IClientAddress>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get clientAddress() {
        return this._clientAddress;
    }

    set clientAddress(clientAddress: IClientAddress) {
        this._clientAddress = clientAddress;
    }
}
