import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ClientAddress } from 'app/shared/model/client-address.model';
import { ClientAddressService } from './client-address.service';
import { ClientAddressComponent } from './client-address.component';
import { ClientAddressDetailComponent } from './client-address-detail.component';
import { ClientAddressUpdateComponent } from './client-address-update.component';
import { ClientAddressDeletePopupComponent } from './client-address-delete-dialog.component';
import { IClientAddress } from 'app/shared/model/client-address.model';

@Injectable({ providedIn: 'root' })
export class ClientAddressResolve implements Resolve<IClientAddress> {
    constructor(private service: ClientAddressService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((clientAddress: HttpResponse<ClientAddress>) => clientAddress.body));
        }
        return of(new ClientAddress());
    }
}

export const clientAddressRoute: Routes = [
    {
        path: 'client-address',
        component: ClientAddressComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClientAddresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-address/:id/view',
        component: ClientAddressDetailComponent,
        resolve: {
            clientAddress: ClientAddressResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClientAddresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-address/new',
        component: ClientAddressUpdateComponent,
        resolve: {
            clientAddress: ClientAddressResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClientAddresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-address/new/:clientId',
        component: ClientAddressUpdateComponent,
        resolve: {
            clientAddress: ClientAddressResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClientAddresses'
        },
        canActivate: [UserRouteAccessService]
    },




    
    {
        path: 'client-address/:id/edit',
        component: ClientAddressUpdateComponent,
        resolve: {
            clientAddress: ClientAddressResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClientAddresses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientAddressPopupRoute: Routes = [
    {
        path: 'client-address/:id/delete',
        component: ClientAddressDeletePopupComponent,
        resolve: {
            clientAddress: ClientAddressResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClientAddresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
