import { IInscription } from 'app/entities/inscription/inscription.model';
import { IFormateur } from 'app/entities/formateur/formateur.model';

export interface IEtablissement {
  id?: number;
  nom?: string | null;
  adresse?: string | null;
  inscriptions?: IInscription[] | null;
  formateurs?: IFormateur[] | null;
}

export class Etablissement implements IEtablissement {
  constructor(
    public id?: number,
    public nom?: string | null,
    public adresse?: string | null,
    public inscriptions?: IInscription[] | null,
    public formateurs?: IFormateur[] | null
  ) {}
}

export function getEtablissementIdentifier(etablissement: IEtablissement): number | undefined {
  return etablissement.id;
}
