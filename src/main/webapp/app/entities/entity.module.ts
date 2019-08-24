import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SunGoldSolarClientsModule } from './clients/clients.module';
import { SunGoldSolarClientAddressModule } from './client-address/client-address.module';
import { SunGoldSolarQuotationModule } from './quotation/quotation.module';
import { SunGoldSolarInvertersModule } from './inverters/inverters.module';
import { SunGoldSolarPanelsModule } from './panels/panels.module';
import { DatasheetsComponent } from './datasheets/datasheets.component';
import {RouterModule, Routes} from "@angular/router";
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */


export const entity: Routes =
    {
        path: 'datasheets',
        component: DatasheetsComponent
    };

@NgModule({
    // prettier-ignore
    imports: [
        SunGoldSolarClientsModule,
        SunGoldSolarClientAddressModule,
        SunGoldSolarQuotationModule,
        SunGoldSolarInvertersModule,
        SunGoldSolarPanelsModule,
        RouterModule.forChild(entity)
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [DatasheetsComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SunGoldSolarEntityModule {}
