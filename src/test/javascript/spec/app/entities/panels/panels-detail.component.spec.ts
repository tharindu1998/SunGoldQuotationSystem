/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SunGoldSolarTestModule } from '../../../test.module';
import { PanelsDetailComponent } from 'app/entities/panels/panels-detail.component';
import { Panels } from 'app/shared/model/panels.model';

describe('Component Tests', () => {
    describe('Panels Management Detail Component', () => {
        let comp: PanelsDetailComponent;
        let fixture: ComponentFixture<PanelsDetailComponent>;
        const route = ({ data: of({ panels: new Panels('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [PanelsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PanelsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PanelsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.panels).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
