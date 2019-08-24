import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Inverters } from 'app/shared/model/inverters.model';
import { InvertersService } from './inverters.service';
import { InvertersComponent } from './inverters.component';
import { InvertersDetailComponent } from './inverters-detail.component';
import { InvertersUpdateComponent } from './inverters-update.component';
import { InvertersDeletePopupComponent } from './inverters-delete-dialog.component';
import { IInverters } from 'app/shared/model/inverters.model';

@Injectable({ providedIn: 'root' })
export class InvertersResolve implements Resolve<IInverters> {
    constructor(private service: InvertersService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((inverters: HttpResponse<Inverters>) => inverters.body));
        }
        return of(new Inverters());
    }
}

export const invertersRoute: Routes = [
    {
        path: 'inverters',
        component: InvertersComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inverters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'inverters/:id/view',
        component: InvertersDetailComponent,
        resolve: {
            inverters: InvertersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inverters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'inverters/new',
        component: InvertersUpdateComponent,
        resolve: {
            inverters: InvertersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inverters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'inverters/:id/edit',
        component: InvertersUpdateComponent,
        resolve: {
            inverters: InvertersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inverters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const invertersPopupRoute: Routes = [
    {
        path: 'inverters/:id/delete',
        component: InvertersDeletePopupComponent,
        resolve: {
            inverters: InvertersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inverters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
