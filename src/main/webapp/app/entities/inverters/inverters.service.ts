import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IInverters } from 'app/shared/model/inverters.model';
import {createRequestOption} from "../../shared/util/request-util";

type EntityResponseType = HttpResponse<IInverters>;
type EntityArrayResponseType = HttpResponse<IInverters[]>;

@Injectable({ providedIn: 'root' })
export class InvertersService {
    private resourceUrl = SERVER_API_URL + 'api/inverters';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/inverters';

    constructor(private http: HttpClient) {}

    create(inverters: IInverters): Observable<EntityResponseType> {
        return this.http.post<IInverters>(this.resourceUrl, inverters, { observe: 'response' });
    }

    update(inverters: IInverters): Observable<EntityResponseType> {
        return this.http.put<IInverters>(this.resourceUrl, inverters, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IInverters>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInverters[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInverters[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
