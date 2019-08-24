import './vendor.ts';

import {NgModule, Injector} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {NgbDatepickerConfig} from '@ng-bootstrap/ng-bootstrap';
import {Ng2Webstorage, LocalStorageService, SessionStorageService} from 'ngx-webstorage';
import {JhiEventManager} from 'ng-jhipster';

import {AuthInterceptor} from './blocks/interceptor/auth.interceptor';
import {AuthExpiredInterceptor} from './blocks/interceptor/auth-expired.interceptor';
import {ErrorHandlerInterceptor} from './blocks/interceptor/errorhandler.interceptor';
import {NotificationInterceptor} from './blocks/interceptor/notification.interceptor';
import {SunGoldSolarAppRoutingModule} from './app-routing.module';
import {SunGoldSolarHomeModule} from './home/home.module';
import {SunGoldSolarAccountModule} from './account/account.module';
import {SunGoldSolarEntityModule} from './entities/entity.module';
import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import {JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ErrorComponent} from './layouts';
import {ReactiveFormsModule} from '@angular/forms';
import {SunGoldSolarSharedModule} from "./shared/shared.module";
import {SunGoldSolarCoreModule} from "./core/core.module";
import {PrintModule} from "./entities/print/print.module";

@NgModule({
    imports: [
        BrowserModule,
        SunGoldSolarAppRoutingModule,
        Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
        SunGoldSolarSharedModule,
        SunGoldSolarCoreModule,
        SunGoldSolarHomeModule,
        SunGoldSolarAccountModule,
        SunGoldSolarEntityModule,
        ReactiveFormsModule,
        PrintModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent,
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [LocalStorageService, SessionStorageService]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class SunGoldSolarAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = {year: moment().year() - 100, month: 1, day: 1};
    }
}
