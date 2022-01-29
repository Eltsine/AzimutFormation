import { IInscription } from 'app/entities/inscription/inscription.model';

export interface IAnneeScolaire {
  id?: number;
  libelleAnnee?: string | null;
  inscriptions?: IInscription[] | null;
}

export class AnneeScolaire implements IAnneeScolaire {
  constructor(public id?: number, public libelleAnnee?: string | null, public inscriptions?: IInscription[] | null) {}
}

export function getAnneeScolaireIdentifier(anneeScolaire: IAnneeScolaire): number | undefined {
  return anneeScolaire.id;
}
