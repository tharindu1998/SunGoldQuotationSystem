/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SunGoldSolarTestModule } from '../../../test.module';
import { PanelsComponent } from 'app/entities/panels/panels.component';
import { PanelsService } from 'app/entities/panels/panels.service';
import { Panels } from 'app/shared/model/panels.model';

describe('Component Tests', () => {
    describe('Panels Management Component', () => {
        let comp: PanelsComponent;
        let fixture: ComponentFixture<PanelsComponent>;
        let service: PanelsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [PanelsComponent],
                providers: []
            })
                .overrideTemplate(PanelsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PanelsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PanelsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Panels('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.panels[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
