import {Injectable} from '@angular/core';

import {BaseEntityService} from './base-entity.service';
import {HttpClient} from '@angular/common/http';
import {Role, Test, User} from '../model/entities';
import {ChangePermsRequest, ReqOperation, ReqPermission} from '../model/dto';


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
    const opts = {
      params: {
        lang: targetLanguage
      }
    };
    return this.http.post<Test>(`${this.getEntityUrl()}/${testId}/translate`, null, opts).toPromise();
  }

  async changeAvailabilityForUser(test: Test, user: User) {
    let permission;
    if (user.role === Role.REDACTOR) {
      permission = ReqPermission.ALL
    } else {
      permission = ReqPermission.READ
    }
    const req: ChangePermsRequest = {
      testId: test.id,
      userId: user.id,
      operation: ReqOperation.GRANT,
      permission: permission
    };

    return this.http.post(`${this.getEntityUrl()}/${test.id}/perms`, req).toPromise();
  }
}
