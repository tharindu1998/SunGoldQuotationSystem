/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SunGoldSolarTestModule } from '../../../test.module';
import { ClientsDeleteDialogComponent } from 'app/entities/clients/clients-delete-dialog.component';
import { ClientsService } from 'app/entities/clients/clients.service';

describe('Component Tests', () => {
    describe('Clients Management Delete Component', () => {
        let comp: ClientsDeleteDialogComponent;
        let fixture: ComponentFixture<ClientsDeleteDialogComponent>;
        let service: ClientsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [ClientsDeleteDialogComponent]
            })
                .overrideTemplate(ClientsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClientsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
