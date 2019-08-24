/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SunGoldSolarTestModule } from '../../../test.module';
import { PanelsUpdateComponent } from 'app/entities/panels/panels-update.component';
import { PanelsService } from 'app/entities/panels/panels.service';
import { Panels } from 'app/shared/model/panels.model';

describe('Component Tests', () => {
    describe('Panels Management Update Component', () => {
        let comp: PanelsUpdateComponent;
        let fixture: ComponentFixture<PanelsUpdateComponent>;
        let service: PanelsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [PanelsUpdateComponent]
            })
                .overrideTemplate(PanelsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PanelsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PanelsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Panels('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.panels = entity;
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
                    const entity = new Panels();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.panels = entity;
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
