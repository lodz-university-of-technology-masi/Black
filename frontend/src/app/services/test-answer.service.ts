import {Injectable} from '@angular/core';
import {BaseEntityService} from './base-entity.service';
import {HttpClient} from '@angular/common/http';
import {TestAnswer} from '../model/entities';

@Injectable({
  providedIn: 'root'
})
export class TestAnswerService extends BaseEntityService<TestAnswer> {

  constructor(http: HttpClient) {
    super(http);
  }

  getEntityUrl(): string {
    return 'answers';
  }
}
