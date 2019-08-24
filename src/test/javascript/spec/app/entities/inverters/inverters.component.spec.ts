/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SunGoldSolarTestModule } from '../../../test.module';
import { InvertersComponent } from 'app/entities/inverters/inverters.component';
import { InvertersService } from 'app/entities/inverters/inverters.service';
import { Inverters } from 'app/shared/model/inverters.model';

describe('Component Tests', () => {
    describe('Inverters Management Component', () => {
        let comp: InvertersComponent;
        let fixture: ComponentFixture<InvertersComponent>;
        let service: InvertersService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [InvertersComponent],
                providers: []
            })
                .overrideTemplate(InvertersComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InvertersComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvertersService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Inverters('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.inverters[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
