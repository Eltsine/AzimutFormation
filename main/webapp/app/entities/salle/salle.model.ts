import { ISession } from 'app/entities/session/session.model';

export interface ISalle {
  id?: number;
  nomSalle?: string;
  capacite?: number | null;
  session?: ISession | null;
}

export class Salle implements ISalle {
  constructor(public id?: number, public nomSalle?: string, public capacite?: number | null, public session?: ISession | null) {}
}

export function getSalleIdentifier(salle: ISalle): number | undefined {
  return salle.id;
}
