import { Moment } from 'moment';

export const enum Designation {
    Ms = 'Ms',
    Mr = 'Mr',
    Miss = 'Miss',
    Dr = 'Dr',
    Rev = 'Rev',  Sir = 'Sir',
    Org = 'Org',  CO = 'CO', Dept = 'Dept',

}

export interface IClients {
    id?: string;
    created?: Moment;
    firstName?: string;
    lastName?: string;
    designation?: Designation;
    nicNumber?: string;
    tel?: string;
}

export class Clients implements IClients {
    constructor(
        public id?: string,
        public created?: Moment,
        public firstName?: string,
        public lastName?: string,
        public designation?: Designation,
        public nicNumber?: string,
        public tel?: string
    ) {}
}
