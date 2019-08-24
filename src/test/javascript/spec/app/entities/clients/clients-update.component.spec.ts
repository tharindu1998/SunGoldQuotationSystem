/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SunGoldSolarTestModule } from '../../../test.module';
import { ClientsUpdateComponent } from 'app/entities/clients/clients-update.component';
import { ClientsService } from 'app/entities/clients/clients.service';
import { Clients } from 'app/shared/model/clients.model';

describe('Component Tests', () => {
    describe('Clients Management Update Component', () => {
        let comp: ClientsUpdateComponent;
        let fixture: ComponentFixture<ClientsUpdateComponent>;
        let service: ClientsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [ClientsUpdateComponent]
            })
                .overrideTemplate(ClientsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClientsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Clients('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clients = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Clients();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clients = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
