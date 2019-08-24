export interface IPanels {
    id?: string;
    palenBrand?: string;
    panelModel?: string;
    panelSize?: number;
    panelPrice?: number;
}

export class Panels implements IPanels {
    constructor(
        public id?: string,
        public palenBrand?: string,
        public panelModel?: string,
        public panelSize?: number,
        public panelPrice?: number
    ) {}
}
