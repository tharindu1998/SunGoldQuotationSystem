/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SunGoldSolarTestModule } from '../../../test.module';
import { ClientAddressDeleteDialogComponent } from 'app/entities/client-address/client-address-delete-dialog.component';
import { ClientAddressService } from 'app/entities/client-address/client-address.service';

describe('Component Tests', () => {
    describe('ClientAddress Management Delete Component', () => {
        let comp: ClientAddressDeleteDialogComponent;
        let fixture: ComponentFixture<ClientAddressDeleteDialogComponent>;
        let service: ClientAddressService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [ClientAddressDeleteDialogComponent]
            })
                .overrideTemplate(ClientAddressDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClientAddressDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientAddressService);
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
