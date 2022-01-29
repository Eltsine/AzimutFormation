import { ISession } from 'app/entities/session/session.model';
import { IFormation } from 'app/entities/formation/formation.model';
import { NomModules } from 'app/entities/enumerations/nom-modules.model';

export interface IModules {
  id?: number;
  nomModule?: NomModules;
  prix?: number | null;
  sessions?: ISession[] | null;
  formation?: IFormation | null;
}

export class Modules implements IModules {
  constructor(
    public id?: number,
    public nomModule?: NomModules,
    public prix?: number | null,
    public sessions?: ISession[] | null,
    public formation?: IFormation | null
  ) {}
}

export function getModulesIdentifier(modules: IModules): number | undefined {
  return modules.id;
}
