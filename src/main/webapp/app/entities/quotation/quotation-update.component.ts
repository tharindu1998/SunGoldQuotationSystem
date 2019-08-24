import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpResponse, HttpErrorResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

import {IQuotation} from 'app/shared/model/quotation.model';
import {QuotationService} from './quotation.service';
import {InvertersService} from "../inverters/inverters.service";
import {IInverters} from "../../shared/model/inverters.model";
import {JhiAlertService} from "ng-jhipster";
import {FormGroup} from "@angular/forms";
import {PanelsService} from "../panels/panels.service";
import {IPanels} from "../../shared/model/panels.model";
import * as moment from "moment";
import {UserService} from "../../core/user/user.service";
import {IUser} from "../../core/user/user.model";

@Component({
    selector: 'jhi-quotation-update',
    templateUrl: './quotation-update.component.html'
})
export class QuotationUpdateComponent implements OnInit {
    private _quotation: IQuotation;
    isSaving = false;
    moment: moment;
    @ViewChild('editForm') form;
    private inverters: any | IInverters[];
    private inverterBrands = [];
    private filteredInverterModels = [];
    private inverterFilledWithData = false;

    private panelsFilledWithData = false;
    private panels: any | IPanels[];
    private panelBrands = [];
    private filteredPanelModels = [];

    private panelTotalPrice = 0;
    private panelModel: any;
    private panelSize: any;
    readyToPrint = true;
    private quotationId: string;
    private users: any | IUser[];
    private usersReady = false;

    constructor(private quotationService: QuotationService,
                private activatedRoute: ActivatedRoute,
                private invertersService: InvertersService,
                private jhiAlertService: JhiAlertService,
                private panelsService: PanelsService,
                private router: Router,
                private  userService: UserService) {

        this.activatedRoute.data.subscribe(({quotation}) => {
            this.quotation = quotation;
            this.quotationId = quotation.id;
            this.panelModel = quotation.panelModel;
            this.panelTotalPrice = quotation.panelTotalPrice;
            this.readyToPrint = true;
            alert(this.panelTotalPrice);
        });
    }

    ngOnInit() {


        this.isSaving = false;


        if (!this.quotation.id) {
            this.quotation.clientId = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['clientId']
                ? this.activatedRoute.snapshot.params['clientId']
                : '';

            this.quotation.addressId = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['addressId']
                ? this.activatedRoute.snapshot.params['addressId']
                : '';
        }

        this.invertersService.query().subscribe(
            (res: HttpResponse<IInverters[]>) => {
                this.inverters = res.body;
                for (let x of this.inverters) {
                    this.inverterBrands.push(x.inverterBrand);
                }
                this.inverterBrands = this.inverterBrands.filter((value, index, array) =>
                    !array.filter((v, i) => JSON.stringify(value) === JSON.stringify(v) && i < index).length);
                this.inverterFilledWithData = true;
                this.onFormChange();

            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.panelsService.query().subscribe((res: HttpResponse<IPanels[]>) => {
                this.panels = res.body;
                for (let x of this.panels) {
                    this.panelBrands.push(x.palenBrand);
                }
                this.panelBrands = this.panelBrands.filter((value, index, array) =>
                    !array.filter((v, i) => JSON.stringify(value) === JSON.stringify(v) && i < index).length);
                this.panelsFilledWithData = true;
                this.onFormChange();


            },
            (res: HttpErrorResponse) => this.onError(res.message));

        this.userService.query().subscribe((res: HttpResponse<IUser[]>) => {
                this.users = res.body;
                this.usersReady = true;
                this.onFormChange();
                // alert(JSON.stringify(this.users))
            }
        );


        this.form.valueChanges.subscribe(res => {

            this._quotation.areaNeeded = this._quotation.numberofPanels * 24;
            this._quotation.unitsGenerates = Number(parseFloat((this._quotation.numberofPanels) * (30 * 3.8 * (this._quotation.panelCapacity / 1000))).toFixed(2));

        });


    }


    onFormChange() {

        if (this.inverterFilledWithData === true) {
            this.filteredInverterModels = [];
            for (let x of  this.inverters) {
                if (this.quotation.inverterBrand === x.inverterBrand) {
                    this.filteredInverterModels.push(x);
                }
            }

        }

        if (this.panelsFilledWithData === true) {
            this.filteredPanelModels = [];
            for (let x of  this.panels) {
                if (this.quotation.panelBrand === x.palenBrand) {
                    this.filteredPanelModels.push(x);
                }
            }

        }

        this.quotation.inverterPrice = this.setInverterPrice();
        this.quotation.panelPrice = this.setPanelTotalPrice();
        this.quotation.systemPrice = this.setSystemTotalPrice();

        if (this.usersReady)
            for (let user of this.users) {

                if (this.quotation.issuedBy)
                    if (user.firstName + ' ' + user.lastName === this.quotation.issuedBy.trim()) {

                        this.quotation.issuersTelephone = String(user.telephone);
                    }
            }
        this._quotation.areaNeeded = this._quotation.numberofPanels * 24;
        this._quotation.unitsGenerates = Number(parseFloat((this._quotation.numberofPanels) * (30 * 3.8 * (this._quotation.panelCapacity / 1000))).toFixed(2));


    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.readyToPrint = true;
        if (this.quotation.id !== undefined) {
            this.subscribeToSaveResponse(this.quotationService.update(this.quotation));
        } else {
            this.subscribeToSaveResponse(this.quotationService.create(this.quotation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuotation>>) {
        result.subscribe((res: HttpResponse<IQuotation>) => {

            // alert(res.body.id);
            this.quotationId = res.body.id;
            this.readyToPrint = false;

            this.router.navigate(['/quotation/'+ this.quotationId +'/view'])


            // this.onSaveSuccess();
        }, (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    get quotation() {
        return this._quotation;
    }

    set quotation(quotation: IQuotation) {
        this._quotation = quotation;
    }

    numberOfPanelsChanged(obj) {
        this._quotation.systemSize = (this._quotation.numberofPanels * this._quotation.panelCapacity) / 1000;
        this._quotation.areaNeeded = this._quotation.numberofPanels * 24;
    }

    systemSizeChanged(obj) {
        this._quotation.numberofPanels = Number(parseFloat((this._quotation.systemSize * 1000) / this._quotation.panelCapacity).toFixed(0));
    }

    panelModelChange($event) {
        let parts = $event.split(',');
        this.quotation.panelCapacity = parts[1];
        this.panelModel = $event.split(',')[0];
        this.panelSize = $event.split(',')[1];
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    private setPanelTotalPrice() {


        let priceForOnePanel = this.panelTotalPrice;
        if (this.panels)

            for (let x of this.panels) {
                console.log(this.panelModel + '  ' + x.panelModel);
                if (x.panelModel == this.panelModel && x.panelSize == this.quotation.panelCapacity) {
                    priceForOnePanel = x.panelPrice * this.quotation.numberofPanels;

                }
            }


        return priceForOnePanel;

    }

    private setInverterPrice() {
        let priceForOnePanel = 0;
        if (this.inverters)
            for (let x of this.inverters) {
                if (x.inverterModel == this.quotation.inverterModel && x.inverterBrand == this.quotation.inverterBrand) {
                    priceForOnePanel = x.inverterPrice * this.quotation.numberOfInverters;
                    this.quotation.inverterCapacity = x.inverterSize;
                    console.log('found');
                }
                if (x.inverterModel == this.quotation.inverterModel2 && x.inverterBrand == this.quotation.inverterBrand) {
                    priceForOnePanel += x.inverterPrice * this.quotation.numberOfInverters2;
                    this.quotation.inverterCapacity2 = x.inverterSize;
                    console.log('found');
                }
            }
        return priceForOnePanel;

    }

    private setSystemTotalPrice() {
        let total = 0;
        // alert('total')
        total = ( Number(this.quotation.panelPrice) + Number(this.quotation.inverterPrice) + Number(this.quotation.constructionPrice)  );
        console.log('price: ' + total);
        total += ((total) * Number(this.quotation.profit)) / 100;
        total += ((total) * Number(this.quotation.commission)) / 100;

        return total;
    }
}
