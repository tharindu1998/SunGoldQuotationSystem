import { NgModule } from '@angular/core';

import { SunGoldSolarSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [SunGoldSolarSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [SunGoldSolarSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SunGoldSolarSharedCommonModule {}
