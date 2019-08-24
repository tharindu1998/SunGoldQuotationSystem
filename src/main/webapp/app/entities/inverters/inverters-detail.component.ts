import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInverters } from 'app/shared/model/inverters.model';

@Component({
    selector: 'jhi-inverters-detail',
    templateUrl: './inverters-detail.component.html'
})
export class InvertersDetailComponent implements OnInit {
    inverters: IInverters;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ inverters }) => {
            this.inverters = inverters;
        });
    }

    previousState() {
        window.history.back();
    }
}
