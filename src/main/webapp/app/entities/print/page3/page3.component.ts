import {Component, Input, OnInit} from '@angular/core';
import {IQuotation} from "../../../shared/model/quotation.model";

@Component({
  selector: 'app-page3',
  templateUrl: './page3.component.html',
  styleUrls: ['./page3.component.css']
})
export class Page3Component implements OnInit {

    @Input('quotation') quotation: IQuotation;
    private systemSize: number;
    private panelCap: string;
    private inv: string;
    private systemPrice: string;
  constructor() { }

  ngOnInit() {

      this.systemSize = this.quotation.inverterCapacity * this.quotation.numberOfInverters + this.quotation.inverterCapacity2 * this.quotation.numberOfInverters2;
      this.panelCap=this.quotation.panelModel.split(',')[1];

      if (this.quotation.inverterCapacity2) {
          this.inv = this.quotation.inverterCapacity + ' * ' + this.quotation.numberOfInverters + ' + ' + this.quotation.inverterCapacity2 + ' * ' + this.quotation.numberOfInverters2;
      } else {
          this.inv = this.quotation.inverterCapacity + ' * ' + this.quotation.numberOfInverters;

      }

      this.systemPrice=this.quotation.systemPrice.toLocaleString();
  }

}
