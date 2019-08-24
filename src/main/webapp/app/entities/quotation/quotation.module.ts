import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import {
    QuotationComponent,
    QuotationDetailComponent,
    QuotationUpdateComponent,
    QuotationDeletePopupComponent,
    QuotationDeleteDialogComponent,
    quotationRoute,
    quotationPopupRoute
} from './';
import {SunGoldSolarSharedModule} from "../../shared/shared.module";

const ENTITY_STATES = [...quotationRoute, ...quotationPopupRoute];

@NgModule({
    imports: [SunGoldSolarSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuotationComponent,
        QuotationDetailComponent,
        QuotationUpdateComponent,
        QuotationDeleteDialogComponent,
        QuotationDeletePopupComponent
    ],
    entryComponents: [QuotationComponent, QuotationUpdateComponent, QuotationDeleteDialogComponent, QuotationDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SunGoldSolarQuotationModule {}
