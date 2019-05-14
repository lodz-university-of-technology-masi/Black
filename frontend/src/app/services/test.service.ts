import {Injectable} from '@angular/core';

import {BaseEntityService} from "./base-entity.service";
import {HttpClient} from "@angular/common/http";
import {Test} from "../model/entities";


@Injectable({
  providedIn: 'root'
})
export class TestService extends BaseEntityService<Test> {

  constructor(http: HttpClient) {
    super(http);
  }

  getEntityUrl(): string {
    return 'tests';
  }

  async translateTest(testId: number, targetLanguage: string): Promise<Test> {
    let opts ={
      params: {
        lang: targetLanguage
      }
    };
    return this.http.post<Test>(`${this.getEntityUrl()}/translate/${testId}`, null, opts).toPromise()
  }
}
