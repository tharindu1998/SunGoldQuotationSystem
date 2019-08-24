import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import * as moment from 'moment';
import {DATE_FORMAT} from 'app/shared/constants/input.constants';
import {map} from 'rxjs/operators';

import {SERVER_API_URL} from 'app/app.constants';
import {IQuotation} from 'app/shared/model/quotation.model';
import {createRequestOption} from "../../shared/util/request-util";

type EntityResponseType = HttpResponse<IQuotation>;
type EntityArrayResponseType = HttpResponse<IQuotation[]>;

@Injectable({providedIn: 'root'})
export class QuotationService {
    private resourceUrl = SERVER_API_URL + 'api/quotations';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/quotations';

    private quotationByAddressId = SERVER_API_URL + 'api/quotations/byaddress';

    constructor(private http: HttpClient) {
    }

    create(quotation: IQuotation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quotation);
        return this.http
            .post<IQuotation>(this.resourceUrl, copy, {observe: 'response'})
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(quotation: IQuotation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quotation);
        return this.http
            .put<IQuotation>(this.resourceUrl, copy, {observe: 'response'})
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<IQuotation>(`${this.resourceUrl}/${id}`, {observe: 'response'})
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuotation[]>(this.resourceUrl, {params: options, observe: 'response'})
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuotation[]>(this.resourceSearchUrl, {params: options, observe: 'response'})
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(quotation: IQuotation): IQuotation {
        const copy: IQuotation = Object.assign({}, quotation, {
            created: quotation.created != null && quotation.created.isValid() ? quotation.created.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.created = res.body.created != null ? moment(res.body.created) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((quotation: IQuotation) => {
            quotation.created = quotation.created != null ? moment(quotation.created) : null;
        });
        return res;
    }

    getQuotationByAddressId(req: string): Observable<EntityArrayResponseType> {
        return this.http
            .get<IQuotation[]>(this.quotationByAddressId+'/'+req, { observe: 'response'})
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }


}
