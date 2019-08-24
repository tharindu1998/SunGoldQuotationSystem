import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SunGoldSolarSharedModule } from 'app/shared';
import {
    PanelsComponent,
    PanelsDetailComponent,
    PanelsUpdateComponent,
    PanelsDeletePopupComponent,
    PanelsDeleteDialogComponent,
    panelsRoute,
    panelsPopupRoute
} from './';

const ENTITY_STATES = [...panelsRoute, ...panelsPopupRoute];

@NgModule({
    imports: [SunGoldSolarSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PanelsComponent, PanelsDetailComponent, PanelsUpdateComponent, PanelsDeleteDialogComponent, PanelsDeletePopupComponent],
    entryComponents: [PanelsComponent, PanelsUpdateComponent, PanelsDeleteDialogComponent, PanelsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SunGoldSolarPanelsModule {}
