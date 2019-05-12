import { Injectable } from '@angular/core';
import {Credentials} from "../model/Credentials";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {User} from "../model/entities";
import { flatMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private static SIGNIN_URL='user/signin';
  private static SIGNOUT_URL='user/signout';
  private static CURRENT_USER_URL='users/current';

  constructor(private http: HttpClient) { }

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
}
