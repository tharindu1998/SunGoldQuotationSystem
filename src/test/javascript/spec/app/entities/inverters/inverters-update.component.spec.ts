/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SunGoldSolarTestModule } from '../../../test.module';
import { InvertersUpdateComponent } from 'app/entities/inverters/inverters-update.component';
import { InvertersService } from 'app/entities/inverters/inverters.service';
import { Inverters } from 'app/shared/model/inverters.model';

describe('Component Tests', () => {
    describe('Inverters Management Update Component', () => {
        let comp: InvertersUpdateComponent;
        let fixture: ComponentFixture<InvertersUpdateComponent>;
        let service: InvertersService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [InvertersUpdateComponent]
            })
                .overrideTemplate(InvertersUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InvertersUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvertersService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Inverters('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.inverters = entity;
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
                    const entity = new Inverters();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.inverters = entity;
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
