/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SunGoldSolarTestModule } from '../../../test.module';
import { ClientsDetailComponent } from 'app/entities/clients/clients-detail.component';
import { Clients } from 'app/shared/model/clients.model';

describe('Component Tests', () => {
    describe('Clients Management Detail Component', () => {
        let comp: ClientsDetailComponent;
        let fixture: ComponentFixture<ClientsDetailComponent>;
        const route = ({ data: of({ clients: new Clients('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [ClientsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClientsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClientsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.clients).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
