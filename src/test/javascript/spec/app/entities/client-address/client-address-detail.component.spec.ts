/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SunGoldSolarTestModule } from '../../../test.module';
import { ClientAddressDetailComponent } from 'app/entities/client-address/client-address-detail.component';
import { ClientAddress } from 'app/shared/model/client-address.model';

describe('Component Tests', () => {
    describe('ClientAddress Management Detail Component', () => {
        let comp: ClientAddressDetailComponent;
        let fixture: ComponentFixture<ClientAddressDetailComponent>;
        const route = ({ data: of({ clientAddress: new ClientAddress('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [ClientAddressDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClientAddressDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClientAddressDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.clientAddress).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
