import {Injectable} from '@angular/core';

import {BaseEntityService} from "./base-entity.service";
import {HttpClient} from "@angular/common/http";
import {Test, User} from "../model/entities";
import {ChangePermsRequest, ReqOperation, ReqPermission} from "../model/dto";


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
    return this.http.post<Test>(`${this.getEntityUrl()}/${testId}/translate`, null, opts).toPromise()
  }

  async makeAvaliableForCandidate(test: Test, candidate: User) {
    let req: ChangePermsRequest = {
      testId: test.id,
      userId: candidate.id,
      operation: ReqOperation.GRANT,
      permission: ReqPermission.READ
    };

    return this.http.post(`${this.getEntityUrl()}/${test.id}/perms`, req).toPromise()
  }
}
