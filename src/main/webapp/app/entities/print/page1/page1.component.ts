import {Component, Input, OnInit} from '@angular/core';
import {IQuotation} from "../../../shared/model/quotation.model";
import {IClients} from "../../../shared/model/clients.model";
import {ClientAddressService} from "../../client-address/client-address.service";
import {ClientAddress, IClientAddress} from "../../../shared/model/client-address.model";

@Component({
    selector: 'app-page1',
    templateUrl: './page1.component.html',
    styleUrls: ['./page1.component.css']
})
export class Page1Component implements OnInit {
    @Input('quotation') quotation: IQuotation;
    @Input('client') client: IClients;
    private systemSize: number;
    private inv: string;
    private addresses: IClientAddress;
    private systemCapacity;


    constructor(private clientAddressService: ClientAddressService) {

    }

    ngOnInit() {

        this.addresses= new ClientAddress('','','','',0);
        this.getAllAddresses(this.quotation.addressId);
        this.systemSize = this.quotation.inverterCapacity * this.quotation.numberOfInverters + this.quotation.inverterCapacity2 * this.quotation.numberOfInverters2;
        if (this.quotation.inverterCapacity2) {
            this.inv = this.quotation.inverterCapacity + ' * ' + this.quotation.numberOfInverters + ' + ' + this.quotation.inverterCapacity2 + ' * ' + this.quotation.numberOfInverters2;
        } else {
            this.inv = this.quotation.inverterCapacity + ' * ' + this.quotation.numberOfInverters;

        }


    }

    getAllAddresses(id) {

        this.clientAddressService.find(id).subscribe(res => {
            console.log(JSON.stringify(res));
            this.addresses = res.body;
        });
    }

}
