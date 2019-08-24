import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientAddress } from 'app/shared/model/client-address.model';

@Component({
    selector: 'jhi-client-address-detail',
    templateUrl: './client-address-detail.component.html'
})
export class ClientAddressDetailComponent implements OnInit {
    clientAddress: IClientAddress;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clientAddress }) => {
            this.clientAddress = clientAddress;
        });
    }

    previousState() {
        window.history.back();
    }
}
