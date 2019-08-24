/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SunGoldSolarTestModule } from '../../../test.module';
import { ClientsComponent } from 'app/entities/clients/clients.component';
import { ClientsService } from 'app/entities/clients/clients.service';
import { Clients } from 'app/shared/model/clients.model';

describe('Component Tests', () => {
    describe('Clients Management Component', () => {
        let comp: ClientsComponent;
        let fixture: ComponentFixture<ClientsComponent>;
        let service: ClientsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [ClientsComponent],
                providers: []
            })
                .overrideTemplate(ClientsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClientsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Clients('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.clients[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
