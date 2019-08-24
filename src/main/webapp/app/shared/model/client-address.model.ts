export interface IClientAddress {
    id?: string;
    clientId?: string;
    address?: string;
    city?: string;
    postalCode?: number;
}

export class ClientAddress implements IClientAddress {
    constructor(public id?: string, public clientId?: string, public address?: string, public city?: string, public postalCode?: number) {}
}
