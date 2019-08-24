/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SunGoldSolarTestModule } from '../../../test.module';
import { InvertersDetailComponent } from 'app/entities/inverters/inverters-detail.component';
import { Inverters } from 'app/shared/model/inverters.model';

describe('Component Tests', () => {
    describe('Inverters Management Detail Component', () => {
        let comp: InvertersDetailComponent;
        let fixture: ComponentFixture<InvertersDetailComponent>;
        const route = ({ data: of({ inverters: new Inverters('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [InvertersDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InvertersDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvertersDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.inverters).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
