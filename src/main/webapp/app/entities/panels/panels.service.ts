import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPanels } from 'app/shared/model/panels.model';

type EntityResponseType = HttpResponse<IPanels>;
type EntityArrayResponseType = HttpResponse<IPanels[]>;

@Injectable({ providedIn: 'root' })
export class PanelsService {
    private resourceUrl = SERVER_API_URL + 'api/panels';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/panels';

    constructor(private http: HttpClient) {}

    create(panels: IPanels): Observable<EntityResponseType> {
        return this.http.post<IPanels>(this.resourceUrl, panels, { observe: 'response' });
    }

    update(panels: IPanels): Observable<EntityResponseType> {
        return this.http.put<IPanels>(this.resourceUrl, panels, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IPanels>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPanels[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPanels[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
