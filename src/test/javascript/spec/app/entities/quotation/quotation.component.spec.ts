/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SunGoldSolarTestModule } from '../../../test.module';
import { QuotationComponent } from 'app/entities/quotation/quotation.component';
import { QuotationService } from 'app/entities/quotation/quotation.service';
import { Quotation } from 'app/shared/model/quotation.model';

describe('Component Tests', () => {
    describe('Quotation Management Component', () => {
        let comp: QuotationComponent;
        let fixture: ComponentFixture<QuotationComponent>;
        let service: QuotationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SunGoldSolarTestModule],
                declarations: [QuotationComponent],
                providers: []
            })
                .overrideTemplate(QuotationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuotationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuotationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Quotation('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.quotations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
