import { IFormateur } from 'app/entities/formateur/formateur.model';

export interface ISpecialite {
  id?: number;
  libSpecialite?: string;
  formateur?: IFormateur | null;
}

export class Specialite implements ISpecialite {
  constructor(public id?: number, public libSpecialite?: string, public formateur?: IFormateur | null) {}
}

export function getSpecialiteIdentifier(specialite: ISpecialite): number | undefined {
  return specialite.id;
}
