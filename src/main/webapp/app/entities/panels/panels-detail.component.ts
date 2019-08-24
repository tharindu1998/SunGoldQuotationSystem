import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPanels } from 'app/shared/model/panels.model';

@Component({
    selector: 'jhi-panels-detail',
    templateUrl: './panels-detail.component.html'
})
export class PanelsDetailComponent implements OnInit {
    panels: IPanels;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ panels }) => {
            this.panels = panels;
        });
    }

    previousState() {
        window.history.back();
    }
}
