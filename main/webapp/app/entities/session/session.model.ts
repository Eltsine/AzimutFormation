import * as dayjs from 'dayjs';
import { IModules } from 'app/entities/modules/modules.model';

export interface ISession {
  id?: number;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  nbreParticipant?: number;
  nbreHeure?: number | null;
  heureDebut?: dayjs.Dayjs | null;
  heureFin?: dayjs.Dayjs | null;
  contenuFormation?: string;
  modules?: IModules | null;
}

export class Session implements ISession {
  constructor(
    public id?: number,
    public dateDebut?: dayjs.Dayjs | null,
    public dateFin?: dayjs.Dayjs | null,
    public nbreParticipant?: number,
    public nbreHeure?: number | null,
    public heureDebut?: dayjs.Dayjs | null,
    public heureFin?: dayjs.Dayjs | null,
    public contenuFormation?: string,
    public modules?: IModules | null
  ) {}
}

export function getSessionIdentifier(session: ISession): number | undefined {
  return session.id;
}
