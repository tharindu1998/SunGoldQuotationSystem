import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IClientAddress } from 'app/shared/model/client-address.model';
import {createRequestOption} from "../../shared/util/request-util";

type EntityResponseType = HttpResponse<IClientAddress>;
type EntityArrayResponseType = HttpResponse<IClientAddress[]>;

@Injectable({ providedIn: 'root' })
export class ClientAddressService {
    private resourceUrl = SERVER_API_URL + 'api/client-addresses';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/client-addresses';

    constructor(private http: HttpClient) { }

    create(clientAddress: IClientAddress): Observable<EntityResponseType> {
        return this.http.post<IClientAddress>(this.resourceUrl, clientAddress, { observe: 'response' });
    }

    update(clientAddress: IClientAddress): Observable<EntityResponseType> {
        return this.http.put<IClientAddress>(this.resourceUrl, clientAddress, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IClientAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClientAddress[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClientAddress[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }


    getAllAddressesByClientId(req): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/client-addresses/_search/' + req);
    }


}
