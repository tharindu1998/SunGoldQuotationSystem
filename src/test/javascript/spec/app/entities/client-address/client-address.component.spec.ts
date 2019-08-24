/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SunGoldSolarTestModule } from '../../../test.module';
import { ClientAddressComponent } from 'app/entities/client-address/client-address.component';
import { ClientAddressService } from 'app/entities/client-address/client-address.service';
import { ClientAddress } from 'app/shared/model/client-address.model';

describe('Component Tests', () => {
    describe('ClientAddress Management Component', () => {
        let comp: ClientAddressComponent;
        let fixture: ComponentFixture<ClientAddressComponent>;
        let service: ClientAddressService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [ClientAddressComponent],
                providers: []
            })
                .overrideTemplate(ClientAddressComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClientAddressComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientAddressService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ClientAddress('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.clientAddresses[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
