import {Moment} from 'moment';

export interface IQuotation {
    id?: string;
    indexId?: number,

    clientId?: string;
    addressId?: string;


    created?: Moment;
    systemSize?: number;
    unitUsage?: string;
    unitsGenerates?: number;
    panelBrand?: string;
    panelModel?: string;
    panelPrice?: Number;
    numberofPanels?: number;

    panelCapacity?: number;
    inverterPrice?: number;
    inverterBrand?: string;

    inverterModel?: string;
    inverterCapacity?: number;
    numberOfInverters?: number;

    inverterModel2?: string;
    inverterCapacity2?: number;
    numberOfInverters2?: number;


    areaNeeded?: number;
    mountingStructure?: string;
    constructionPrice?: string;
    profit?: string;
    commission?: string;
    systemPrice?: number;

    issuedBy?: string;
    issuersTelephone?: string;


}

export class Quotation implements IQuotation {
    constructor(public id?: string,
                public  indexId?: number,

                public clientId?: string,
                public addressId?: string,

                public created?: Moment,
                public systemSize?: number,
                public unitUsage?: string,
                public unitsGenerates?: number,
                public panelBrand?: string,
                public panelModel?: string,
                public numberofPanels?: number,
                public panelCapacity?: number,
                public inverterPrice?: number,
                public inverterBrand?: string,
                public inverterModel?: string,
                public inverterCapacity?: number,
                public inverterModel2?: string,
                public inverterCapacity2?: number,
                public numberOfInverters2?: number,
                public areaNeeded?: number,
                public mountingStructure?: string,
                public constructionPrice?: string,
                public profit?: string,
                public commission?: string,
                public systemPrice?: number,
                public panelPrice?: number,
                public numberOfInverters?: number,
                public issuedBy?: string,
                public issuersTelephone?: string,) {
    }
}
