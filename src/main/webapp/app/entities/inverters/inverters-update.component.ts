import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IInverters } from 'app/shared/model/inverters.model';
import { InvertersService } from './inverters.service';

@Component({
    selector: 'jhi-inverters-update',
    templateUrl: './inverters-update.component.html'
})
export class InvertersUpdateComponent implements OnInit {
    private _inverters: IInverters;
    isSaving: boolean;

    constructor(private invertersService: InvertersService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ inverters }) => {
            this.inverters = inverters;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.inverters.id !== undefined) {
            this.subscribeToSaveResponse(this.invertersService.update(this.inverters));
        } else {
            this.subscribeToSaveResponse(this.invertersService.create(this.inverters));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IInverters>>) {
        result.subscribe((res: HttpResponse<IInverters>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get inverters() {
        return this._inverters;
    }

    set inverters(inverters: IInverters) {
        this._inverters = inverters;
    }
}
