import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IClientAddress } from 'app/shared/model/client-address.model';
import { Principal } from 'app/core';
import { ClientAddressService } from './client-address.service';

@Component({
    selector: 'jhi-client-address',
    templateUrl: './client-address.component.html'
})
export class ClientAddressComponent implements OnInit, OnDestroy {
    clientAddresses: IClientAddress[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
 
    constructor(
        private clientAddressService: ClientAddressService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal, 
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : ''; 
    }

    loadAll() {
        if (this.currentSearch) {
            this.clientAddressService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IClientAddress[]>) => (this.clientAddresses = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.clientAddressService.query().subscribe(
            (res: HttpResponse<IClientAddress[]>) => {
                this.clientAddresses = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInClientAddresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IClientAddress) {
        return item.id;
    }

    registerChangeInClientAddresses() {
        this.eventSubscriber = this.eventManager.subscribe('clientAddressListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
