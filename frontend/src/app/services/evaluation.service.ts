import {Injectable} from '@angular/core';
import {BaseEntityService} from "./base-entity.service";
import {HttpClient} from "@angular/common/http";
import {Evaluation} from "../model/entities";

@Injectable({
  providedIn: 'root'
})
export class EvaluationService extends BaseEntityService<Evaluation>{

  constructor(http: HttpClient) {
    super(http);
  }

  getEntityUrl(): string {
    return "evaluations";
  }
}
