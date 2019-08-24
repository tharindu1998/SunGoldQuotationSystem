import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {IQuotation} from '../../../shared/model/quotation.model';
import {ClientsService} from '../../clients/clients.service';
import {Clients, Designation, IClients} from '../../../shared/model/clients.model';
import {ClientAddressService} from '../../client-address/client-address.service';
import * as moment from 'moment';
import {ClientAddress} from '../../../shared/model/client-address.model';

@Component({
    selector: 'jhi-cost-print',
    templateUrl: './cost-print.component.html',
    styleUrls: ['./cost-print.component.css']
})
export class CostPrintComponent implements OnInit {
    quotation: IQuotation;
    private client: Clients;
    private designation: Designation;
    private clientAddress: any;

    constructor(private activatedRoute: ActivatedRoute,
                private clientsService: ClientsService,
                private clientAddresses: ClientAddressService) {
        this.client = new Clients('', null, '', '', <Designation>'', '', '');
        this.clientAddress = new ClientAddress('', '', '', '', 0);

    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({quotation}) => {
            this.quotation = quotation;

            this.getClientInfo();
            this.getClientAddress();

        });

    }

    getClientInfo() {
        this.clientsService.find(this.quotation.clientId).subscribe(res => {
            this.client = res.body;
            // this.designation = this.client.designation;
            console.log(this.client);

        });
    }

    getClientAddress() {
        this.clientAddresses.find(this.quotation.addressId).subscribe(res => {
            this.clientAddress = res.body;
            // this.designation = this.client.designation;
            console.log(this.clientAddress);

        });

    }

}
