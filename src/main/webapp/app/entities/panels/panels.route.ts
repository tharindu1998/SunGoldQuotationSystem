import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Panels } from 'app/shared/model/panels.model';
import { PanelsService } from './panels.service';
import { PanelsComponent } from './panels.component';
import { PanelsDetailComponent } from './panels-detail.component';
import { PanelsUpdateComponent } from './panels-update.component';
import { PanelsDeletePopupComponent } from './panels-delete-dialog.component';
import { IPanels } from 'app/shared/model/panels.model';

@Injectable({ providedIn: 'root' })
export class PanelsResolve implements Resolve<IPanels> {
    constructor(private service: PanelsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((panels: HttpResponse<Panels>) => panels.body));
        }
        return of(new Panels());
    }
}

export const panelsRoute: Routes = [
    {
        path: 'panels',
        component: PanelsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Panels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'panels/:id/view',
        component: PanelsDetailComponent,
        resolve: {
            panels: PanelsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Panels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'panels/new',
        component: PanelsUpdateComponent,
        resolve: {
            panels: PanelsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Panels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'panels/:id/edit',
        component: PanelsUpdateComponent,
        resolve: {
            panels: PanelsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Panels'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const panelsPopupRoute: Routes = [
    {
        path: 'panels/:id/delete',
        component: PanelsDeletePopupComponent,
        resolve: {
            panels: PanelsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Panels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
