import { IInscription } from 'app/entities/inscription/inscription.model';
import { IFormateur } from 'app/entities/formateur/formateur.model';

export interface IApprenant {
  id?: number;
  photoContentType?: string | null;
  photo?: string | null;
  nom?: string;
  prenom?: string;
  statut?: string;
  niveau?: string;
  etatStud?: boolean | null;
  contact?: string;
  email?: string | null;
  addParent?: string | null;
  inscription?: IInscription | null;
  formateur?: IFormateur | null;
}

export class Apprenant implements IApprenant {
  constructor(
    public id?: number,
    public photoContentType?: string | null,
    public photo?: string | null,
    public nom?: string,
    public prenom?: string,
    public statut?: string,
    public niveau?: string,
    public etatStud?: boolean | null,
    public contact?: string,
    public email?: string | null,
    public addParent?: string | null,
    public inscription?: IInscription | null,
    public formateur?: IFormateur | null
  ) {
    this.etatStud = this.etatStud ?? false;
  }
}

export function getApprenantIdentifier(apprenant: IApprenant): number | undefined {
  return apprenant.id;
}
