import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {IClients} from 'app/shared/model/clients.model';
import {ClientAddressService} from "../client-address/client-address.service";
import {QuotationService} from "../quotation/quotation.service";
import {IQuotation} from "../../shared/model/quotation.model";

@Component({
    selector: 'jhi-clients-detail',
    templateUrl: './clients-detail.component.html'
})
export class ClientsDetailComponent implements OnInit {
    clients: IClients;
    addresses;
    private issuedQuotations = [];
    dataModalForClientAddressAndQuotations: ClientQuotationDataModal;

    constructor(private activatedRoute: ActivatedRoute, private clientAddresses: ClientAddressService,
                private quotationService: QuotationService,) {
        this.dataModalForClientAddressAndQuotations = new ClientQuotationDataModal();
        this.activatedRoute.data.subscribe(({clients}) => {
            this.clients = clients;
            this.getAllAddresses(clients.id);
        });
        console.log(this.clients.id);
    }

    ngOnInit() {

    }

    previousState() {
        window.history.back();
    }

    getAllAddresses(id) {

        this.clientAddresses.getAllAddressesByClientId(id).subscribe(addresses => {
            // console.log(JSON.stringify(res));
            this.addresses = addresses;

            for (let i = 0; i < this.addresses.length; i++) {
                this.quotationService.getQuotationByAddressId(this.addresses[i].id).subscribe(quotations => {
                    // console.log(JSON.stringify(res.body));




                    this.issuedQuotations.push({
                        'address': addresses,
                        'quotation': quotations.body
                    });
                    console.log(JSON.stringify(this.issuedQuotations));
                });
            }



        });

    }

    getQuotationId(body, id) {

        var list = [];
        for (let quotation of body) {
            if (id === quotation.addressId) {

                list.push(quotation);


            }
        }
        console.log(JSON.stringify(list));
        return list;

    }
}

class ClientQuotationDataModal {
    constructor(public id?: string, public clientId?: string, public address?: string, public city?: string, public postalCode?: number,
                public quotation?: IQuotation[]) {
    }

}
