



import {RouterModule} from "@angular/router";
import {SunGoldSolarSharedModule} from "../../shared/shared.module";
import {CoverComponent} from "./cover/cover.component";
import {Page3Component} from "./page3/page3.component";
import {Page2Component} from "./page2/page2.component";
import {Page1Component} from "./page1/page1.component";
import {PrintComponent} from "./print.component";
import {PrintviewComponent} from "../../printview/printview.component";
import {printRoutes} from "./print.route";
import {NgModule} from "@angular/core";
import { CostPrintComponent } from './cost-print/cost-print.component';
import { MeeteringAndfinancingComponent } from './meetering-andfinancing/meetering-andfinancing.component';

@NgModule({
    imports: [SunGoldSolarSharedModule, RouterModule.forChild(printRoutes)],
    declarations: [
        PrintviewComponent,
        PrintComponent,
        Page1Component,
        Page2Component,
        Page3Component,
        CoverComponent,
        CostPrintComponent,
        MeeteringAndfinancingComponent
    ],
    entryComponents: [



    ]
})
export class PrintModule {}
