import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import {
    ClientsComponent,
    ClientsDetailComponent,
    ClientsUpdateComponent,
    ClientsDeletePopupComponent,
    ClientsDeleteDialogComponent,
    clientsRoute,
    clientsPopupRoute
} from './';
import {SunGoldSolarSharedModule} from "../../shared/shared.module";

const ENTITY_STATES = [...clientsRoute, ...clientsPopupRoute];

@NgModule({
    imports: [SunGoldSolarSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClientsComponent,
        ClientsDetailComponent,
        ClientsUpdateComponent,
        ClientsDeleteDialogComponent,
        ClientsDeletePopupComponent
    ],
    entryComponents: [ClientsComponent, ClientsUpdateComponent, ClientsDeleteDialogComponent, ClientsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SunGoldSolarClientsModule { }
