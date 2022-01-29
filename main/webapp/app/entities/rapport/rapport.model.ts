import { IFormation } from 'app/entities/formation/formation.model';

export interface IRapport {
  id?: number;
  formation?: IFormation | null;
}

export class Rapport implements IRapport {
  constructor(public id?: number, public formation?: IFormation | null) {}
}

export function getRapportIdentifier(rapport: IRapport): number | undefined {
  return rapport.id;
}
