import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from "@angular/router";
import {PrintComponent} from "./print.component";
import {UserRouteAccessService} from "../../core/auth/user-route-access-service";
import {CostPrintComponent} from "./cost-print/cost-print.component";
import {Injectable} from "@angular/core";
import {IQuotation, Quotation} from "../../shared/model/quotation.model";
import {QuotationService} from "../quotation/quotation.service";
import {HttpResponse} from "@angular/common/http";
import {of} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class QuotationResolve implements Resolve<IQuotation> {
    constructor(private service: QuotationService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['quotationId'] ? route.params['quotationId'] : null;
        if (id) {
            return this.service.find(id).pipe(map((quotation: HttpResponse<Quotation>) => quotation.body));
        }
        return of(new Quotation());
    }
}


export const printRoutes: Routes = [
    {
        path: 'print/quotation/:quotationId',
        component: PrintComponent,
        resolve: {
            quotation: QuotationResolve
        },

        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'print/cost/:quotationId',
        component: CostPrintComponent,
        resolve: {
            quotation: QuotationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    }

];

