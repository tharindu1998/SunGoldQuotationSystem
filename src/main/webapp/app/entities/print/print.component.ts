import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {IQuotation} from "../../shared/model/quotation.model";
import {ClientAddressService} from "../client-address/client-address.service";
import {ClientsService} from "../clients/clients.service";
import {Clients, Designation, IClients} from "../../shared/model/clients.model";
import {InvertersService} from "../inverters/inverters.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {IInverters} from "../../shared/model/inverters.model";
import {JhiAlertService} from "ng-jhipster";
import moment = require("moment");

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class PrintComponent implements OnInit {

    title = 'Printable';
    quotation: IQuotation;
    readyToPrint = true;
    quotationId = '';
    private client: Clients;
    private systemSize: any;
    private inverters: IInverters[];
    private designation='';

    constructor(private activatedRoute: ActivatedRoute,
                private clientAddresses: ClientAddressService,
                private clientsService: ClientsService, private jhiAlertService: JhiAlertService,
                private invertersService: InvertersService,) {

        this.client = new Clients('', <moment.Moment>'', '', '', <Designation>'', '', '');
        this.activatedRoute.data.subscribe(({quotation}) => {
            this.quotation = quotation;
            // this.quotationId = quotation.id;
            this.readyToPrint = false;
            this.getClientInfo();

        });
        this.invertersService.query().subscribe(
            (res: HttpResponse<IInverters[]>) => {
                this.inverters = res.body;
                this.getSystemSIze();

            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit(): void {


    }

    getClientInfo() {
        this.clientsService.find(this.quotation.clientId).subscribe(res => {
            this.client = res.body;
            this.designation= String( this.client.designation );
            // alert(JSON.stringify(this.client))
        })
    }

    getClientAddress() {

    }


    private getSystemSIze() {

        this.systemSize = this.quotation.inverterCapacity*this.quotation.numberOfInverters + this.quotation.inverterCapacity2*this.quotation.numberOfInverters2;

    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }


}
