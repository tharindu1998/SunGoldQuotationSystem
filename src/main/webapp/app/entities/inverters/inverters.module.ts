import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SunGoldSolarSharedModule } from 'app/shared';
import {
    InvertersComponent,
    InvertersDetailComponent,
    InvertersUpdateComponent,
    InvertersDeletePopupComponent,
    InvertersDeleteDialogComponent,
    invertersRoute,
    invertersPopupRoute
} from './';

const ENTITY_STATES = [...invertersRoute, ...invertersPopupRoute];

@NgModule({
    imports: [SunGoldSolarSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InvertersComponent,
        InvertersDetailComponent,
        InvertersUpdateComponent,
        InvertersDeleteDialogComponent,
        InvertersDeletePopupComponent
    ],
    entryComponents: [InvertersComponent, InvertersUpdateComponent, InvertersDeleteDialogComponent, InvertersDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SunGoldSolarInvertersModule {}
