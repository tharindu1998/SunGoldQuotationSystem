import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClientAddress } from 'app/shared/model/client-address.model';
import { ClientAddressService } from './client-address.service';

@Component({
    selector: 'jhi-client-address-delete-dialog',
    templateUrl: './client-address-delete-dialog.component.html'
})
export class ClientAddressDeleteDialogComponent {
    clientAddress: IClientAddress;

    constructor(
        private clientAddressService: ClientAddressService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.clientAddressService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'clientAddressListModification',
                content: 'Deleted an clientAddress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-client-address-delete-popup',
    template: ''
})
export class ClientAddressDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clientAddress }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClientAddressDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.clientAddress = clientAddress;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
