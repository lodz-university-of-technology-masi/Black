import {Injectable} from '@angular/core';

import {BaseEntityService} from './base-entity.service';
import {HttpClient} from '@angular/common/http';
import {Position} from '../model/entities';


@Injectable({
  providedIn: 'root'
})
export class PositionService extends BaseEntityService<Position> {

  constructor(http: HttpClient) {
    super(http);
  }

  getEntityUrl(): string {
    return 'positions';
  }
}
