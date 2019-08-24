export interface IInverters {
    id?: string;
    inverterBrand?: string;
    inverterModel?: string;
    inverterSize?: number;
    inverterPrice?: number;
}

export class Inverters implements IInverters {
    constructor(
        public id?: string,
        public inverterBrand?: string,
        public inverterModel?: string,
        public inverterSize?: number,
        public inverterPrice?: number
    ) {}
}
