import {Injectable} from '@angular/core';
import {Credentials} from '../model/Credentials';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {User} from '../model/entities';
import {flatMap, map} from 'rxjs/operators';
import {BaseEntityService} from './base-entity.service';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService extends BaseEntityService<User> {

  private static BASE_USER_URL = 'users';
  private static CURRENT_USER_URL = `${UserService.BASE_USER_URL}/current`;

  private static SIGNOUT_URL = 'user/signout';
  private static SIGNIN_URL = 'user/signin';

  private loggedIn: boolean = false;
  role = '' ;


  constructor(http: HttpClient, private router: Router) {
    super(http);
  }

  async getRedactors(): Promise<User[]> {
    return this.http.get<User[]>(UserService.BASE_USER_URL + "/redactors").toPromise()
  }

  async login(credentials: Credentials): Promise<User> {
    const formData = new FormData();
    formData.append('username', credentials.username);
    formData.append('password', credentials.password);

    return this.http.post<void>(UserService.SIGNIN_URL, formData)
      .toPromise()
      .then(() => this.http.get<User>(UserService.CURRENT_USER_URL).toPromise())
      .then((user) => {
        this.loggedIn = true;
      this.role = user.role;

        return user;
      })
  }

  async logout(): Promise<void> {
    await this.http.post<void>(UserService.SIGNOUT_URL, {}).toPromise();
    this.loggedIn = false;
    return
  }

  getEntityUrl(): string {
    return UserService.BASE_USER_URL;
  }

  private async loadCurrentUser(): Promise<User> {
    return this.http.get<User>(UserService.CURRENT_USER_URL).toPromise()
  }

  async isLoggedIn(): Promise<boolean> {
    if (this.loggedIn){
      return this.loggedIn
    }
    this.loggedIn = await this.loadCurrentUser()
      .then((user)=> true)
      .catch((err)=> false)
    return this.loggedIn
  }
}
