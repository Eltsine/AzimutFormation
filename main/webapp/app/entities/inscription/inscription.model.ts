import * as dayjs from 'dayjs';
import { IPeriode } from 'app/entities/periode/periode.model';
import { IFormation } from 'app/entities/formation/formation.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { IAnneeScolaire } from 'app/entities/annee-scolaire/annee-scolaire.model';
import { EtatEtudiant } from 'app/entities/enumerations/etat-etudiant.model';

export interface IInscription {
  id?: number;
  dateInscription?: dayjs.Dayjs;
  montantApayer?: number;
  montantVerse?: number;
  resteApayer?: number;
  etatEtudiant?: EtatEtudiant;
  periode?: IPeriode | null;
  formations?: IFormation[] | null;
  etablissement?: IEtablissement | null;
  anneeScolaire?: IAnneeScolaire | null;
}

export class Inscription implements IInscription {
  constructor(
    public id?: number,
    public dateInscription?: dayjs.Dayjs,
    public montantApayer?: number,
    public montantVerse?: number,
    public resteApayer?: number,
    public etatEtudiant?: EtatEtudiant,
    public periode?: IPeriode | null,
    public formations?: IFormation[] | null,
    public etablissement?: IEtablissement | null,
    public anneeScolaire?: IAnneeScolaire | null
  ) {}
}

export function getInscriptionIdentifier(inscription: IInscription): number | undefined {
  return inscription.id;
}
