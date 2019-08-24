import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SunGoldSolarSharedModule } from 'app/shared';
import {
    ClientAddressComponent,
    ClientAddressDetailComponent,
    ClientAddressUpdateComponent,
    ClientAddressDeletePopupComponent,
    ClientAddressDeleteDialogComponent,
    clientAddressRoute,
    clientAddressPopupRoute
} from './';

const ENTITY_STATES = [...clientAddressRoute, ...clientAddressPopupRoute];

@NgModule({
    imports: [SunGoldSolarSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClientAddressComponent,
        ClientAddressDetailComponent,
        ClientAddressUpdateComponent,
        ClientAddressDeleteDialogComponent,
        ClientAddressDeletePopupComponent
    ],
    entryComponents: [
        ClientAddressComponent,
        ClientAddressUpdateComponent,
        ClientAddressDeleteDialogComponent,
        ClientAddressDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SunGoldSolarClientAddressModule {}
