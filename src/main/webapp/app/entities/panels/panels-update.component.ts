import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPanels } from 'app/shared/model/panels.model';
import { PanelsService } from './panels.service';

@Component({
    selector: 'jhi-panels-update',
    templateUrl: './panels-update.component.html'
})
export class PanelsUpdateComponent implements OnInit {
    private _panels: IPanels;
    isSaving: boolean;

    constructor(private panelsService: PanelsService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ panels }) => {
            this.panels = panels;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.panels.id !== undefined) {
            this.subscribeToSaveResponse(this.panelsService.update(this.panels));
        } else {
            this.subscribeToSaveResponse(this.panelsService.create(this.panels));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPanels>>) {
        result.subscribe((res: HttpResponse<IPanels>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get panels() {
        return this._panels;
    }

    set panels(panels: IPanels) {
        this._panels = panels;
    }
}
