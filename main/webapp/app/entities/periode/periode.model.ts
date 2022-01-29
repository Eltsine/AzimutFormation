import * as dayjs from 'dayjs';

export interface IPeriode {
  id?: number;
  duree?: string | null;
  dateDebut?: dayjs.Dayjs;
  dateFin?: dayjs.Dayjs;
}

export class Periode implements IPeriode {
  constructor(public id?: number, public duree?: string | null, public dateDebut?: dayjs.Dayjs, public dateFin?: dayjs.Dayjs) {}
}

export function getPeriodeIdentifier(periode: IPeriode): number | undefined {
  return periode.id;
}
