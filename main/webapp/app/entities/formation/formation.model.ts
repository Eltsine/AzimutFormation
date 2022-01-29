import { IRapport } from 'app/entities/rapport/rapport.model';
import { IModules } from 'app/entities/modules/modules.model';
import { IInscription } from 'app/entities/inscription/inscription.model';
import { NomFormation } from 'app/entities/enumerations/nom-formation.model';

export interface IFormation {
  id?: number;
  nomFormation?: NomFormation;
  rapport?: IRapport | null;
  modules?: IModules[] | null;
  inscription?: IInscription | null;
}

export class Formation implements IFormation {
  constructor(
    public id?: number,
    public nomFormation?: NomFormation,
    public rapport?: IRapport | null,
    public modules?: IModules[] | null,
    public inscription?: IInscription | null
  ) {}
}

export function getFormationIdentifier(formation: IFormation): number | undefined {
  return formation.id;
}
