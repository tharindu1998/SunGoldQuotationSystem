/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SunGoldSolarTestModule } from '../../../test.module';
import { PanelsDeleteDialogComponent } from 'app/entities/panels/panels-delete-dialog.component';
import { PanelsService } from 'app/entities/panels/panels.service';

describe('Component Tests', () => {
    describe('Panels Management Delete Component', () => {
        let comp: PanelsDeleteDialogComponent;
        let fixture: ComponentFixture<PanelsDeleteDialogComponent>;
        let service: PanelsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [PanelsDeleteDialogComponent]
            })
                .overrideTemplate(PanelsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PanelsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PanelsService);
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
