import {Injectable} from '@angular/core';
import {BaseEntityService} from "./base-entity.service";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TestService extends BaseEntityService {

  constructor(http: HttpClient) {
    super(http);
  }

  getEntityUrl(): string {
    return "tests";
  }
}