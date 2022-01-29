import { ISpecialite } from 'app/entities/specialite/specialite.model';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';

export interface IFormateur {
  id?: number;
  photoContentType?: string | null;
  photo?: string | null;
  cnib?: string;
  nom?: string;
  prenom?: string;
  statut?: string | null;
  contact?: string;
  email?: string | null;
  salaireHoraire?: number | null;
  salaireMensuel?: number | null;
  specialites?: ISpecialite[] | null;
  personnes?: IApprenant[] | null;
  etablissement?: IEtablissement | null;
}

export class Formateur implements IFormateur {
  constructor(
    public id?: number,
    public photoContentType?: string | null,
    public photo?: string | null,
    public cnib?: string,
    public nom?: string,
    public prenom?: string,
    public statut?: string | null,
    public contact?: string,
    public email?: string | null,
    public salaireHoraire?: number | null,
    public salaireMensuel?: number | null,
    public specialites?: ISpecialite[] | null,
    public personnes?: IApprenant[] | null,
    public etablissement?: IEtablissement | null
  ) {}
}

export function getFormateurIdentifier(formateur: IFormateur): number | undefined {
  return formateur.id;
}
