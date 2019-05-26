import {Injectable} from '@angular/core';
import {BaseEntityService} from './base-entity.service';
import {HttpClient} from '@angular/common/http';
import {Evaluation} from '../model/entities';

@Injectable({
  providedIn: 'root'
})
export class EvaluationService extends BaseEntityService<Evaluation> {

  constructor(http: HttpClient) {
    super(http);
  }

  public async sendEmailNotification(evaluation: Evaluation) {
    return this.http.post(`${this.getEntityUrl()}/${evaluation.id}/sendemail`, null).toPromise()
  }

  getEntityUrl(): string {
    return 'evaluations';
  }
}
