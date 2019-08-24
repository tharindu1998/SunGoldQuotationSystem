import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPanels } from 'app/shared/model/panels.model';
import { PanelsService } from './panels.service';

@Component({
    selector: 'jhi-panels-delete-dialog',
    templateUrl: './panels-delete-dialog.component.html'
})
export class PanelsDeleteDialogComponent {
    panels: IPanels;

    constructor(private panelsService: PanelsService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.panelsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'panelsListModification',
                content: 'Deleted an panels'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-panels-delete-popup',
    template: ''
})
export class PanelsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ panels }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PanelsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.panels = panels;
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
