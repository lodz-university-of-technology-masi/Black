import { Injectable } from '@angular/core';
import {Credentials} from "../model/Credentials";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {User} from "../model/entities";
import { flatMap } from 'rxjs/operators';
import {BaseEntityService} from "./base-entity.service";

@Injectable({
  providedIn: 'root'
})
export class UserService extends BaseEntityService{

  private static BASE_USER_URL='users';
  private static CURRENT_USER_URL=`${UserService.BASE_USER_URL}/current`;

  private static SIGNOUT_URL='user/signout';
  private static SIGNIN_URL='user/signin';

  constructor(http: HttpClient) {
    super(http)
  }

  login(credentials: Credentials) : Observable<User> {
    const formData = new FormData();
    formData.append('username', credentials.username)
    formData.append('password', credentials.password)

    return this.http.post(UserService.SIGNIN_URL, formData).pipe(
      flatMap(() => this.http.get<User>(UserService.CURRENT_USER_URL))
    )
  }

  logout() : Observable<void> {
    return this.http.post<void>(UserService.SIGNOUT_URL, {})
  }

  getEntityUrl(): string {
    return UserService.BASE_USER_URL;
  }
}
